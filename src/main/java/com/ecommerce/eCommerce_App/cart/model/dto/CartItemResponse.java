package com.ecommerce.eCommerce_App.cart.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("product_id")
    private Long productId;
    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("product_image_url")
    private String productImageUrl;
    @JsonProperty("product_price")
    private BigDecimal productPrice; // unit price
    @JsonProperty("quantity")
    private Integer quantity;  // number of this product in the cart
    @JsonProperty("item_total_amount")
    private BigDecimal itemTotalAmount; // total price of this item = unit price * quantity
}
