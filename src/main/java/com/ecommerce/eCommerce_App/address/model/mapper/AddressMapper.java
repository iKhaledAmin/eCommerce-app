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

    Address toEntity(AddressRequest request);

    @Mapping(target = "city",source = "entity.city.name")
    @Mapping(target = "country",source = "entity.country.name")
    AddressResponse toResponse(Address entity);

    @Mapping(target = "city", source = "entity.address.city.name")
    @Mapping(target = "country", source = "entity.address.country.name")
    @Mapping(target = "street", source = "entity.address.street")
    @Mapping(target = "state", source = "entity.address.state")
    @Mapping(target = "zipCode", source = "entity.address.zipCode")
    CustomerAddressResponse toResponse(CustomerAddress entity);

}
