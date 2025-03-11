package com.ecommerce.eCommerce_App.users.service;


import com.ecommerce.eCommerce_App.users.model.dto.CustomerResponse;
import com.ecommerce.eCommerce_App.users.model.dto.RegistrationRequest;
import com.ecommerce.eCommerce_App.users.model.dto.UserRequest;
import com.ecommerce.eCommerce_App.users.model.entity.Customer;
import com.ecommerce.eCommerce_App.users.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    CustomerResponse toResponse(Customer customer);
    Customer toEntity(RegistrationRequest request);

    Customer register(RegistrationRequest registrationRequest);
    Customer editProfile(Long customerId, UserRequest userRequest, MultipartFile newImageFile);


    Optional<Customer> getOptionalById(Long customerId);
    Customer getById(Long customerId);
    List<Customer> getAll();



}
