package com.ecommerce.eCommerce_App.stock.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class StockBatchRequest {

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Long itemQuantity;

    @NotNull(message = "Item buying price is required")
    @DecimalMin(value = "0.01", message = "Item buying price must be at least 0.01")
    @DecimalMax(value = "999999.99", message = "Item buying price must be less than or equal to 999999.99")
    private BigDecimal itemBuyingPrice;

    @NotNull(message = "Item expiration date is required")
    @Future(message = "Item expiration date must be in the future")
    private LocalDate itemExpirationDate;
}
