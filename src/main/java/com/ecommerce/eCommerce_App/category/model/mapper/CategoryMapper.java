package com.ecommerce.eCommerce_App.category.model.mapper;


import com.ecommerce.eCommerce_App.category.model.dto.CategoryRequest;
import com.ecommerce.eCommerce_App.category.model.dto.CategoryResponse;
import com.ecommerce.eCommerce_App.category.model.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//@Mapper(componentModel = "spring", uses = ImageMapper.class)
@Mapper(componentModel = "spring")
public interface CategoryMapper {

    //@Mapping(target = "image", ignore = true) // Map image manually
    @Mapping(target = "products", expression = "java(new java.util.ArrayList<>())")
    Category toEntity(CategoryRequest request);


    CategoryResponse toResponse(Category entity);


}
