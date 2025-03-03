package com.ecommerce.eCommerce_App.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {

    @JsonProperty("category_id")
    private Long id;

    @JsonProperty("category_name")
    private String name;

    @JsonProperty("category_description")
    private String description;

    @JsonProperty("category_products_count")
    private int productsCount;

    @JsonProperty("category_image_url")
    private String imageUrl;
}