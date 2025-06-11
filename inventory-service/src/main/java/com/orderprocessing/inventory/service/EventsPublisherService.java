package com.orderprocessing.inventory.service;

import com.orderprocessing.inventory.dto.ProductReservationsDTO;
import com.orderprocessing.inventory.entity.ProductReservationsEntity;
import com.orderprocessing.inventory.events.InventoryReservationFailedEvent;
import com.orderprocessing.inventory.events.InventoryReservedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventsPublisherService {

    @Value("${spring.kafka.producer.topic-name}")
    private String topicName;

    private KafkaTemplate<String, InventoryReservedEvent> inventoryReservedEventKafkaTemplate;
    private KafkaTemplate<String, InventoryReservationFailedEvent> inventoryReservationFailedEventKafkaTemplate;

    @Autowired
    public EventsPublisherService(KafkaTemplate<String, InventoryReservedEvent> inventoryReservedEventKafkaTemplate, KafkaTemplate<String, InventoryReservationFailedEvent> inventoryReservationFailedEventKafkaTemplate) {
        this.inventoryReservedEventKafkaTemplate = inventoryReservedEventKafkaTemplate;
        this.inventoryReservationFailedEventKafkaTemplate = inventoryReservationFailedEventKafkaTemplate;
    }

    public void publishInventoryReservedEvent(List<ProductReservationsEntity> savedProductReservations, String orderId){
        InventoryReservedEvent inventoryReservedEvent = new InventoryReservedEvent();
        inventoryReservedEvent.setOrderId(orderId);
        List<ProductReservationsDTO> productReservationsDTOList = savedProductReservations.stream().map(reservation -> {
            ProductReservationsDTO productReservationsDTO = new ProductReservationsDTO();
            productReservationsDTO.setOrderId(orderId);
            productReservationsDTO.setProductId(reservation.getProductId());
            productReservationsDTO.setQuantityReserved(reservation.getQuantityReserved());
            productReservationsDTO.setReservationId(reservation.getReservationId());
            return productReservationsDTO;
        }).collect(Collectors.toList());
        inventoryReservedEvent.setProductReservations(productReservationsDTOList);
        Double totalOrderPrice = savedProductReservations.stream().map(p -> p.getPrice() * p.getQuantityReserved()).reduce(0.0, Double::sum);
        inventoryReservedEvent.setTotalPrice(totalOrderPrice);
        this.inventoryReservedEventKafkaTemplate.send(topicName, orderId, inventoryReservedEvent);
    }
}
