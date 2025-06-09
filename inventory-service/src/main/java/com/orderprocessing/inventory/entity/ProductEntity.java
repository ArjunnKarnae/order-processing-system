package com.orderprocessing.inventory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "products")
public class ProductEntity extends BaseEntity{

    public ProductEntity() {
    }

    public ProductEntity(String productId, String productName, String productDescription, String category, int currentStockLevel, int reservedStockLevel, String status, Double price) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.category = category;
        this.currentStockLevel = currentStockLevel;
        this.reservedStockLevel = reservedStockLevel;
        this.status = status;
        this.price = price;
    }

    @Id
    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_description", nullable = false)
    private String productDescription;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "current_stock_level", nullable = false)
    @Positive
    private int currentStockLevel;

    @Column(name = "reserved_stock_level")
    private int reservedStockLevel;

    @Column(name = "status")
    private String status;

    @Column(name = "price", nullable = false)
    @Positive
    private Double price;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getCurrentStockLevel() {
        return currentStockLevel;
    }

    public void setCurrentStockLevel(int currentStockLevel) {
        this.currentStockLevel = currentStockLevel;
    }

    public int getReservedStockLevel() {
        return reservedStockLevel;
    }

    public void setReservedStockLevel(int reservedStockLevel) {
        this.reservedStockLevel = reservedStockLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", category='" + category + '\'' +
                ", currentStockLevel=" + currentStockLevel +
                ", reservedStockLevel=" + reservedStockLevel +
                ", status='" + status + '\'' +
                ", price=" + price +
                '}';
    }
}
