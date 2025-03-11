package com.ecommerce.eCommerce_App.users.repository;

import com.ecommerce.eCommerce_App.repository.GenericRepository;
import com.ecommerce.eCommerce_App.users.model.entity.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepo extends GenericRepository<Customer, Long> {
    @Override
    default Class<Customer> getEntityClass() {
        return Customer.class;
    }

    Optional<Customer> findByAccount(String account);
}
