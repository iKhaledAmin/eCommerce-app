package com.ecommerce.eCommerce_App.product.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    @JsonProperty("product_id")
    private Long id;

    @JsonProperty("product_name")
    private String name;

    @JsonProperty("product_brand")
    private String brand;

    @JsonProperty("product_price")
    private BigDecimal price;

    @JsonProperty("product_inventory_quantity")
    private int inventoryQuantity;

    @JsonProperty("product_description")
    private String description;

    @JsonProperty("product_category")
    private String categoryName; // Include category name instead of full object

    @JsonProperty("product_image_url")
    private String imageUrl;

    @JsonProperty("product_status")
    private String status;

}
