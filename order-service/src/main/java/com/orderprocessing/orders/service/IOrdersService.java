package com.orderprocessing.orders.service;

import com.orderprocessing.orders.dto.OrderRequest;
import com.orderprocessing.orders.dto.OrderResponse;

import java.util.List;

public interface IOrdersService {

    public OrderResponse createOrder(OrderRequest orderRequest);

    List<OrderResponse> retrieveAllOrders();

    OrderResponse retrieveOrderById(String orderId);
}
