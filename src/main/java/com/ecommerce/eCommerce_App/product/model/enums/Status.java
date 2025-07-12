package com.ecommerce.eCommerce_App.product.model.enums;

public enum Status {
    ACTIVE("Visible to customers"),
    INACTIVE("Hidden from customers");

    private final String description;

    Status(String description) {
        this.description = description;
    }
}
