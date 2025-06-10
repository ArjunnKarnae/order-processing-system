package com.orderprocessing.inventory.mapper;

import com.orderprocessing.inventory.dto.InventoryRequestDTO;
import com.orderprocessing.inventory.dto.InventoryResponseDTO;
import com.orderprocessing.inventory.dto.ProductDTO;
import com.orderprocessing.inventory.dto.ProductReservationsDTO;
import com.orderprocessing.inventory.entity.ProductEntity;
import com.orderprocessing.inventory.entity.ProductReservationsEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class InventoryMapper {

    public static List<ProductEntity> mapInventoryRequestToProductsEntityList(InventoryRequestDTO inventoryRequestDTO){
        List<ProductEntity> productEntityList = inventoryRequestDTO.getProductDTOList().stream().filter(Objects::nonNull)
                .map(productDTO -> {
                    ProductEntity productEntity = new ProductEntity();
                    if(null == productDTO.getProductId()){
                        productEntity.setProductId(getRandomUUID());
                    }else{
                        productEntity.setProductId(productDTO.getProductId());
                    }
                    productEntity.setStatus("AVAILABLE");
                    productEntity.setProductName(productDTO.getProductName());
                    productEntity.setProductDescription(productDTO.getProductDescription());
                    productEntity.setCategory(productDTO.getCategory());
                    productEntity.setCurrentStockLevel(productDTO.getQuantity());
                    productEntity.setPrice(productDTO.getPrice());
                    return productEntity;
                }).collect(Collectors.toList());

        return productEntityList;
    }

    private static String getRandomUUID(){
        String randomUUID = UUID.randomUUID().toString().replace("-", "").substring(0,8);
        return "PROD-"+randomUUID.toUpperCase();
    }

    public static InventoryResponseDTO mapProductsEntityListToInventoryResponse(List<ProductEntity> createdProducts) {
        InventoryResponseDTO inventoryResponseDTO = new InventoryResponseDTO();
        List<ProductDTO> productDTOList =  createdProducts.stream().filter(Objects::nonNull)
                .map(productEntity -> {
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setProductId(productEntity.getProductId());
                    productDTO.setProductName(productEntity.getProductName());
                    productDTO.setProductDescription(productEntity.getProductDescription());
                    productDTO.setPrice(productEntity.getPrice());
                    productDTO.setCategory(productEntity.getCategory());
                    productDTO.setQuantity(productEntity.getCurrentStockLevel());
                    return productDTO;
                }).collect(Collectors.toList());
        inventoryResponseDTO.setProductDTOList(productDTOList);
        return inventoryResponseDTO;
    }

    public static InventoryResponseDTO mapProductsEntityToInventoryResponse(ProductEntity productEntity) {
        InventoryResponseDTO inventoryResponseDTO = new InventoryResponseDTO();
        List<ProductDTO> productDTOList = new ArrayList<>();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(productEntity.getProductId());
        productDTO.setProductName(productEntity.getProductName());
        productDTO.setProductDescription(productEntity.getProductDescription());
        productDTO.setPrice(productEntity.getPrice());
        productDTO.setCategory(productEntity.getCategory());
        productDTO.setQuantity(productEntity.getCurrentStockLevel());
        productDTOList.add(productDTO);
        inventoryResponseDTO.setProductDTOList(productDTOList);
        return inventoryResponseDTO;
    }

    public static ProductReservationsDTO mapProductReservationEntityToDto(ProductReservationsEntity productReservationsEntity){
        ProductReservationsDTO productReservationsDTO = new ProductReservationsDTO();
        productReservationsDTO.setOrderId(productReservationsEntity.getOrderId());
        productReservationsDTO.setReservationId(productReservationsEntity.getReservationId());
        productReservationsDTO.setProductId(productReservationsEntity.getProductId());
        productReservationsDTO.setStatus(productReservationsEntity.getStatus());
        productReservationsDTO.setQuantityReserved(productReservationsEntity.getQuantityReserved());
        return productReservationsDTO;
    }
}
