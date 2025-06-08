package com.orderprocessing.inventory.dto;

public class ProductDTO {

    public ProductDTO() {
    }

    public ProductDTO(String productId, String requestType, int quantity, String productName, String productDescription, String category, Double price) {
        this.productId = productId;
        this.requestType = requestType;
        this.quantity = quantity;
        this.productName = productName;
        this.productDescription = productDescription;
        this.category = category;
        this.price = price;
    }

    private String productId;
    private String requestType;
    private int quantity;
    private String productName;
    private String productDescription;
    private String category;
    private Double price;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
