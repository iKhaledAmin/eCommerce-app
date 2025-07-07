package com.ecommerce.eCommerce_App.order.model.enums;

public enum OrderStatus {
    PENDING,        // Order created, awaiting payment
    PROCESSING,     // Payment received, preparing shipment
    SHIPPED,       // Items dispatched to carrier
    DELIVERED,     // Customer received items
    CANCELLED,     // Order cancelled before shipping
    REFUNDED,      // Order refunded after payment
    FAILED         // Payment failed
}
