package com.orderprocessing.inventory.events;

import java.time.LocalDateTime;
import java.util.Set;

public class PaymentFailedEvent {

    public PaymentFailedEvent() {
    }

    public PaymentFailedEvent(String orderId, String paymentId, double amount, String paymentStatus, String reason, LocalDateTime failedAt, Set<String> reservationIds) {
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.reason = reason;
        this.failedAt = failedAt;
        this.reservationIds = reservationIds;
    }

    private String orderId;
    private String paymentId;
    private double amount;
    private String paymentStatus;
    private String reason;
    private LocalDateTime failedAt;

    private Set<String> reservationIds;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getFailedAt() {
        return failedAt;
    }

    public void setFailedAt(LocalDateTime failedAt) {
        this.failedAt = failedAt;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Set<String> getReservationIds() {
        return reservationIds;
    }

    public void setReservationIds(Set<String> reservationIds) {
        this.reservationIds = reservationIds;
    }
}
