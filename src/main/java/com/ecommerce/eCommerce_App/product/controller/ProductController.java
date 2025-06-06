package com.ecommerce.eCommerce_App.product.controller;

import com.ecommerce.eCommerce_App.image.ImageResponse;
import com.ecommerce.eCommerce_App.product.model.dto.ProductRequest;
import com.ecommerce.eCommerce_App.product.model.dto.ProductResponse;
import com.ecommerce.eCommerce_App.product.model.entity.Product;
import com.ecommerce.eCommerce_App.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/category/{categoryId}")
    public ResponseEntity<ProductResponse> createProduct(
            @PathVariable Long categoryId,
            @RequestPart(name = "product_request") @Valid ProductRequest productRequest,
            @RequestPart(name = "image_files",required = true) List<MultipartFile> imageFiles
    ) {
        ProductResponse response = productService.toResponse(
                productService.add(categoryId, productRequest, imageFiles)
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long productId,
            @RequestPart(name = "product_request") @Valid ProductRequest productRequest,
            @RequestPart(name = "new_image_files",required = true) List<MultipartFile> newImageFiles
    ) {
        ProductResponse response = productService.toResponse(
                productService.update(productId, productRequest, newImageFiles)
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.delete(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long productId) {
        ProductResponse response = productService.toResponse(productService.getById(productId));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<Product> products = productService.getAll();
        List<ProductResponse> responses = products.stream()
                .map(productService::toResponse).toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getAllProductsByCategoryId(@PathVariable Long categoryId) {
        List<Product> products = productService.getAllByCategoryId(categoryId);
        List<ProductResponse> responses = products
                .stream()
                .map(productService::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{productId}/images")
    public ResponseEntity<List<ImageResponse>> getAllImagesByProductId(@PathVariable Long productId) {
        List<ImageResponse> responses = productService.getAllImagesByProductId(productId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{productId}/images/{imageId}/set-primary")
    public ResponseEntity<ImageResponse> setProductPrimaryImage(
            @PathVariable Long productId,
            @PathVariable Long imageId
    ) {
        ImageResponse response = productService.setProductPrimaryImage(imageId, productId);
        return ResponseEntity.ok(response);
    }
}
