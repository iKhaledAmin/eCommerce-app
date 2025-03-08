package com.ecommerce.eCommerce_App.address.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityResponse {

    @JsonProperty("city_id")
    private Long id;

    @JsonProperty("city_name")
    private String name;
}
