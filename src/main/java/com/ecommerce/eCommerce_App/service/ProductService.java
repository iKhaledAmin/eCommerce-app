package com.ecommerce.eCommerce_App.service;

import com.ecommerce.eCommerce_App.model.entity.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product add(Product product);
    Product update(Long productId,Product newProduct);
    void delete(Long id);
    Optional<Product> getOptionalById(Long id);
    Product getById(Long id);
    List<Product> getAll();
    List<Product> getAllByName(String productName);
    List<Product> getByCategory(String categoryName);
    List<Product> getBybrand(String brand);
    List<Product> getBybrandAndName(String brand,String productName);
    List<Product> getByCategoryAndBrand(String categoryName,String brand);
    Long countByBrandAndName(String brand,String productName);
}
