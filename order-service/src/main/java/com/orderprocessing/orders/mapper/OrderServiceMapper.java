package com.orderprocessing.orders.mapper;

import com.orderprocessing.orders.dto.OrderItemDTO;
import com.orderprocessing.orders.dto.OrderRequest;
import com.orderprocessing.orders.dto.OrderResponse;
import com.orderprocessing.orders.dto.ProductDTO;
import com.orderprocessing.orders.entity.OrderEntity;
import com.orderprocessing.orders.entity.OrderItemsEntity;
import com.orderprocessing.orders.repository.OrderItemsRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class OrderServiceMapper {

    public static OrderEntity mapOrderRequestToOrderEntity(OrderRequest orderRequest){
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(orderEntity.getOrderId());
        orderEntity.setTotalAmount(orderEntity.getTotalAmount());
        orderEntity.setCustomerId(orderEntity.getCustomerId());
        return orderEntity;
    }

    public static List<OrderItemsEntity> mapOrderRequestToOrderItemsEntity(OrderRequest orderRequest){

       List<OrderItemsEntity> orderItemsEntityList = orderRequest.getItems().stream().filter(Objects::nonNull).map(item -> {
            OrderItemsEntity orderItemsEntity = new OrderItemsEntity();
            orderItemsEntity.setProductId(orderItemsEntity.getProductId());
            orderItemsEntity.setProductName(orderItemsEntity.getProductName());
            orderItemsEntity.setProductDescription(orderItemsEntity.getProductDescription());
            orderItemsEntity.setCategory(orderItemsEntity.getCategory());
            orderItemsEntity.setQuantity(orderItemsEntity.getQuantity());
            return orderItemsEntity;
        }).collect(Collectors.toList());

        return orderItemsEntityList;
    }

    public static OrderResponse mapOrderResponseFromCreatedOrder(OrderEntity createdOrder, List<OrderItemsEntity> createdOrderItemsEntity) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(createdOrder.getOrderId());
        orderResponse.setCustomerId(createdOrder.getCustomerId());
        orderResponse.setOrderDate(createdOrder.getCreatedDate());
        List<OrderItemDTO> orderItemDTOList = createdOrderItemsEntity.stream().filter(Objects::nonNull).map(createdOrderItemEntity -> {
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            ProductDTO productDTO = new ProductDTO();
            orderItemDTO.setPrice(createdOrderItemEntity.getPrice());
            orderItemDTO.setQuantity(createdOrderItemEntity.getQuantity());
            productDTO.setProductId(createdOrderItemEntity.getProductId());
            productDTO.setProductName(createdOrderItemEntity.getProductName());
            productDTO.setProductDescription(createdOrderItemEntity.getProductDescription());
            productDTO.setCategory(createdOrderItemEntity.getCategory());
            orderItemDTO.setProductDTO(productDTO);
            return orderItemDTO;
        }).collect(Collectors.toList());
        orderResponse.setItems(orderItemDTOList);
        return orderResponse;
    }
}
