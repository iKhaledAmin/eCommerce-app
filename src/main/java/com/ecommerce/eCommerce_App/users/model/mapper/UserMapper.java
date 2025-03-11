package com.ecommerce.eCommerce_App.users.model.mapper;

import com.ecommerce.eCommerce_App.users.model.dto.RegistrationRequest;
import com.ecommerce.eCommerce_App.users.model.dto.UserRequest;
import com.ecommerce.eCommerce_App.users.model.dto.UserResponse;
import com.ecommerce.eCommerce_App.users.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);

    User toEntity(UserRequest request);
    User toEntity(RegistrationRequest request);
}
