package com.orderprocessing.payments.events;

import com.orderprocessing.payments.dto.ProductReservationsDTO;

import java.util.List;

public class InventoryReservedEvent {

    public InventoryReservedEvent(String orderId, List<ProductReservationsDTO> productReservations, double totalPrice) {
        this.orderId = orderId;
        this.productReservations = productReservations;
        this.totalPrice = totalPrice;
    }

    public InventoryReservedEvent() {

    }

    private String orderId;
    private List<ProductReservationsDTO> productReservations;
    private double totalPrice;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<ProductReservationsDTO> getProductReservations() {
        return productReservations;
    }

    public void setProductReservations(List<ProductReservationsDTO> productReservations) {
        this.productReservations = productReservations;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "InventoryReservedEvent{" +
                "orderId='" + orderId + '\'' +
                ", productReservations=" + productReservations +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
