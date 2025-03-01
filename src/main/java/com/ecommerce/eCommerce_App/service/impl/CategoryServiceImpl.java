package com.ecommerce.eCommerce_App.service.impl;


import com.ecommerce.eCommerce_App.exception.ConflictException;
import com.ecommerce.eCommerce_App.global.utils.NonNullBeanUtils;
import com.ecommerce.eCommerce_App.model.dto.CategoryRequest;
import com.ecommerce.eCommerce_App.model.dto.CategoryResponse;
import com.ecommerce.eCommerce_App.model.entity.Category;
import com.ecommerce.eCommerce_App.model.entity.Image;
import com.ecommerce.eCommerce_App.model.enums.EntityType;
import com.ecommerce.eCommerce_App.model.mapper.CategoryMapper;
import com.ecommerce.eCommerce_App.repository.CategoryRepo;
import com.ecommerce.eCommerce_App.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final CategoryMapper categoryMapper;
    private final ImageServiceImpl imageService;
    private final NonNullBeanUtils nonNullBeanUtils;


    private Category save(Category category) {
        return categoryRepo.save(category);
    }

    public Category toEntity(CategoryRequest request) {
        return categoryMapper.toEntity(request);
    }

    public CategoryResponse toResponse(Category category) {
        CategoryResponse response = categoryMapper.toResponse(category);

        Optional<Image> image = imageService.getOptionalByEntityId(category.getId());
        if (image.isPresent()) {
            response.setImageUrl(imageService.toResponse(image.get()));
        }

        return response;
    }

    public Boolean IsExistByName(String categoryName) {
        return categoryRepo.existsByName(categoryName);
    }
    private void throwExceptionIfCategoryNameAlreadyExists(String categoryName) {
        if (IsExistByName(categoryName)) {
            throw new ConflictException("Category name already exists!");
        }

    }
    @Override
    public Category add(Category newCategory) {
       throwExceptionIfCategoryNameAlreadyExists(newCategory.getName());
       return categoryRepo.save(newCategory);
    }

    @Transactional
    @Override
    public Category add(Category newCategory, MultipartFile image) {

        Category savedCategory = add(newCategory);
        imageService.add(image, savedCategory.getId(), EntityType.CATEGORY);
        return savedCategory;
    }


    @Override
    public Category add(CategoryRequest categoryRequest) {
        Category newCategory = toEntity(categoryRequest);
        return add(newCategory,categoryRequest.getImage());
    }
    @Override
    public Category update(Long categoryId, Category newCategory) {
        Category existingCategory = getById(categoryId);

        // Copy properties from newCategory to existingCategory, excluding the "id", "product", and "image" properties
        nonNullBeanUtils.copyProperties(newCategory, existingCategory, "id", "product", "image");

        return save(existingCategory);
    }

    @Override
    public Category update(Long categoryId, Category newCategory, MultipartFile image) {

        Category updatedCategory = update(categoryId, newCategory);

        Optional<Image> existingImage = imageService.getOptionalByEntityId(categoryId);
        if (existingImage.isPresent()) {
            imageService.update(existingImage.get().getId(), image);
        }

        return updatedCategory;
    }

    @Override
    @Transactional
    public Category update(Long categoryId, CategoryRequest categoryRequest) {
        Category newCategory = toEntity(categoryRequest);
        return update(categoryId,newCategory,categoryRequest.getImage());
    }


    @Transactional
    @Override
    public void delete(Long categoryId) {
        getById(categoryId);

        // Delete image
        Optional<Image> image = imageService.getOptionalByEntityId(categoryId);
        if (image.isPresent()) {
            imageService.deleteById(image.get().getId());
        }

        categoryRepo.deleteById(categoryId);
    }

    @Override
    public Optional<Category> getOptionalById(Long categoryId) {
        return categoryRepo.findById(categoryId);
    }

    @Override
    public Category getById(Long categoryId) {
        return getOptionalById(categoryId).orElseThrow(
                () -> new NoSuchElementException("Category not found!")
        );
    }

    @Override
    public Optional<Category> getOptionalByName(String categoryName) {
        return categoryRepo.findByName(categoryName);
    }

    @Override
    public Category getByName(String categoryName) {
        return getOptionalByName(categoryName).orElseThrow(
                () -> new NoSuchElementException("Category not found!")
        );
    }

    @Override
    public List<Category> getAll() {
        return categoryRepo.findAll();
    }
}
