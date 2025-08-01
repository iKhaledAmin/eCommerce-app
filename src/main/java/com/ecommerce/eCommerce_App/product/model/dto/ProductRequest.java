package com.ecommerce.eCommerce_App.product.model.dto;

import com.ecommerce.eCommerce_App.product.model.enums.Status;
import com.ecommerce.eCommerce_App.stock.model.dto.StockBatchRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotBlank(message = "Name is required")
    @NotNull(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "Description is required")
    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    private String description;

    @NotNull(message = "Brand is required")
    @NotBlank(message = "Brand is required")
    @Size(min = 2, max = 50, message = "Brand must be between 2 and 50 characters")
    @JsonProperty("brand")
    private String brand;

    @DecimalMin(value = "0.01", message = "Selling price must be greater than or equal to 0.01")
    @DecimalMax(value = "999999.99", message = "Selling price must be less than or equal to 999999.99")
    @JsonProperty("selling_price")
    private BigDecimal sellingPrice;

    @JsonProperty("status")
    private Status status = Status.INACTIVE;

    @NotNull(message = "Category id is required")
    @JsonProperty("category_id")
    private Long categoryId;


    @Valid
    @JsonProperty("stock_details")
    private StockBatchRequest stockDetails;

}
