package com.ecommerce.eCommerce_App.service;


import com.ecommerce.eCommerce_App.model.dto.CategoryRequest;
import com.ecommerce.eCommerce_App.model.dto.CategoryResponse;
import com.ecommerce.eCommerce_App.model.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category toEntity(CategoryRequest request);
    CategoryResponse toResponse(Category entity);

    Boolean IsExistByName(String categoryName);


    Category add(Category newCategory);
    Category add(CategoryRequest categoryRequest);

    Category update(Long categoryId,Category newCategory);
    Category update(Long categoryId, CategoryRequest categoryRequest);


    void delete(Long id);


    Optional<Category> getOptionalById(Long categoryId);
    Category getById(Long categoryId);

    Optional<Category> getOptionalByName(String categoryName);
    Category getByName(String categoryName);


    List<Category> getAll();

}
