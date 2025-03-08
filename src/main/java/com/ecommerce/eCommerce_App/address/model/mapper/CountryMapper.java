package com.ecommerce.eCommerce_App.address.model.mapper;

import com.ecommerce.eCommerce_App.address.model.dto.CountryResponse;
import com.ecommerce.eCommerce_App.address.model.entity.Country;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    CountryResponse toResponse(Country entity);
}
