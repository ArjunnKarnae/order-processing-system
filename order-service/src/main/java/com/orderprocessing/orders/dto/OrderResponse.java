package com.orderprocessing.orders.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(name = "Order Response", description = "Order Response DTO")
public class OrderResponse {

    public OrderResponse() {
    }

    public OrderResponse(String orderId, String customerId, List<OrderItemDTO> items, double totalAmount, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
    }

    @Schema(name = "order-id", description = "Order ID", example = "ORD-93s7df7c")
    private String orderId;

    @Schema(name = "customer-id", description = "Customer ID", example = "Cust001")
    private String customerId;

    @Schema(name = "order-items", description = "List of Ordered Items")
    private List<OrderItemDTO> items;

    @Schema(name = "total-amount", description = "Total Amount for the Order")
    private double totalAmount;

    @Schema(name = "order-date", description = "Date when the Order was places")
    private LocalDateTime orderDate;

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

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", items=" + items +
                ", totalAmount=" + totalAmount +
                ", orderDate=" + orderDate +
                '}';
    }
}
