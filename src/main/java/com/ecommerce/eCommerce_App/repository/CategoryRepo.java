package com.ecommerce.eCommerce_App.repository;

import com.ecommerce.eCommerce_App.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {
    Optional<Category> findByName(String name);

    boolean existsByName(String name);
}
