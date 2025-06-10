package com.orderprocessing.orders.dto;

import java.util.List;

public class OrderPlacedEvent {

    public OrderPlacedEvent() {
    }

    public OrderPlacedEvent(String orderId, List<OrderItemDTO> items) {
        this.orderId = orderId;
        this.items = items;
    }

    private String orderId;
    private List<OrderItemDTO> items;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }
}
