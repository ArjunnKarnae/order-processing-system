package com.orderprocessing.payments.service;

import com.orderprocessing.payments.dto.PaymentsDTO;
import com.orderprocessing.payments.dto.ProductReservationsDTO;

import java.util.List;

public interface IPaymentsService {
    List<PaymentsDTO> getAllPayments();
}
