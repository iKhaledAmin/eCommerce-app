package com.ecommerce.eCommerce_App.cart.exception;

public class CartOperationException extends RuntimeException {

    public CartOperationException() {
        super("Cart operation failed.");
    }
    public CartOperationException(String message) {
        super(message);
    }
}
