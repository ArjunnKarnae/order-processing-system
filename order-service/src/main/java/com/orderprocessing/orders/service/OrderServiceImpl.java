package com.orderprocessing.orders.service;

import com.orderprocessing.orders.dto.OrderRequest;
import com.orderprocessing.orders.dto.OrderResponse;
import com.orderprocessing.orders.entity.OrderEntity;
import com.orderprocessing.orders.entity.OrderItemsEntity;
import com.orderprocessing.orders.mapper.OrderServiceMapper;
import com.orderprocessing.orders.repository.OrderItemsRepository;
import com.orderprocessing.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements IOrdersService{

    private OrderRepository orderRepository;

    private OrderItemsRepository orderItemsRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderItemsRepository orderItemsRepository) {
        this.orderRepository = orderRepository;
        this.orderItemsRepository = orderItemsRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Override
    public OrderResponse createOrder(OrderRequest orderRequest){

        OrderEntity orderEntity = OrderServiceMapper.mapOrderRequestToOrderEntity(orderRequest);
        orderEntity.setOrderStatus("ORDERED");
        orderEntity.setPaymentStatus("PENDING");
        orderEntity.setOrderId("ORD-001");

        List<OrderItemsEntity> orderItemsEntityList = OrderServiceMapper.mapOrderRequestToOrderItemsEntity(orderRequest);
        orderItemsEntityList.stream().forEach(orderItemsEntity -> orderEntity.addOrderItemsEntity(orderItemsEntity));

        OrderEntity createdOrder = this.orderRepository.save(orderEntity);
       // List<OrderItemsEntity> createdOrderItemsEntity = this.orderItemsRepository.saveAll(orderItemsEntityList);
        OrderResponse orderResponse = OrderServiceMapper.mapOrderResponseFromCreatedOrder(createdOrder);
        return orderResponse;
    }

}
