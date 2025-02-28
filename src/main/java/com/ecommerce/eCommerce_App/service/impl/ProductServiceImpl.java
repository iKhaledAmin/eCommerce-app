package com.ecommerce.eCommerce_App.service.impl;


import com.ecommerce.eCommerce_App.model.entity.Product;
import com.ecommerce.eCommerce_App.model.mapper.ProductMapper;
import com.ecommerce.eCommerce_App.repository.ProductRepo;
import com.ecommerce.eCommerce_App.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;


    @Override
    public Product add(Product product) {
        return null;
    }

    @Override
    public Product update(Long productId, Product newProduct) {
        return null;
    }

    @Override
    public void delete(Long id) {
        getById(id);
        productRepo.deleteById(id);
    }

    @Override
    public Optional<Product> getOptionalById(Long id) {
        return productRepo.findById(id);
    }

    @Override
    public Product getById(Long id) {
        return getOptionalById(id).orElseThrow(
                () -> new NoSuchElementException("Product not found!")
        );
    }

    @Override
    public List<Product> getAll() {
        return productRepo.findAll();
    }

    @Override
    public List<Product> getAllByName(String productName) {
        return productRepo.findAllByName(productName);
    }

    @Override
    public List<Product> getByCategory(String categoryName) {
        return null;
    }

    @Override
    public List<Product> getBybrand(String brand) {
        return productRepo.findAllByBrand(brand);
    }

    @Override
    public List<Product> getBybrandAndName(String brand, String productName) {
        return productRepo.findAllByBrandAndName(brand,productName);
    }

    @Override
    public List<Product> getByCategoryAndBrand(String categoryName, String brand) {
        return null;
    }

    @Override
    public Long countByBrandAndName(String brand, String productName) {
        return productRepo.countByBrandAndName(brand,productName);
    }
}
