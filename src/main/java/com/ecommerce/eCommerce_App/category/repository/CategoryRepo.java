package com.ecommerce.eCommerce_App.category.repository;

import com.ecommerce.eCommerce_App.category.model.entity.Category;
import com.ecommerce.eCommerce_App.global.repository.GenericRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends GenericRepository<Category, Long> {
    @Override
    default Class<Category> getEntityClass() {
        return Category.class;
    }
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
