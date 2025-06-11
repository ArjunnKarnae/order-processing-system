package com.orderprocessing.inventory.service;

import com.orderprocessing.inventory.dto.OrderItemDTO;
import com.orderprocessing.inventory.entity.ProductEntity;
import com.orderprocessing.inventory.entity.ProductReservationsEntity;
import com.orderprocessing.inventory.events.OrderPlacedEvent;
import com.orderprocessing.inventory.repository.ProductRepository;
import com.orderprocessing.inventory.repository.ProductReservationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventsConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);

    private ProductRepository productRepository;
    private ProductReservationsRepository productReservationsRepository;
    private EventsPublisherService eventsPublisherService;

    @Autowired
    public EventsConsumerService(ProductRepository productRepository, ProductReservationsRepository productReservationsRepository, EventsPublisherService eventsPublisherService) {
        this.productRepository = productRepository;
        this.productReservationsRepository = productReservationsRepository;
        this.eventsPublisherService = eventsPublisherService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @KafkaListener(topics = "order-topic", groupId = "inventory-consumer-group", containerFactory = "orderPlacedEventConcurrentKafkaListenerContainerFactory")
    public void consumeOrderPlacedEvent(OrderPlacedEvent orderPlacedEvent){
        logger.info("################## Start - Reading the Order placed event data ############");
        Set<String> orderedProductIds = orderPlacedEvent.getItems()
                .stream().map(orderItemDTO -> orderItemDTO.getProductDTO().getProductId()).collect(Collectors.toSet());

        Map<String, ProductEntity> fetchedProducts = this.productRepository.findAllById(orderedProductIds)
                .stream().collect(Collectors.toMap(ProductEntity::getProductId, productEntity -> productEntity));

        List<ProductEntity> productsToSave = new ArrayList<>();
        List<ProductReservationsEntity> productReservationsEntities = new ArrayList<>();

        orderPlacedEvent.getItems()
                .stream().forEach(orderItemDTO -> {
                    if(fetchedProducts.containsKey(orderItemDTO.getProductDTO().getProductId())){
                        ProductEntity fetchedProductEntity = fetchedProducts.get(orderItemDTO.getProductDTO().getProductId());
                        if(!"OUT OF STOCK".equalsIgnoreCase(fetchedProductEntity.getStatus())) {
                            productsToSave.add(populateProductToSave(orderItemDTO, fetchedProductEntity));
                            productReservationsEntities.add(populateProductReservation(orderItemDTO, orderPlacedEvent.getOrderId()));
                        }
                    }
                });

        this.productRepository.saveAll(productsToSave);
        List<ProductReservationsEntity> saveProductReservations = this.productReservationsRepository.saveAll(productReservationsEntities);
        publishInventoryReservedEvent(saveProductReservations, orderPlacedEvent.getOrderId());
        logger.info("################## End - Reading the Order placed event data ############");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void publishInventoryReservedEvent(List<ProductReservationsEntity> saveProductReservations, String orderId){
        this.eventsPublisherService.publishInventoryReservedEvent(saveProductReservations, orderId);
    }

    private ProductReservationsEntity populateProductReservation(OrderItemDTO orderItemDTO, String orderId) {
        logger.info("Inside populateProductReservation method - BEGIN ");
        ProductReservationsEntity productReservationsEntity = new ProductReservationsEntity();
        productReservationsEntity.setOrderId(orderId);
        productReservationsEntity.setProductId(orderItemDTO.getProductDTO().getProductId());
        productReservationsEntity.setQuantityReserved(orderItemDTO.getQuantity());
        productReservationsEntity.setPrice(orderItemDTO.getPrice());
        productReservationsEntity.setStatus("PENDING");
        logger.info("Inside populateProductReservation method - END ");
        return productReservationsEntity;
    }

    private ProductEntity populateProductToSave(OrderItemDTO orderItemDTO, ProductEntity fetchedProductEntity) {
        logger.info("Inside populateProductToSave method - BEGIN ");
        var updatedStockLevel = fetchedProductEntity.getCurrentStockLevel() - orderItemDTO.getQuantity();
        fetchedProductEntity.setCurrentStockLevel(updatedStockLevel);
        fetchedProductEntity.setReservedStockLevel(orderItemDTO.getQuantity());
        if (fetchedProductEntity.getCurrentStockLevel() == 0) {
            fetchedProductEntity.setStatus("OUT OF STOCK");
        }
        logger.info("Inside populateProductToSave method - END ");
        return fetchedProductEntity;
    }
}
