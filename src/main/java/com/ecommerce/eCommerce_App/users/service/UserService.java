package com.ecommerce.eCommerce_App.users.service;

import com.ecommerce.eCommerce_App.users.model.dto.RegistrationRequest;
import com.ecommerce.eCommerce_App.users.model.dto.UserRequest;
import com.ecommerce.eCommerce_App.users.model.dto.UserResponse;
import com.ecommerce.eCommerce_App.users.model.entity.User;
import com.ecommerce.eCommerce_App.users.model.enums.UserRole;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface UserService {

    User toEntity(RegistrationRequest request);
    User toEntity(UserRequest request);
    UserResponse toResponse(User user);


    User add(User newUser, UserRole userRepo);
    User update(Long userId,User newUser);
    User update(Long userId,User newUser, MultipartFile newImageFile);
    User editProfile(Long userId, UserRequest userRequest, MultipartFile newImageFile);

    Optional<User> getOptionalById(Long userId);
    User getById(Long userId);
    Optional<User> getOptionalByAccount(String account);
}
