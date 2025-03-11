package com.ecommerce.eCommerce_App.users.model.mapper;

import com.ecommerce.eCommerce_App.users.model.dto.CustomerResponse;
import com.ecommerce.eCommerce_App.users.model.dto.RegistrationRequest;
import com.ecommerce.eCommerce_App.users.model.entity.Customer;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerResponse toResponse(Customer customer);
    Customer toEntity(RegistrationRequest request);
}
