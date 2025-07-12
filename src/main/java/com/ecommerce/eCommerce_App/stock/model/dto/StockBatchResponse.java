package com.ecommerce.eCommerce_App.stock.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class StockBatchResponse {

    private Long id;

    @JsonProperty("batch_code")
    private String batchCode;

    @JsonProperty("product_batch_number")
    private Long productBatchNumber;

    @JsonProperty("arrival_date")
    private LocalDateTime arrivalDate;

    @JsonProperty("item_quantity")
    private Long itemQuantity;

    @JsonProperty("item_buying_price")
    private BigDecimal itemBuyingPrice;

    @JsonProperty("item_expiration_date")
    private LocalDate itemExpirationDate;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("product_name")
    private String productName;
}
