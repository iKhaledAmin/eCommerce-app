package com.ecommerce.eCommerce_App.model.mapper;


import com.ecommerce.eCommerce_App.model.dto.CategoryRequest;
import com.ecommerce.eCommerce_App.model.dto.CategoryResponse;
import com.ecommerce.eCommerce_App.model.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ImageMapper.class)
public interface CategoryMapper {

    @Mapping(target = "image", ignore = true) // Map image manually
    @Mapping(target = "products", expression = "java(new java.util.ArrayList<>())")
    Category toEntity(CategoryRequest request);

    @Mapping(target = "image", source = "image") // Use ImageMapper for image mapping
    CategoryResponse toResponse(Category entity);


}
