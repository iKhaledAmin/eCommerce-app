package com.ecommerce.eCommerce_App.address.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {
    @NotNull
    @JsonProperty("country_id")
    private Long countryId;

    @NotNull
    @JsonProperty("city_id")
    private Long cityId;


    @NotNull
    @NotBlank
    @JsonProperty("street")
    private String street;

    @JsonProperty("state")
    private String state;

    @NotNull
    @JsonProperty("zip_code")
    private String zipCode;
}
