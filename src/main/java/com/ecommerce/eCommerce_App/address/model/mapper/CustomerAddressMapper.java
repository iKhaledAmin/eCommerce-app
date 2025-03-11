package com.ecommerce.eCommerce_App.address.model.mapper;

import com.ecommerce.eCommerce_App.address.model.dto.CustomerAddressResponse;
import com.ecommerce.eCommerce_App.address.model.entity.CustomerAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerAddressMapper {

    @Mapping(source = "address.id", target = "id")
    @Mapping(source = "address.country.name", target = "country")
    @Mapping(source = "address.city.name", target = "city")
    @Mapping(source = "address.street", target = "street")
    @Mapping(source = "address.state", target = "state")
    @Mapping(source = "address.zipCode", target = "zipCode")
    @Mapping(source = "customer.id", target = "customerId")
    CustomerAddressResponse toResponse(CustomerAddress entity);
}
