package com.ecommerce.eCommerce_App.address.model.mapper;

import com.ecommerce.eCommerce_App.address.model.dto.AddressRequest;
import com.ecommerce.eCommerce_App.address.model.dto.AddressResponse;
import com.ecommerce.eCommerce_App.address.model.dto.CustomerAddressResponse;
import com.ecommerce.eCommerce_App.address.model.entity.Address;
import com.ecommerce.eCommerce_App.address.model.entity.CustomerAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "customerAddresses",expression = "java(new java.util.ArrayList<>())")
    Address toEntity(AddressRequest request);

    @Mapping(target = "city",source = "entity.city.name")
    @Mapping(target = "country",source = "entity.country.name")
    AddressResponse toResponse(Address entity);


}
