package com.ecommerce.eCommerce_App.users.service.impl;

import com.ecommerce.eCommerce_App.exception.ConflictException;
import com.ecommerce.eCommerce_App.global.utils.NonNullBeanUtils;
import com.ecommerce.eCommerce_App.image.Image;
import com.ecommerce.eCommerce_App.image.EntityType;
import com.ecommerce.eCommerce_App.service.EntityRetrievalService;
import com.ecommerce.eCommerce_App.image.ImageServiceImpl;
import com.ecommerce.eCommerce_App.users.model.dto.RegistrationRequest;
import com.ecommerce.eCommerce_App.users.model.dto.UserRequest;
import com.ecommerce.eCommerce_App.users.model.dto.UserResponse;
import com.ecommerce.eCommerce_App.users.model.entity.User;
import com.ecommerce.eCommerce_App.users.model.enums.UserRole;
import com.ecommerce.eCommerce_App.users.model.mapper.UserMapper;
import com.ecommerce.eCommerce_App.users.repository.UserRepo;
import com.ecommerce.eCommerce_App.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final ImageServiceImpl imageService;
    private final NonNullBeanUtils nonNullBeanUtils;
    private final EntityRetrievalService entityRetrievalService;

    @Override
    public User toEntity(RegistrationRequest request) {
        return userMapper.toEntity(request);
    }
    @Override
    public User toEntity(UserRequest request) {
        return userMapper.toEntity(request);
    }
    @Override
    public UserResponse toResponse(User user) {
        return userMapper.toResponse(user);
    }

    private User save(User user) {
        return userRepo.save(user);
    }

    private void throwExceptionIfAccountAlreadyExist(String account) {
        getOptionalByAccount(account)
                .ifPresent(user -> { throw new ConflictException("This account is already exist.");
                });
    }

    private User add(User newUser) {
        throwExceptionIfAccountAlreadyExist(newUser.getAccount());
        newUser.setDateOfJoining(LocalDate.now());
        return save(newUser);
    }



    @Override
    public User add(User newUser, UserRole userRepo) {
        throwExceptionIfAccountAlreadyExist(newUser.getAccount());
        newUser.setDateOfJoining(LocalDate.now());
        newUser.setUserRole(userRepo);

        //String hashedPassword = passwordEncoder.encode(newUser.getPassword());
        //newUser.setPassword(hashedPassword);

        return save(newUser);
    }

    @Override
    public User update(Long userId,User newUser) {
        User existingUser = getById(userId);

        // Copy properties from newUser to existingUser, excluding the "account", "password", "userRole", "dateOfJoining" and "profileImageUrl" fields
        nonNullBeanUtils.copyProperties(newUser, existingUser, "account", "password", "userRole", "dateOfJoining", "profileImageUrl");

        existingUser.setProfileImageUrl(newUser.getProfileImageUrl());

        // hash the password
        //String hashedPassword = passwordEncoder.encode(existingUser.getPassword());
        //existingUser.setPassword(hashedPassword);

        return save(existingUser);
    }

    private void handleUserImage(Long userId, User newUser, MultipartFile newImageFile) {
        // Delegate image handling to ImageService
        Image updatedImage = imageService.update(userId, EntityType.USER, newImageFile);

        // Update the profileImageUrl in the User entity
        if (updatedImage != null) {
            newUser.setProfileImageUrl(updatedImage.getStoragePath());
        } else {
            newUser.setProfileImageUrl(null); // Set to null if the image was deleted
        }
    }

    @Override
    public User update(Long userId,User newUser, MultipartFile newImageFile) {
        handleUserImage(userId,newUser,newImageFile);
        return update(userId,newUser);
    }


    @Override
    public User editProfile(Long userId, UserRequest userRequest, MultipartFile newImageFile) {
        User newUser = userMapper.toEntity(userRequest);
        return update(userId,newUser,newImageFile);
    }


    @Override
    public Optional<User> getOptionalById(Long userId){
        return entityRetrievalService.getOptionalById(User.class,userId);
    }
    @Override
    public User getById(Long userId){
        return entityRetrievalService.getById(User.class,userId);
    }
    @Override
    public Optional<User> getOptionalByAccount(String account) {
        return userRepo.findByAccount(account);
    }
}
