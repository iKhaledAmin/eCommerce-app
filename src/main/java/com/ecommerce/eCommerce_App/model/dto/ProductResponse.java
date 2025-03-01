package com.ecommerce.eCommerce_App.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventoryQuantity;
    private String description;
    private String categoryName; // Include category name instead of full object
    private String imageUrl;
}
