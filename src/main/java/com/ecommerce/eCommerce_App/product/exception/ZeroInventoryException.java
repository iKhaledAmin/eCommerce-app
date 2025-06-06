package com.ecommerce.eCommerce_App.product.exception;

public class ZeroInventoryException extends InventoryException{

    public ZeroInventoryException() {
        super("Product is not available.");
    }
    public ZeroInventoryException(String productName) {
        super(String.format("Product '%s' is out of stock", productName));
    }

}
