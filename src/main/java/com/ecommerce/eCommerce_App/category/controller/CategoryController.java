package com.ecommerce.eCommerce_App.category.controller;


import com.ecommerce.eCommerce_App.category.model.dto.CategoryRequest;
import com.ecommerce.eCommerce_App.category.model.dto.CategoryResponse;
import com.ecommerce.eCommerce_App.category.model.entity.Category;
import com.ecommerce.eCommerce_App.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;


    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @RequestPart(name = "category_request") @Valid CategoryRequest categoryRequest,
            @RequestPart(name = "image_file", required = true) MultipartFile imageFile
    ) {
        CategoryResponse response = categoryService.toResponse(
                categoryService.add(categoryRequest, imageFile)
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long categoryId,
            @RequestPart(name = "category_request") @Valid CategoryRequest categoryRequest,
            @RequestPart(name = "new_image_file", required = true) MultipartFile newImageFile
    ) {
        CategoryResponse response = categoryService.toResponse(
                categoryService.update(categoryId, categoryRequest, newImageFile)
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.delete(categoryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long categoryId) {
        CategoryResponse response = categoryService.toResponse(categoryService.getById(categoryId));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<Category> categories = categoryService.getAll();

        List<CategoryResponse> responses = categories.stream()
                .map(categoryService::toResponse)
                .toList();

        return ResponseEntity.ok(responses);

    }
}
