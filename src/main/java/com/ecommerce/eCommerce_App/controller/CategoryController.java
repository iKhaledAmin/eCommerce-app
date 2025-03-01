package com.ecommerce.eCommerce_App.controller;


import com.ecommerce.eCommerce_App.model.dto.CategoryRequest;
import com.ecommerce.eCommerce_App.model.dto.CategoryResponse;
import com.ecommerce.eCommerce_App.model.entity.Category;
import com.ecommerce.eCommerce_App.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@ModelAttribute @Valid CategoryRequest categoryRequest) {
        CategoryResponse response = categoryService.toResponse(categoryService.add(categoryRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long categoryId, @ModelAttribute @Valid CategoryRequest categoryRequest) {
        CategoryResponse response = categoryService.toResponse(categoryService.update(categoryId, categoryRequest));
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
