package com.ecommerce.eCommerce_App.category.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {


    @NotBlank(message = "Name is required")
    @NotNull(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @JsonProperty("name")
    private String name;

    @NotBlank(message = "Description is required")
    @NotNull(message = "Name is required")
    @Size(min = 10, max = 255, message = "Description must be between 10 and 255 characters")
    @JsonProperty("description")
    private String description;

}
