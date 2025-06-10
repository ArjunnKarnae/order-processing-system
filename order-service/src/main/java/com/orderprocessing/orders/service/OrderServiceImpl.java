package com.orderprocessing.orders.service;

import com.orderprocessing.orders.dto.*;
import com.orderprocessing.orders.entity.OrderEntity;
import com.orderprocessing.orders.entity.OrderItemsEntity;
import com.orderprocessing.orders.exceptions.OrderNotFoundException;
import com.orderprocessing.orders.mapper.OrderServiceMapper;
import com.orderprocessing.orders.repository.OrderItemsRepository;
import com.orderprocessing.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrdersService{

    private KafkaTemplate<String, OrderPlacedEvent> orderPlacedEventKafkaTemplate;

    private OrderRepository orderRepository;

    private OrderItemsRepository orderItemsRepository;

    @Autowired
    public OrderServiceImpl(KafkaTemplate<String, OrderPlacedEvent> orderPlacedEventKafkaTemplate, OrderRepository orderRepository, OrderItemsRepository orderItemsRepository) {
        this.orderPlacedEventKafkaTemplate = orderPlacedEventKafkaTemplate;
        this.orderRepository = orderRepository;
        this.orderItemsRepository = orderItemsRepository;
    }


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Override
    public OrderResponse createOrder(OrderRequest orderRequest){

        OrderEntity orderEntity = OrderServiceMapper.mapOrderRequestToOrderEntity(orderRequest);
        orderEntity.setOrderStatus("ORDERED");
        orderEntity.setPaymentStatus("PENDING");
        orderEntity.setOrderId(getOrderId());

        List<OrderItemsEntity> orderItemsEntityList = OrderServiceMapper.mapOrderRequestToOrderItemsEntity(orderRequest);
        orderItemsEntityList.stream().forEach(orderItemsEntity -> orderEntity.addOrderItemsEntity(orderItemsEntity));

        OrderEntity createdOrder = this.orderRepository.save(orderEntity);

        sendOrderPlacedEvent(createdOrder);

        OrderResponse orderResponse = OrderServiceMapper.mapOrderResponseFromCreatedOrder(createdOrder);
        return orderResponse;
    }

    private void sendOrderPlacedEvent(OrderEntity createdOrder) {
        OrderPlacedEvent orderPlacedEvent = populateOrderPlacedEvent(createdOrder);
        this.orderPlacedEventKafkaTemplate.send("order-topic", orderPlacedEvent.getOrderId(), orderPlacedEvent);
    }

    @Override
    public List<OrderResponse> retrieveAllOrders() {

        List<OrderEntity> fetchedOrderEntityList =  this.orderRepository.findAll();
        List<OrderResponse> orderResponseList = fetchedOrderEntityList.stream().map(fetchedOrder ->{
            return OrderServiceMapper.mapOrderResponseFromCreatedOrder(fetchedOrder);
        }).collect(Collectors.toList());

        return orderResponseList;
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse retrieveOrderById(String orderId) {

        return this.orderRepository
                .findById(orderId).map(OrderServiceMapper::mapOrderResponseFromCreatedOrder)
                .orElseThrow(
                        () -> new OrderNotFoundException(String.format("No Order exists with Order Id %s", orderId), HttpStatus.INTERNAL_SERVER_ERROR.value()));


    }

    private String getOrderId(){
        String randomId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        return "ORD-"+randomId;
    }

    private OrderPlacedEvent populateOrderPlacedEvent(OrderEntity createdOrder){
        OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
        orderPlacedEvent.setOrderId(createdOrder.getOrderId());
        List<OrderItemDTO> orderItemDTOList = createdOrder.getOrderItemsEntityList().stream().map(orderItemsEntity -> {
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            orderItemDTO.setPrice(orderItemsEntity.getPrice());
            orderItemDTO.setQuantity(orderItemsEntity.getQuantity());
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(orderItemsEntity.getProductId());
            productDTO.setProductName(orderItemsEntity.getProductName());
            productDTO.setProductDescription(orderItemsEntity.getProductDescription());
            productDTO.setCategory(orderItemsEntity.getCategory());
            orderItemDTO.setProductDTO(productDTO);
            return orderItemDTO;
        }).collect(Collectors.toList());
        orderPlacedEvent.setItems(orderItemDTOList);
        return orderPlacedEvent;
    }


}
