package com.ecommerce.eCommerce_App.users.service.impl;


import com.ecommerce.eCommerce_App.service.EntityRetrievalService;
import com.ecommerce.eCommerce_App.users.model.dto.CustomerResponse;
import com.ecommerce.eCommerce_App.users.model.dto.RegistrationRequest;
import com.ecommerce.eCommerce_App.users.model.dto.UserRequest;
import com.ecommerce.eCommerce_App.users.model.entity.Customer;
import com.ecommerce.eCommerce_App.users.model.enums.UserRole;
import com.ecommerce.eCommerce_App.users.model.mapper.CustomerMapper;
import com.ecommerce.eCommerce_App.users.repository.CustomerRepo;
import com.ecommerce.eCommerce_App.users.service.CustomerService;
import com.ecommerce.eCommerce_App.users.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;
    private final CustomerMapper customerMapper;
    private final UserService userService;
    private final EntityRetrievalService entityRetrievalService;


    @Override
    public CustomerResponse toResponse(Customer customer) {
        return customerMapper.toResponse(customer);
    }

    public Customer toEntity(RegistrationRequest request) {
        return customerMapper.toEntity(request);
    }

    private Customer save(Customer customer) {
        return customerRepo.save(customer);
    }

    public Customer add(Customer newCustomer) {
        // Set default values
        newCustomer.setUserRole(UserRole.ROLE_CUSTOMER);
        newCustomer.setDateOfJoining(LocalDate.now());
        newCustomer.setLoyaltyPoints(0);
        newCustomer.setTotalSpent(0.0);
        newCustomer.setLastPurchaseDate(null);

        return save(newCustomer);
    }

    @Transactional
    @Override
    public Customer register(RegistrationRequest registrationRequest){
        Customer newCustomer = toEntity(registrationRequest);
        newCustomer =(Customer) userService.add(newCustomer,UserRole.ROLE_CUSTOMER);
        return add(newCustomer);
    }

    @Transactional
    @Override
    public Customer editProfile(Long customerId, UserRequest userRequest, MultipartFile newImageFile) {
        getById(customerId);
        return (Customer)userService.editProfile(customerId,userRequest,newImageFile);
    }

    public Optional<Customer> getOptionalById(Long customerId) {
        return entityRetrievalService.getOptionalById(Customer.class,customerId);
    }
    public Customer getById(Long customerId) {
        return entityRetrievalService.getById(Customer.class,customerId);
    }
    public List<Customer> getAll() {
        return customerRepo.findAll();
    }

}
