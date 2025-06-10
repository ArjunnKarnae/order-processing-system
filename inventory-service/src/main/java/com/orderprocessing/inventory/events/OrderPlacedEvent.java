package com.orderprocessing.inventory.events;

import com.orderprocessing.inventory.dto.OrderItemDTO;

import java.util.List;

public class OrderPlacedEvent {

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

    @Override
    public String toString() {
        return "OrderPlacedEvent{" +
                "orderId='" + orderId + '\'' +
                ", items=" + items +
                '}';
    }
}
