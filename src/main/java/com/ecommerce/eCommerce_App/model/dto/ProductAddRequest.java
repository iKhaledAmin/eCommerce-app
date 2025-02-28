package com.ecommerce.eCommerce_App.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAddRequest {
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventoryQuantity;
    private String description;
    private List<MultipartFile> images; // List of images to upload
}
