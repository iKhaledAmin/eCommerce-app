package com.ecommerce.eCommerce_App.address.model.dto;

import com.ecommerce.eCommerce_App.address.model.enums.AddressType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCustomerAddressRequest extends AddressRequest {

    @NotNull
    @JsonProperty("address_type")
    private AddressType addressType;

    @NotNull
    @JsonProperty("is_default")
    private boolean isDefault;
}
