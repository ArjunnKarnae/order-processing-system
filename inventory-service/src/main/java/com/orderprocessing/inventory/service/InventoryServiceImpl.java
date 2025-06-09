package com.orderprocessing.inventory.service;

import com.orderprocessing.inventory.dto.InventoryRequestDTO;
import com.orderprocessing.inventory.dto.InventoryResponseDTO;
import com.orderprocessing.inventory.dto.ProductDTO;
import com.orderprocessing.inventory.entity.ProductEntity;
import com.orderprocessing.inventory.exceptions.ProductNotFoundException;
import com.orderprocessing.inventory.mapper.InventoryMapper;
import com.orderprocessing.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.Producible;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements IInventoryService{

    private ProductRepository productRepository;

    @Autowired
    public InventoryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Override
    public InventoryResponseDTO addNewProductsToInventory(InventoryRequestDTO inventoryRequestDTO) {

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

        return InventoryMapper.mapProductsEntityListToInventoryResponse(updatedAndCreatedProducts);
    }

    @Transactional(readOnly = true)
    @Override
    public InventoryResponseDTO getAllProducts() {
        List<ProductEntity> fetchedProducts = this.productRepository.findAll();
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


}
