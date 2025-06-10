package com.orderprocessing.inventory.controller;

import com.orderprocessing.inventory.dto.InventoryRequestDTO;
import com.orderprocessing.inventory.dto.InventoryResponseDTO;
import com.orderprocessing.inventory.dto.ProductReservationsDTO;
import com.orderprocessing.inventory.service.IInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/inventory")
public class InventoryController {

    private IInventoryService inventoryService;

    @Autowired
    public InventoryController(IInventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/products")
    public ResponseEntity<InventoryResponseDTO> addNewProductsToInventory(@RequestBody InventoryRequestDTO inventoryRequestDTO){
        InventoryResponseDTO inventoryResponseDTO = this.inventoryService.addNewProductsToInventory(inventoryRequestDTO);
        return ResponseEntity.status(201).body(inventoryResponseDTO);
    }

    @GetMapping("/products")
    public ResponseEntity<InventoryResponseDTO> getAllProducts(){
        InventoryResponseDTO inventoryResponseDTO = this.inventoryService.getAllProducts();
        return ResponseEntity.status(200).body(inventoryResponseDTO);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<InventoryResponseDTO> getProductById(@PathVariable String productId){
        InventoryResponseDTO inventoryResponseDTO = this.inventoryService.getProductById(productId);
        return ResponseEntity.status(200).body(inventoryResponseDTO);
    }

    @GetMapping("/products/orders")
    public ResponseEntity<List<ProductReservationsDTO>> getAllProductOrders(){
        List<ProductReservationsDTO> productReservationsDTOList = this.inventoryService.getAllProductOrders();
        return ResponseEntity.status(200).body(productReservationsDTOList);
    }
}
