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

    public ProductEntity(String productId, String productName, String productDescription, String category, int stockQuantity, Double price) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.category = category;
        this.stockQuantity = stockQuantity;
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

    @Column(name = "stock_quantity", nullable = false)
    @Positive
    private int stockQuantity;

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

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", category='" + category + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", price=" + price +
                '}';
    }
}
