package com.orderprocessing.inventory.service;

import com.orderprocessing.inventory.dto.InventoryRequestDTO;
import com.orderprocessing.inventory.dto.InventoryResponseDTO;
import com.orderprocessing.inventory.dto.OrderItemDTO;
import com.orderprocessing.inventory.dto.ProductReservationsDTO;
import com.orderprocessing.inventory.entity.ProductReservationsEntity;
import com.orderprocessing.inventory.events.OrderPlacedEvent;
import com.orderprocessing.inventory.entity.ProductEntity;
import com.orderprocessing.inventory.exceptions.ProductNotFoundException;
import com.orderprocessing.inventory.mapper.InventoryMapper;
import com.orderprocessing.inventory.repository.ProductRepository;
import com.orderprocessing.inventory.repository.ProductReservationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class InventoryServiceImpl implements IInventoryService{

    private static final Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);

    private ProductRepository productRepository;
    private ProductReservationsRepository productReservationsRepository;
    @Autowired
    public InventoryServiceImpl(ProductRepository productRepository, ProductReservationsRepository productReservationsRepository) {
        this.productRepository = productRepository;
        this.productReservationsRepository = productReservationsRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Override
    public InventoryResponseDTO addNewProductsToInventory(InventoryRequestDTO inventoryRequestDTO) {
        logger.info("Inside addNewProductsToInventory method - BEGIN ");
        List<ProductEntity> incomingProductEntityList = InventoryMapper.mapInventoryRequestToProductsEntityList(inventoryRequestDTO);

        Set<String> incomingProductIds =  incomingProductEntityList.stream().map(ProductEntity::getProductId).collect(Collectors.toSet());

        Map<String, ProductEntity> existingProductsmap = this.productRepository.findAllById(incomingProductIds)
                        .stream().collect(Collectors.toMap(ProductEntity::getProductId, productEntity -> productEntity));

        List<ProductEntity> productsToSave = new ArrayList<>();

        incomingProductEntityList.stream().forEach(incomingProduct -> {
            ProductEntity existingProduct = existingProductsmap.get(incomingProduct.getProductId());
            if(null == existingProduct){
                productsToSave.add(incomingProduct);
            }else{
                int updatedStock = existingProduct.getCurrentStockLevel() + incomingProduct.getCurrentStockLevel();
                existingProduct.setCurrentStockLevel(updatedStock);
                productsToSave.add(existingProduct);
            }
        });

        List<ProductEntity> updatedAndCreatedProducts = this.productRepository.saveAll(productsToSave);
        logger.info("Inside addNewProductsToInventory method - END ");
        return InventoryMapper.mapProductsEntityListToInventoryResponse(updatedAndCreatedProducts);
    }

    @Transactional(readOnly = true)
    @Override
    public InventoryResponseDTO getAllProducts() {
        logger.info("Inside getAllProducts method - BEGIN ");
        List<ProductEntity> fetchedProducts = this.productRepository.findAll();
        logger.info("Inside getAllProducts method - END ");
        return InventoryMapper.mapProductsEntityListToInventoryResponse(fetchedProducts);
    }

    @Override
    public InventoryResponseDTO getProductById(String productId) {
        return this.productRepository
                .findById(productId).map(InventoryMapper::mapProductsEntityToInventoryResponse)
                .orElseThrow(() ->
                     new ProductNotFoundException(String.format("No Product Found with ID: %s", productId), HttpStatus.INTERNAL_SERVER_ERROR.value())
                );
    }

    @Override
    public List<ProductReservationsDTO> getAllProductOrders() {

        return this.productReservationsRepository.findAll()
                .stream()
                .map(InventoryMapper::mapProductReservationEntityToDto)
                .collect(Collectors.toList());
    }

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
        this.productReservationsRepository.saveAll(productReservationsEntities);

        logger.info("################## End - Reading the Order placed event data ############");
    }



    private ProductReservationsEntity populateProductReservation(OrderItemDTO orderItemDTO, String orderId) {
        logger.info("Inside populateProductReservation method - BEGIN ");
        ProductReservationsEntity productReservationsEntity = new ProductReservationsEntity();
        productReservationsEntity.setOrderId(orderId);
        productReservationsEntity.setProductId(orderItemDTO.getProductDTO().getProductId());
        productReservationsEntity.setQuantityReserved(orderItemDTO.getQuantity());
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
