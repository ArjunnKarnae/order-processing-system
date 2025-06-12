package com.orderprocessing.payments.events;

import java.time.LocalDateTime;
import java.util.Set;

public class PaymentProcessedEvent {

    public PaymentProcessedEvent() {
    }

    public PaymentProcessedEvent(String orderId, String paymentId, double amount, String paymentStatus, LocalDateTime paidAt, Set<String> reservationIds) {
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.paidAt = paidAt;
        this.reservationIds = reservationIds;
    }

    private String orderId;
    private String paymentId;
    private double amount;
    private String paymentStatus;
    private LocalDateTime paidAt;
    private Set<String> reservationIds;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
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

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public Set<String> getReservationIds() {
        return reservationIds;
    }

    public void setReservationIds(Set<String> reservationIds) {
        this.reservationIds = reservationIds;
    }
}
