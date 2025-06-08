package com.orderprocessing.inventory.service;

import com.orderprocessing.inventory.dto.InventoryRequestDTO;
import com.orderprocessing.inventory.dto.InventoryResponseDTO;
import com.orderprocessing.inventory.dto.ProductDTO;
import com.orderprocessing.inventory.entity.ProductEntity;
import com.orderprocessing.inventory.exceptions.ProductNotFoundException;
import com.orderprocessing.inventory.mapper.InventoryMapper;
import com.orderprocessing.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        List<ProductEntity> productEntityList = InventoryMapper.mapInventoryRequestToProductsEntityList(inventoryRequestDTO);
        List<ProductEntity> createdProducts = this.productRepository.saveAll(productEntityList);
        return InventoryMapper.mapProductsEntityListToInventoryResponse(createdProducts);
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
