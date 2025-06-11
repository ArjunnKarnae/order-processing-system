package com.orderprocessing.payments.service;

import com.orderprocessing.payments.entity.PaymentsEntity;
import com.orderprocessing.payments.events.InventoryReservedEvent;
import com.orderprocessing.payments.repository.PaymentsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@EnableKafka
@Service
public class EventsConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(EventsConsumerService.class);


    private PaymentsRepository paymentsRepository;

    @Autowired
    public EventsConsumerService(PaymentsRepository paymentsRepository) {
        this.paymentsRepository = paymentsRepository;
    }

    @KafkaListener(topics = "${spring.kafka.consumer.topic-name}", groupId = "${spring.kafka.consumer.group-id}", containerFactory = "inventoryReservedEventKafkaListenerContainerFactory")
    public void consumeInventoryReservedEvent(InventoryReservedEvent inventoryReservedEvent){
        logger.debug("####### Inside consumeInventoryReservedEvent - Reading Event Start");
        System.out.println(inventoryReservedEvent);
        List<PaymentsEntity> paymentsToSave = getPaymentsEntityList(inventoryReservedEvent);
        paymentsToSave.stream().forEach(pe -> {
            pe.setPaymentStatus(processPayment(inventoryReservedEvent)? "SUCCESS": "FAILURE");
        });
        List<PaymentsEntity> savedPaymentsEntityList = this.paymentsRepository.saveAll(paymentsToSave);
        if(!CollectionUtils.isEmpty(savedPaymentsEntityList)){
            System.out.println("Payment Saved Success");
        }
        logger.debug("####### Inside consumeInventoryReservedEvent - Reading Event End");
    }

    private List<PaymentsEntity> getPaymentsEntityList(InventoryReservedEvent inventoryReservedEvent){

        List<PaymentsEntity> paymentsEntityList = inventoryReservedEvent.getProductReservations().stream().filter(Objects::nonNull)
                .map(productReservation -> {
                    PaymentsEntity paymentsEntity = new PaymentsEntity();
                    paymentsEntity.setOrderId(productReservation.getOrderId());
                    paymentsEntity.setReservationId(productReservation.getReservationId());
                    paymentsEntity.setPaymentId(productReservation.getProductId());
                    paymentsEntity.setPaymentMethod("CARD");
                    paymentsEntity.setTotalAmount(inventoryReservedEvent.getTotalPrice());
                    return paymentsEntity;
                }).collect(Collectors.toList());
        return paymentsEntityList;
    }

    private boolean processPayment(InventoryReservedEvent inventoryReservedEvent){
        if(Math.floor(Math.random() * 10) < 5){
            return true;
        }
        return false;
    }
}
