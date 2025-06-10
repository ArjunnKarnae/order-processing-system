package com.orderprocessing.orders.dto;

import java.util.List;

public class OrderPlacedEvent {

    public OrderPlacedEvent() {
    }

    public OrderPlacedEvent(String orderId, String productId, List<OrderItemDTO> items) {
        this.orderId = orderId;
        this.productId = productId;
        this.items = items;
    }

    private String orderId;
    private String productId;
    private List<OrderItemDTO> items;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }
}
