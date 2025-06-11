package com.orderprocessing.inventory.events;

import java.time.LocalDateTime;

public class InventoryReservationFailedEvent {

    public InventoryReservationFailedEvent() {
    }

    public InventoryReservationFailedEvent(String orderId, String failedReason, LocalDateTime failedAt) {
        this.orderId = orderId;
        this.failedReason = failedReason;
        this.failedAt = failedAt;
    }

    private String orderId;
    private String failedReason;
    private LocalDateTime failedAt;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    public LocalDateTime getFailedAt() {
        return failedAt;
    }

    public void setFailedAt(LocalDateTime failedAt) {
        this.failedAt = failedAt;
    }

    @Override
    public String toString() {
        return "InventoryFailedEvent{" +
                "orderId='" + orderId + '\'' +
                ", failedReason='" + failedReason + '\'' +
                ", failedAt=" + failedAt +
                '}';
    }
}
