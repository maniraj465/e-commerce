package com.maniraj.orderservice.service;

import com.maniraj.orderservice.client.InventoryClient;
import com.maniraj.orderservice.dto.OrderRequest;
import com.maniraj.orderservice.model.Order;
import com.maniraj.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public Order placeOrder(OrderRequest orderRequest) {
        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if (isProductInStock) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());

            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Product with SKU code " + orderRequest.skuCode() + " is not in stock");
        }
    }
}
