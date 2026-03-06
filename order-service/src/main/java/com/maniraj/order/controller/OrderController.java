package com.maniraj.order.controller;

import com.maniraj.order.dto.OrderRequest;
import com.maniraj.order.dto.OrderResponse;
import com.maniraj.order.model.Order;
import com.maniraj.order.service.OrderService;
import jakarta.validation.Valid;
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
    public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody OrderRequest orderRequest) {
        System.out.println("Received order request: " + orderRequest);
        Order order = orderService.placeOrder(orderRequest);
        OrderResponse response = new OrderResponse(
                "Order placed successfully",
                order.getOrderNumber(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(201).body(response);
    }

}
