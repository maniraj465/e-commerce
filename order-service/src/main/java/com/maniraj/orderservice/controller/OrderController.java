package com.maniraj.orderservice.controller;

import com.maniraj.orderservice.dto.OrderRequest;
import com.maniraj.orderservice.dto.OrderResponse;
import com.maniraj.orderservice.model.Order;
import com.maniraj.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
        System.out.println("Received order request: " + orderRequest);
        Order order = orderService.placeOrder(orderRequest);
        OrderResponse response = new OrderResponse("Order placed successfully", order.getOrderNumber(), LocalDateTime.now());
        return ResponseEntity.status(201).body(response);
    }

}
