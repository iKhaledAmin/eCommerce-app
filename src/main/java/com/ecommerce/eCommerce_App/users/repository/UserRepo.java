package com.ecommerce.eCommerce_App.users.repository;

import com.ecommerce.eCommerce_App.global.repository.GenericRepository;
import com.ecommerce.eCommerce_App.users.model.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends GenericRepository<User, Long> {
    @Override
    default Class<User> getEntityClass() {
        return User.class;
    }

    Optional<User> findByAccount(String account);
}
