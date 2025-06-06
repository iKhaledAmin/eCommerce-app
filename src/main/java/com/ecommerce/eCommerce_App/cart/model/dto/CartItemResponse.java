package com.ecommerce.eCommerce_App.cart.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
    private Long productId;
    private String productName;
    private String productImageUrl;
    private BigDecimal productPrice; // unit price
    private Integer quantity;  // number of this product in the cart
    private BigDecimal itemTotalAmount; // total price of this item = unit price * quantity

}
