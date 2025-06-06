package com.ecommerce.eCommerce_App.global.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository <T, ID> extends JpaRepository<T, ID> {
    Class<T> getEntityClass();
}
