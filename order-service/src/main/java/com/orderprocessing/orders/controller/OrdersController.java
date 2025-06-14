package com.orderprocessing.orders.controller;


import com.orderprocessing.orders.dto.ErrorResponseDTO;
import com.orderprocessing.orders.dto.OrderRequest;
import com.orderprocessing.orders.dto.OrderResponse;
import com.orderprocessing.orders.service.IOrdersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "OrdersController", description = "Controller for managing the Orders")
@RestController
@RequestMapping("api/orders")
public class OrdersController {

    private IOrdersService ordersService;

    @Autowired
    public OrdersController(IOrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @Operation(description = "End point for Creating a new Order")
    @ApiResponses(value = {
                   @ApiResponse(content = @Content(schema = @Schema(implementation = OrderResponse.class)),
                        responseCode = "200",
                        description = "POST api/orders call - Success"),
                    @ApiResponse(content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)),
                            responseCode = "500", description = "POST api/orders call - Failure")
                }
        )
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest){
        OrderResponse orderResponse = this.ordersService.createOrder(orderRequest);
        return ResponseEntity.status(200).body(orderResponse);
    }

    @Operation(description = "End point for retrieving all the Orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GET api/orders call - Success",
            content = @Content(schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "500", description = "GET api/orders call - Failure",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<OrderResponse>> retrieveAllOrders(){
        List<OrderResponse> orderResponseList = this.ordersService.retrieveAllOrders();
        return ResponseEntity.status(200).body(orderResponseList);
    }

    @Operation(description = "End point for retrieving the order based on a Order Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GET api/orders/{orderId} - Success",
            content = @Content(schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "500", description = "GET api/orders/{orderId} - Failure",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> retrieveOrderById(@PathVariable String orderId){
        OrderResponse orderResponse = this.ordersService.retrieveOrderById(orderId);
        return ResponseEntity.status(200).body(orderResponse);
    }
}
