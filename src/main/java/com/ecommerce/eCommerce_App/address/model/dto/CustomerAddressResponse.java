package com.ecommerce.eCommerce_App.address.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddressResponse extends AddressResponse {

    @JsonProperty("address_type")
    private String addressType;

    @JsonProperty("is_default")
    private boolean isDefault;
}
