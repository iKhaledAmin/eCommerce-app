package com.ecommerce.eCommerce_App.address.model.mapper;


import com.ecommerce.eCommerce_App.address.model.dto.CityResponse;
import com.ecommerce.eCommerce_App.address.model.entity.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityResponse toResponse(City entity);
}
