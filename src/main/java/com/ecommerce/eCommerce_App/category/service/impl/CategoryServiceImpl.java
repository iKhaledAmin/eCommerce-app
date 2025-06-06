package com.ecommerce.eCommerce_App.category.service.impl;


import com.ecommerce.eCommerce_App.global.exception.ConflictException;
import com.ecommerce.eCommerce_App.global.utils.NonNullBeanUtils;
import com.ecommerce.eCommerce_App.image.ImageServiceImpl;
import com.ecommerce.eCommerce_App.category.model.dto.CategoryRequest;
import com.ecommerce.eCommerce_App.category.model.dto.CategoryResponse;
import com.ecommerce.eCommerce_App.category.model.entity.Category;
import com.ecommerce.eCommerce_App.image.Image;
import com.ecommerce.eCommerce_App.image.EntityType;
import com.ecommerce.eCommerce_App.category.model.mapper.CategoryMapper;
import com.ecommerce.eCommerce_App.category.repository.CategoryRepo;
import com.ecommerce.eCommerce_App.category.service.CategoryService;
import com.ecommerce.eCommerce_App.global.service.impl.EntityRetrievalServiceImpl;
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
    private final EntityRetrievalServiceImpl entityRetrievalService;
    private final NonNullBeanUtils nonNullBeanUtils;


    private Category save(Category category) {
        return categoryRepo.save(category);
    }

    public Category toEntity(CategoryRequest request) {
        return categoryMapper.toEntity(request);
    }

    public CategoryResponse toResponse(Category category) {
        CategoryResponse response = categoryMapper.toResponse(category);

        Optional<Image> image = imageService.getPrimaryImageByEntityIdAndEntityType(category.getId(),EntityType.CATEGORY);
        if (image.isPresent()) {
            response.setImageUrl(imageService.toURL(image.get()));
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
       return save(newCategory);
    }


    @Override
    public Category add(Category newCategory, MultipartFile imageFile) {
        Category savedCategory = add(newCategory);
        imageService.add(imageFile, savedCategory.getId(), EntityType.CATEGORY);
        return savedCategory;
    }

    @Override
    @Transactional
    public Category add(CategoryRequest categoryRequest,MultipartFile imageFile) {
        Category newCategory = toEntity(categoryRequest);
        return add(newCategory,imageFile);
    }
    @Override
    public Category update(Long categoryId, Category newCategory) {
        Category existingCategory = getById(categoryId);
        if (!existingCategory.getName().equals(newCategory.getName())) {
            throwExceptionIfCategoryNameAlreadyExists(newCategory.getName());
        }

        // Copy properties from newCategory to existingCategory, excluding the "id", "products" properties
        nonNullBeanUtils.copyProperties(newCategory, existingCategory, "id", "products");

        return save(existingCategory);
    }

    private void handleUpdatingCategoryImage(Long categoryId, Category newCategory, MultipartFile newImageFile) {
        // Delegate image handling to ImageService
        Image updatedImage = imageService.update(categoryId,EntityType.CATEGORY, newImageFile);

        // Update the profileImageUrl in the User entity
        if (updatedImage != null) {
            newCategory.setImageUrl(updatedImage.getStoragePath());
        } else {
            newCategory.setImageUrl(null); // Set to null if the image was deleted
        }
    }
    @Override
    public Category update(Long categoryId, Category newCategory, MultipartFile newImageFile) {
        Category updatedCategory = update(categoryId, newCategory);
        handleUpdatingCategoryImage(categoryId, updatedCategory, newImageFile);
        return updatedCategory;
    }

    @Override
    @Transactional
    public Category update(Long categoryId, CategoryRequest categoryRequest,MultipartFile newImageFile) {
        Category newCategory = toEntity(categoryRequest);
        return update(categoryId,newCategory,newImageFile);
    }


    @Transactional
    @Override
    public void delete(Long categoryId) {
        getById(categoryId);

        // Delete image
        Optional<Image> image = imageService.getOptionalByEntityIdAndEntityType(categoryId,EntityType.CATEGORY);
        if (image.isPresent()) {
            imageService.deleteById(image.get().getId());
        }

        categoryRepo.deleteById(categoryId);
    }

    @Override
    public Optional<Category> getOptionalById(Long categoryId) {
        return entityRetrievalService.getOptionalById(Category.class, categoryId);
    }

    @Override
    public Category getById(Long categoryId) {
        return entityRetrievalService.getById(Category.class, categoryId);
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

    @Override
    @Transactional
    public void increaseProductsCountByOne(Long categoryId) {
        if (categoryRepo.existsById(categoryId)) {
            categoryRepo.increaseProductsCountByOne(categoryId);
        }
    }

    @Override
    @Transactional
    public void decreaseProductsCountByOne(Long categoryId) {
        Optional<Category> category = getOptionalById(categoryId);
        if (category.isPresent()) {
            if (category.get().getProductsCount() > 0) {
                categoryRepo.decreaseProductsCountByOne(categoryId);
            }
        }
    }
}
