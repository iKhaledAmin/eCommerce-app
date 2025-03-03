package com.ecommerce.eCommerce_App.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse {
    @JsonProperty("image_id")
    private Long imageId;

    @JsonProperty("image_name")
    private String imageName;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("is_primary")
    private boolean isPrimary;
}
