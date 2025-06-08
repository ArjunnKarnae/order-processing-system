package com.orderprocessing.orders.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OrderServiceExceptionHandler {

    @ExceptionHandler(exception = OrderNotFoundException.class)
    public ResponseEntity handleOrderNotFoundException(OrderNotFoundException orderNotFoundException){
        return new ResponseEntity<>(orderNotFoundException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
