package com.orderprocessing.inventory.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_reservations")
public class ProductReservationsEntity extends BaseEntity{
    public ProductReservationsEntity(String reservationId, String productId, String orderId, int quantityReserved, String status) {
        this.reservationId = reservationId;
        this.productId = productId;
        this.orderId = orderId;
        this.quantityReserved = quantityReserved;
        this.status = status;
    }

    public ProductReservationsEntity() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String reservationId;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "quantity_reserved", nullable = false)
    private int quantityReserved;

    @Column(name = "status")
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
        return "ProductReservationsEntity{" +
                "reservationId=" + reservationId +
                ", productId='" + productId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", quantityReserved=" + quantityReserved +
                ", status='" + status + '\'' +
                '}';
    }
}
