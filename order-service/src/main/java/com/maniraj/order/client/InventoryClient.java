package com.maniraj.order.client;

//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

//@FeignClient(name = "inventory-service", url = "http://localhost:8082")
public interface InventoryClient {

    Logger log = LoggerFactory.getLogger(InventoryClient.class);

//    @GetMapping("/api/inventory")
    @GetExchange("/api/inventory")
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackRoute")
    @Retry(name = "inventory")

    boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);

    default boolean fallbackRoute(String skuCode, Integer quantity, Throwable throwable) {
        log.info("Cannot get inventory for SKU: {} with quantity: {}. Error: {}", skuCode, quantity, throwable.getMessage());
        return false;
    }
}
