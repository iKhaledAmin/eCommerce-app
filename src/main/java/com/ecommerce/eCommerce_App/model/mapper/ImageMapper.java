package com.ecommerce.eCommerce_App.model.mapper;


import com.ecommerce.eCommerce_App.model.dto.ImageResponse;
import com.ecommerce.eCommerce_App.model.entity.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    @Mapping(target = "fileUrl", source = "fileName", qualifiedByName = "generateFileUrl")
    ImageResponse toResponse(Image entity);

    @Named("generateFileUrl")
    static String generateFileUrl(String fileName) {
        return "uploads/images/" + fileName;  // Replace with your actual file storage path
    }
}
