package com.ecommerce.eCommerce_App.model.mapper;


import com.ecommerce.eCommerce_App.model.entity.Image;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Value;

@Mapper(componentModel = "spring")
public interface ImageMapper {


    default String toResponse(Image entity) {
        if (entity == null || entity.getStoragePath() == null) {
            return null;
        }
        return "http://localhost:7070/" + entity.getStoragePath(); // Adjust base URL as needed
    }
}
