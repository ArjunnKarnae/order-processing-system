package com.orderprocessing.orders.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Product", description = "Product DTO")
public class ProductDTO {

    public ProductDTO() {
    }

    public ProductDTO(String productId, String productName, String productDescription, String category) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.category = category;
    }

    @Schema(name = "product-id", description = "ID of the Product", example = "PROD-S396W67E")
    private String productId;
    @Schema(name = "product-name", description = "Product Name")
    private String productName;
    @Schema(name = "product-description", description = "More Information about Product")
    private String productDescription;
    @Schema(name = "category", description = "Product Category")
    private String category;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
