package com.ecommerce.eCommerce_App.product.service;

import com.ecommerce.eCommerce_App.image.ImageResponse;
import com.ecommerce.eCommerce_App.product.model.dto.ProductRequest;
import com.ecommerce.eCommerce_App.product.model.dto.ProductResponse;
import com.ecommerce.eCommerce_App.product.model.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product toEntity(ProductRequest request);
    ProductResponse toResponse(Product entity);

    Product add(ProductRequest productRequest,List<MultipartFile> imageFiles);

    Product update(Long productId, ProductRequest productRequest,List<MultipartFile> newImageFiles);


    Optional<Product> getOptionalById(Long id);
    Product getById(Long id);
    Product getByIdWithLock(Long id);
    List<Product> getAll();
    List<Product> getAllByName(String productName);
    List<Product> getByCategory(String categoryName);
    List<Product> getBybrand(String brand);
    List<Product> getBybrandAndName(String brand,String productName);
    List<Product> getByCategoryAndBrand(String categoryName,String brand);
    Long countByBrandAndName(String brand,String productName);

    List<Product> getAllByCategoryId(Long categoryId);
    List<ImageResponse> getAllImagesByProductId(Long productId);
    ImageResponse setProductPrimaryImage(Long imageId,Long productId);

    Product pickProductFromInventory(Long productId, Long quantity);
    Product restockProductInInventory(Long productId, Long quantity);
}
