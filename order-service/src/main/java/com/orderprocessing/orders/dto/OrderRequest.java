package com.orderprocessing.orders.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "Order Request", description = "Order Request")
public class OrderRequest {

    public OrderRequest() {
    }

    public OrderRequest(String customerId, List<OrderItemDTO> items, double totalAmount) {
        this.customerId = customerId;
        this.items = items;
        this.totalAmount = totalAmount;
    }

    @Schema(name = "customer-id", description = "Customer ID")
    private String customerId;
    @Schema(name = "Order Items", description = "Order Items List")
    private List<OrderItemDTO> items;
    @Schema(name = "total-amount", description = "Total price for the Order")
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
