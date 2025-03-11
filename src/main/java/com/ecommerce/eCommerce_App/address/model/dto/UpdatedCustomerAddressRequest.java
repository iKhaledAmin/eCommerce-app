package com.ecommerce.eCommerce_App.address.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedCustomerAddressRequest extends AddCustomerAddressRequest {
    @NotNull
    @JsonProperty("address_id")
    private Long addressId;
}
