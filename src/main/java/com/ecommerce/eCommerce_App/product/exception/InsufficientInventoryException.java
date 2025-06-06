package com.ecommerce.eCommerce_App.product.exception;

public class InsufficientInventoryException extends InventoryException {
    public InsufficientInventoryException() {
        super("Product quantity not enough.");
    }

    public InsufficientInventoryException(long available, long requested) {
        super(String.format("Only %d items available, requested %d", available, requested));
    }
}
