package com.maniraj.order.dto;

import com.maniraj.order.model.UserDetails;

import java.math.BigDecimal;

public record OrderRequest(Long id, String orderNumber, String skuCode, BigDecimal price, Integer quantity, UserDetails userDetails) {
}
