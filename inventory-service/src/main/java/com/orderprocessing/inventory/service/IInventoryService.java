package com.orderprocessing.inventory.service;

import com.orderprocessing.inventory.dto.InventoryRequestDTO;
import com.orderprocessing.inventory.dto.InventoryResponseDTO;
import com.orderprocessing.inventory.dto.ProductReservationsDTO;

import java.util.List;

public interface IInventoryService {

    public InventoryResponseDTO addNewProductsToInventory(InventoryRequestDTO inventoryRequestDTO);

    InventoryResponseDTO getAllProducts();

    InventoryResponseDTO getProductById(String productId);

    List<ProductReservationsDTO> getAllProductOrders();
}
