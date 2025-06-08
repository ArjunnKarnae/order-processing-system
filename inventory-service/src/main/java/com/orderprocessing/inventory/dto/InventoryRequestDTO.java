package com.orderprocessing.inventory.dto;

import java.util.List;

public class InventoryRequestDTO {

    public InventoryRequestDTO() {
    }

    public InventoryRequestDTO(List<ProductDTO> productDTOList) {
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
