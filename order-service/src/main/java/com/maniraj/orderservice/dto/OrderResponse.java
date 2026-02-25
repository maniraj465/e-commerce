package com.maniraj.orderservice.dto;

import java.time.LocalDateTime;

public record OrderResponse(String message, String orderNumber, LocalDateTime timestamp) {
}
