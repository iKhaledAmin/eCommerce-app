package com.ecommerce.eCommerce_App.cart.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {

    private Long customerId;

    // Cart summary
    private Long productCount; // the number of distinct products
    private Long itemCount;    // the number all of items
    private BigDecimal totalAmount; // the total amount of the cart

    // Cart items
    private List<CartItemResponse> items;

}
