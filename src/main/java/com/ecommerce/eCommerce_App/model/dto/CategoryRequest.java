package com.ecommerce.eCommerce_App.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    private String name;
    private String description;
    private MultipartFile image; // Upload image file when adding a category
}
