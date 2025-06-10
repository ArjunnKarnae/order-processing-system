package com.orderprocessing.inventory.dto;

import com.orderprocessing.inventory.dto.OrderItemDTO;

import java.util.List;

public class OrderPlacedEvent {

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

    @Override
    public String toString() {
        return "OrderPlacedEvent{" +
                "orderId='" + orderId + '\'' +
                ", productId='" + productId + '\'' +
                ", items=" + items +
                '}';
    }
}
