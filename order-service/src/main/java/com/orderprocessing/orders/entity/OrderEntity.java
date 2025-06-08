package com.orderprocessing.orders.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity{

    public OrderEntity() {
    }

    public OrderEntity(String orderId, String customerId, List<OrderItemsEntity> orderItemsEntityList, double totalAmount, String orderStatus, String paymentStatus) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItemsEntityList = orderItemsEntityList;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
    }

    @Id
    @NotNull
    @Column(name = "order_id")
    private String orderId;

    @NotNull
    @Column(name = "customer_id")
    private String customerId;

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL)
    private List<OrderItemsEntity> orderItemsEntityList;

    @Positive
    private double totalAmount;

    @NotNull
    private String orderStatus;

    @NotNull
    private String paymentStatus;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<OrderItemsEntity> getOrderItemsEntityList() {
        return orderItemsEntityList;
    }

    public void setOrderItemsEntityList(List<OrderItemsEntity> orderItemsEntityList) {
        this.orderItemsEntityList = orderItemsEntityList;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void addOrderItemsEntity(OrderItemsEntity orderItemsEntity){
        if(null == this.orderItemsEntityList){
            this.orderItemsEntityList = new ArrayList<>();
        }
        this.orderItemsEntityList.add(orderItemsEntity);
        orderItemsEntity.setOrderEntity(this);
    }

    public void removeOrderItemsEntity(OrderItemsEntity orderItemsEntity){
        this.orderItemsEntityList.remove(orderItemsEntity);
        orderItemsEntity.setOrderEntity(null);
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", orderItemsEntityList=" + orderItemsEntityList +
                ", totalAmount=" + totalAmount +
                ", orderStatus='" + orderStatus + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}
