package com.ecommerce.eCommerce_App.stock.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RestockResponse extends StockBatchResponse {

    @JsonProperty("total_product_stock_quantity")
    private Long totalProductStockQuantity;

}
