package com.orderprocessing.orders.controller;


import com.orderprocessing.orders.dto.OrderRequest;
import com.orderprocessing.orders.dto.OrderResponse;
import com.orderprocessing.orders.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrdersController {

    private IOrdersService ordersService;

    @Autowired
    public OrdersController(IOrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest){
        OrderResponse orderResponse = this.ordersService.createOrder(orderRequest);
        return ResponseEntity.status(200).body(orderResponse);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> retrieveAllOrders(){
        List<OrderResponse> orderResponseList = this.ordersService.retrieveAllOrders();
        return ResponseEntity.status(200).body(orderResponseList);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> retrieveOrderById(@PathVariable String orderId){
        OrderResponse orderResponse = this.ordersService.retrieveOrderById(orderId);
        return ResponseEntity.status(200).body(orderResponse);
    }
}
