package com.maniraj.orderservice.dto;

import com.maniraj.orderservice.model.UserDetails;

import java.math.BigDecimal;

public record OrderRequest(Long id, String orderNumber, String skuCode, BigDecimal price, Integer quantity, UserDetails userDetails) {
}
