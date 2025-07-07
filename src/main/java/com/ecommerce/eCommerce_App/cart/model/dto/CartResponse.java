package com.ecommerce.eCommerce_App.cart.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("customer_id")
    private Long customerId;

    // Cart summary
    @JsonProperty("product_count")
    private Long productCount; // the number of distinct products
    @JsonProperty("item_count")
    private Long itemCount;    // the number all of items
    @JsonProperty("total_price")
    private BigDecimal totalPrice; // the total amount of the cart

    // Cart items
    @JsonProperty("items")
    private List<CartItemResponse> items;

}
