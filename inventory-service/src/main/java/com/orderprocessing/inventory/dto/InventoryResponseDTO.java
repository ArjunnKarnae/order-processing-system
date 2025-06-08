package com.orderprocessing.inventory.dto;

import java.util.List;

public class InventoryResponseDTO {

    public InventoryResponseDTO() {
    }

    public InventoryResponseDTO(List<ProductDTO> productDTOList) {
        this.productDTOList = productDTOList;
    }

    private List<ProductDTO> productDTOList;

    public List<ProductDTO> getProductDTOList() {
        return productDTOList;
    }

    public void setProductDTOList(List<ProductDTO> productDTOList) {
        this.productDTOList = productDTOList;
    }
}
