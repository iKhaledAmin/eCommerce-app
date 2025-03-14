package com.ecommerce.eCommerce_App.image;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
