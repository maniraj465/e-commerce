package com.maniraj.order.service;

import java.util.UUID;

import com.maniraj.order.client.InventoryClient;
import com.maniraj.order.dto.OrderRequest;
 import com.maniraj.order.exception.ProductNotInStockException;
import com.maniraj.order.model.Order;
import com.maniraj.order.event.OrderPlacedEvent;
import com.maniraj.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;


    public Order placeOrder(OrderRequest orderRequest) {

        log.info("Checking inventory for SKU code: " + orderRequest.skuCode() + " and quantity: " + orderRequest.quantity());

        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        log.info("Inventory check result for SKU code: " + orderRequest.skuCode() + " and quantity: " + orderRequest.quantity() + " is: " + isProductInStock);

        if (isProductInStock) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());
            order.setUserDetails(orderRequest.userDetails());
            orderRepository.save(order);

            // Send the message to Kafka Topic
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber(), order.getUserDetails().getEmail());
            log.info("Start - sending order placed event to Kafka for order number: " + order.getOrderNumber());
            kafkaTemplate.send("order-placed", orderPlacedEvent);
            log.info("End - sent order placed event to Kafka for                                                                                                                                                  order number: " + order.getOrderNumber());

            return order;
        } else {
            throw new ProductNotInStockException(orderRequest.skuCode(), orderRequest.quantity());
        }
    }
}
