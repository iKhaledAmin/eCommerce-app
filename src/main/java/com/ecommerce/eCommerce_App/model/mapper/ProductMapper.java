package com.ecommerce.eCommerce_App.model.mapper;


import com.ecommerce.eCommerce_App.model.dto.ProductAddRequest;
import com.ecommerce.eCommerce_App.model.dto.ProductResponse;
import com.ecommerce.eCommerce_App.model.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductAddRequest request);

    ProductResponse toResponse(Product entity);

}
