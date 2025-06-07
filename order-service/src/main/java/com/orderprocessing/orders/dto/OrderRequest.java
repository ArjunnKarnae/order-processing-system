package com.orderprocessing.orders.dto;

import java.util.List;

public class OrderRequest {

    public OrderRequest() {
    }

    public OrderRequest(String customerId, List<OrderItemDTO> items, double totalAmount) {
        this.customerId = customerId;
        this.items = items;
        this.totalAmount = totalAmount;
    }

    private String customerId;
    private List<OrderItemDTO> items;
    private double totalAmount;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "customerId='" + customerId + '\'' +
                ", items=" + items +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
