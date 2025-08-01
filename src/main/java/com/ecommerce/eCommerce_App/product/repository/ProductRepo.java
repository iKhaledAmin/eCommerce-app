package com.ecommerce.eCommerce_App.product.repository;


import com.ecommerce.eCommerce_App.product.model.entity.Product;
import com.ecommerce.eCommerce_App.global.repository.GenericRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends GenericRepository<Product, Long> {
    @Override
    default Class<Product> getEntityClass() {
        return Product.class;
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.id = :productId")
    Optional<Product> findByIdForCriticalUpdate(Long productId);

    List<Product> findAllByName(String productName);

    List<Product> findAllByBrand(String brand);

    List<Product> findAllByBrandAndName(String brand, String productName);

    Long countByBrandAndName(String brand, String productName);

    List<Product> findAllByCategory_Id(Long categoryId);



}
