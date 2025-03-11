package com.ecommerce.eCommerce_App.users.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse extends UserResponse {

    @JsonProperty("loyalty_points")
    private Integer loyaltyPoints;

    @JsonProperty("total_spent")
    private double totalSpent;

    @JsonProperty("last_purchase_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate lastPurchaseDate;
}
