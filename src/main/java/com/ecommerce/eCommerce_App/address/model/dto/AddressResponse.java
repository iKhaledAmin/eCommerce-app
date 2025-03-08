package com.ecommerce.eCommerce_App.address.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {

    @JsonProperty("address_id")
    private Long id;

    @JsonProperty("country_name")
    private String country;

    @JsonProperty("city_name")
    private String city;

    @JsonProperty("street_name")
    private String street;

    @JsonProperty("state_name")
    private String state;

    @JsonProperty("zip_code")
    private String zipCode;
}
