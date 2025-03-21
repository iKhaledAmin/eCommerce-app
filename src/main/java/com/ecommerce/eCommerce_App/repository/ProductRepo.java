package com.ecommerce.eCommerce_App.repository;


import com.ecommerce.eCommerce_App.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends GenericRepository<Product, Long> {
    @Override
    default Class<Product> getEntityClass() {
        return Product.class;
    }
    List<Product> findAllByName(String productName);

    List<Product> findAllByBrand(String brand);

    List<Product> findAllByBrandAndName(String brand, String productName);

    Long countByBrandAndName(String brand, String productName);

    List<Product> findAllByCategory_Id(Long categoryId);
}
