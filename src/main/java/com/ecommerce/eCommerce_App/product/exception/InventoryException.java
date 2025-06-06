package com.ecommerce.eCommerce_App.product.exception;

public abstract class InventoryException extends RuntimeException {
    public InventoryException(String message) {
        super(message);
    }
}
