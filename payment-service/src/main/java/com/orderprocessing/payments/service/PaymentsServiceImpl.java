package com.orderprocessing.payments.service;

import com.orderprocessing.payments.dto.PaymentsDTO;
import com.orderprocessing.payments.dto.ProductReservationsDTO;
import com.orderprocessing.payments.entity.PaymentsEntity;
import com.orderprocessing.payments.mapper.PaymentMapper;
import com.orderprocessing.payments.repository.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentsServiceImpl implements IPaymentsService{

    private PaymentsRepository paymentsRepository;

    @Autowired
    public PaymentsServiceImpl(PaymentsRepository paymentsRepository) {
        this.paymentsRepository = paymentsRepository;
    }

    @Override
    public List<PaymentsDTO> getAllPayments() {
        List<PaymentsDTO> paymentsDTOList = null;
        List<PaymentsEntity> paymentsEntityList =  this.paymentsRepository.findAll();
        paymentsDTOList = paymentsEntityList.stream().map(PaymentMapper::mapPaymentEntityPaymentsDto).collect(Collectors.toList());
        return paymentsDTOList;
    }
}
