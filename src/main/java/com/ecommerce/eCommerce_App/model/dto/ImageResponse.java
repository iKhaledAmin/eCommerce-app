package com.ecommerce.eCommerce_App.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse {
    private Long id;
    private String fileName;
    private String fileType;
    private String fileUrl; // This can be used for frontend to access the image
}