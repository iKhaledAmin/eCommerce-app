package com.ecommerce.eCommerce_App.address.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryResponse {
    @JsonProperty("country_id")
    private Long id;
    @JsonProperty("country_name")
    private String name;
    @JsonProperty("country_code")
    private String code;
}
