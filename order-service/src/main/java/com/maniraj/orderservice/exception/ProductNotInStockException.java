package com.maniraj.orderservice.exception;

public class ProductNotInStockException extends RuntimeException {

    private final String skuCode;
    private final Integer requestedQuantity;

    public ProductNotInStockException(String skuCode, Integer requestedQuantity) {
        super(String.format("Product with SKU code '%s' is not in stock (requested quantity: %d)",
                skuCode, requestedQuantity));
        this.skuCode = skuCode;
        this.requestedQuantity = requestedQuantity;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public Integer getRequestedQuantity() {
        return requestedQuantity;
    }
}
