package com.orderprocessing.inventory.dto;

import jakarta.persistence.Column;

public class ProductReservationsDTO {

    public ProductReservationsDTO() {
    }

    public ProductReservationsDTO(String reservationId, String productId, String orderId, int quantityReserved, String status) {
        this.reservationId = reservationId;
        this.productId = productId;
        this.orderId = orderId;
        this.quantityReserved = quantityReserved;
        this.status = status;
    }

    private String reservationId;
    private String productId;
    private String orderId;
    private int quantityReserved;
    private String status;

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getQuantityReserved() {
        return quantityReserved;
    }

    public void setQuantityReserved(int quantityReserved) {
        this.quantityReserved = quantityReserved;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ProductReservationsDTO{" +
                "reservationId='" + reservationId + '\'' +
                ", productId='" + productId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", quantityReserved=" + quantityReserved +
                ", status='" + status + '\'' +
                '}';
    }
}
