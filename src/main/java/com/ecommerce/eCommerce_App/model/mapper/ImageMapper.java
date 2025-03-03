package com.ecommerce.eCommerce_App.model.mapper;


import com.ecommerce.eCommerce_App.model.dto.ImageResponse;
import com.ecommerce.eCommerce_App.model.entity.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Value;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    @Mapping(target = "imageId", source = "id")
    @Mapping(target = "imageUrl", expression = "java(toURL(entity))")
    ImageResponse toResponse(Image entity);

    default String toURL(Image entity) {
        if (entity == null || entity.getStoragePath() == null) {
            return null;
        }
        return "http://localhost:7070/" + entity.getStoragePath(); // Adjust base URL as needed
    }
}
