package com.orderprocessing.payments.mapper;

import com.orderprocessing.payments.dto.PaymentsDTO;
import com.orderprocessing.payments.dto.ProductReservationsDTO;
import com.orderprocessing.payments.entity.PaymentsEntity;
import com.orderprocessing.payments.events.PaymentFailedEvent;
import com.orderprocessing.payments.events.PaymentProcessedEvent;

public class PaymentMapper {
    public static PaymentProcessedEvent mapPaymentEntityToProcessEvent(PaymentsEntity savedPaymentEntity){
        PaymentProcessedEvent paymentProcessedEvent = new PaymentProcessedEvent();
        paymentProcessedEvent.setPaymentId(savedPaymentEntity.getPaymentId());
        paymentProcessedEvent.setOrderId(savedPaymentEntity.getOrderId());
        paymentProcessedEvent.setAmount(savedPaymentEntity.getTotalAmount());
        paymentProcessedEvent.setPaymentStatus(savedPaymentEntity.getPaymentStatus());
        paymentProcessedEvent.setPaidAt(savedPaymentEntity.getCreatedAt());
        return paymentProcessedEvent;
    }

    public static PaymentFailedEvent mapPaymentEntityToFailedEvent(PaymentsEntity savedPaymentEntity){
        PaymentFailedEvent paymentFailedEvent = new PaymentFailedEvent();
        paymentFailedEvent.setOrderId(savedPaymentEntity.getOrderId());
        paymentFailedEvent.setPaymentId(savedPaymentEntity.getPaymentId());
        paymentFailedEvent.setPaymentStatus(savedPaymentEntity.getPaymentStatus());
        paymentFailedEvent.setAmount(savedPaymentEntity.getTotalAmount());
        paymentFailedEvent.setFailedAt(savedPaymentEntity.getCreatedAt());
        paymentFailedEvent.setReason("Could Not Process the Payment");
        return paymentFailedEvent;
    }

    public static PaymentsDTO mapPaymentEntityPaymentsDto(PaymentsEntity paymentsEntity){
        PaymentsDTO paymentsDTO = new PaymentsDTO();
        paymentsDTO.setOrderId(paymentsEntity.getOrderId());
        paymentsDTO.setPaymentId(paymentsEntity.getPaymentId());
        paymentsDTO.setPaymentMethod(paymentsEntity.getPaymentMethod());
        paymentsDTO.setPaymentStatus(paymentsEntity.getPaymentStatus());
        paymentsDTO.setTotalAmount(paymentsEntity.getTotalAmount());
        return paymentsDTO;
    }
}
