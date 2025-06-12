package com.orderprocessing.payments.controller;

import com.orderprocessing.payments.dto.PaymentsDTO;
import com.orderprocessing.payments.dto.ProductReservationsDTO;
import com.orderprocessing.payments.service.IPaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

    private IPaymentsService paymentsService;

    @Autowired
    public PaymentsController(IPaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentsDTO>> getAllPayments(){
        List<PaymentsDTO> paymentsDTOList = this.paymentsService.getAllPayments();
        return ResponseEntity.status(200).body(paymentsDTOList);
    }
}
