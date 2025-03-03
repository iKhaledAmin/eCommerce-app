package com.ecommerce.eCommerce_App.repository;

import com.ecommerce.eCommerce_App.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {
    Optional<Category> findByName(String name);

    boolean existsByName(String name);
    boolean existsById(Long id);


    @Modifying
    @Query("UPDATE Category c SET c.productsCount = c.productsCount + 1 WHERE c.id = :categoryId")
    void increaseProductsCountByOne(Long categoryId);

    @Modifying
    @Query("UPDATE Category c SET c.productsCount = c.productsCount - 1 WHERE c.id = :categoryId AND c.productsCount > 0")
    void decreaseProductsCountByOne(Long categoryId);
}
