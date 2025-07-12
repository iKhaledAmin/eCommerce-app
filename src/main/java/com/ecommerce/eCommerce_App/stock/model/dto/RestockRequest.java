package com.ecommerce.eCommerce_App.stock.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;



@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RestockRequest extends StockBatchRequest {
    @NotNull(message = "Product ID is required")
    @JsonProperty("product_id")
    private Long productId;
}
