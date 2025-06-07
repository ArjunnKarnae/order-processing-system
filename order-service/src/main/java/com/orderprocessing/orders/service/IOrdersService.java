package com.orderprocessing.orders.service;

import com.orderprocessing.orders.dto.OrderRequest;
import com.orderprocessing.orders.dto.OrderResponse;

public interface IOrdersService {

    public OrderResponse createOrder(OrderRequest orderRequest);
}
