package com.orderprocessing.orders.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.classify.annotation.Classifier;

@Entity
@Table(name = "order_items")
public class OrderItemsEntity {

    public OrderItemsEntity() {
    }

    public OrderItemsEntity(long itemId, String productId, String productName, String productDescription, String category, double price) {
        this.itemId = itemId;
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.category = category;
        this.price = price;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long itemId;
    @NotNull
    @Column(name = "product_id")
    private String productId;
    @NotNull
    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_description")
    private String productDescription;

    @NotNull
    @Column(name = "category")
    private String category;

    @Positive
    @Column(name = "price")
    private double price;

    @Positive
    @Column(name = "quantity")
    private int quantity;

    @ManyToOne()
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private OrderEntity orderEntity;

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

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItemsEntity{" +
                "itemId=" + itemId +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +

                '}';
    }
}
