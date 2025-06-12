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

import java.util.*;
import java.util.stream.Collectors;


@EnableKafka
@Service
public class EventsConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(EventsConsumerService.class);
    private PaymentsRepository paymentsRepository;
    private EventsPublisherService eventsPublisherService;
    @Autowired
    public EventsConsumerService(PaymentsRepository paymentsRepository, EventsPublisherService eventsPublisherService) {
        this.paymentsRepository = paymentsRepository;
        this.eventsPublisherService = eventsPublisherService;
    }

    @KafkaListener(topics = "${spring.kafka.consumer.topic-name}", groupId = "${spring.kafka.consumer.group-id}", containerFactory = "inventoryReservedEventKafkaListenerContainerFactory")
    public void consumeInventoryReservedEvent(InventoryReservedEvent inventoryReservedEvent){
        logger.debug("####### Inside consumeInventoryReservedEvent - Reading Event Start");
        System.out.println(inventoryReservedEvent);
        PaymentsEntity paymentToSave = getPaymentsEntityList(inventoryReservedEvent);
        PaymentsEntity savedPaymentEntity = this.paymentsRepository.save(paymentToSave);
        Set<String> reservationIds = inventoryReservedEvent.getProductReservations().stream().filter(Objects::nonNull)
                .map(productReservationsDTO -> productReservationsDTO.getReservationId()).collect(Collectors.toSet());
        if("SUCCESS".equalsIgnoreCase(savedPaymentEntity.getPaymentStatus())){
            this.eventsPublisherService.publishPaymentProcessedEvent(savedPaymentEntity, reservationIds);
        }else{
            this.eventsPublisherService.publishPaymentFailedEvent(savedPaymentEntity, reservationIds);
        }
        logger.debug("####### Inside consumeInventoryReservedEvent - Reading Event End");
    }

    private PaymentsEntity getPaymentsEntityList(InventoryReservedEvent inventoryReservedEvent){
        PaymentsEntity paymentsEntity = new PaymentsEntity();
        paymentsEntity.setOrderId(inventoryReservedEvent.getOrderId());
        paymentsEntity.setPaymentId(generatePaymentId());
        paymentsEntity.setPaymentMethod("CARD");
        paymentsEntity.setTotalAmount(inventoryReservedEvent.getTotalPrice());
        paymentsEntity.setPaymentStatus(processPayment(inventoryReservedEvent)? "SUCCESS": "FAILURE");
        return paymentsEntity;
    }

    private String generatePaymentId() {
        return UUID.randomUUID().toString().replace("-", "").substring(1, 8);
    }
    private boolean processPayment(InventoryReservedEvent inventoryReservedEvent){
        if(Math.floor(Math.random() * 10) < 5){
            return true;
        }
        return false;
    }
}
