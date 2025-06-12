package com.orderprocessing.payments.service;

import com.orderprocessing.payments.entity.PaymentsEntity;
import com.orderprocessing.payments.events.PaymentFailedEvent;
import com.orderprocessing.payments.events.PaymentProcessedEvent;
import com.orderprocessing.payments.mapper.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EventsPublisherService {

    @Qualifier("paymentProcessedEventKafkaTemplate")
    private KafkaTemplate<String, PaymentProcessedEvent> paymentProcessedEventKafkaTemplate;

    @Qualifier("paymentFailedEventKafkaTemplate")
    private KafkaTemplate<String, PaymentFailedEvent> paymentFailedEventKafkaTemplate;

    @Autowired
    public EventsPublisherService(KafkaTemplate<String, PaymentProcessedEvent> paymentProcessedEventKafkaTemplate, KafkaTemplate<String, PaymentFailedEvent> paymentFailedEventKafkaTemplate) {
        this.paymentProcessedEventKafkaTemplate = paymentProcessedEventKafkaTemplate;
        this.paymentFailedEventKafkaTemplate = paymentFailedEventKafkaTemplate;
    }
    public void publishPaymentProcessedEvent(PaymentsEntity savedPaymentEntity, Set<String> reservationIds){
        PaymentProcessedEvent paymentProcessedEvent = PaymentMapper.mapPaymentEntityToProcessEvent(savedPaymentEntity);
        paymentProcessedEvent.setReservationIds(reservationIds);
        this.paymentProcessedEventKafkaTemplate.send("payment-success-topic", paymentProcessedEvent.getPaymentId(), paymentProcessedEvent);
    }
    public void publishPaymentFailedEvent(PaymentsEntity savedPaymentEntity, Set<String> reservationIds){
        PaymentFailedEvent paymentFailedEvent = PaymentMapper.mapPaymentEntityToFailedEvent(savedPaymentEntity);
        paymentFailedEvent.setReservationIds(reservationIds);
        this.paymentFailedEventKafkaTemplate.send("payment-failure-topic", paymentFailedEvent.getPaymentId(), paymentFailedEvent);
    }
}
