package com.maniraj.orderservice.exception;

public class OrderProcessingException extends RuntimeException {
    private final String errorCode;

    public OrderProcessingException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}