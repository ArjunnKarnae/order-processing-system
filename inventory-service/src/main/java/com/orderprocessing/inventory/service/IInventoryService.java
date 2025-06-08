package com.orderprocessing.inventory.service;

import com.orderprocessing.inventory.dto.InventoryRequestDTO;
import com.orderprocessing.inventory.dto.InventoryResponseDTO;

public interface IInventoryService {

    public InventoryResponseDTO addNewProductsToInventory(InventoryRequestDTO inventoryRequestDTO);

    InventoryResponseDTO getAllProducts();

    InventoryResponseDTO getProductById(String productId);
}
