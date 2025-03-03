package com.ecommerce.eCommerce_App.model.mapper;


import com.ecommerce.eCommerce_App.model.dto.ProductRequest;
import com.ecommerce.eCommerce_App.model.dto.ProductResponse;
import com.ecommerce.eCommerce_App.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequest request);


    @Mapping(target = "categoryName", source = "category.name") // Map category.name to categoryName
    ProductResponse toResponse(Product entity);

}
