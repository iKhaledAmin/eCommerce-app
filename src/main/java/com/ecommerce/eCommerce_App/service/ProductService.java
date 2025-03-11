package com.ecommerce.eCommerce_App.service;

import com.ecommerce.eCommerce_App.image.ImageResponse;
import com.ecommerce.eCommerce_App.model.dto.ProductRequest;
import com.ecommerce.eCommerce_App.model.dto.ProductResponse;
import com.ecommerce.eCommerce_App.model.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product toEntity(ProductRequest request);
    ProductResponse toResponse(Product entity);

    Product add(Long categoryId,Product newProduct);
    Product add(Long categoryId,Product newProduct,List<MultipartFile> images);
    Product add(Long categoryId,ProductRequest productRequest,List<MultipartFile> imageFiles);


    Product update(Long productId,Product newProduct);
    Product update(Long productId, Product newProduct,List<MultipartFile> newImageFiles);
    Product update(Long productId, ProductRequest productRequest,List<MultipartFile> newImageFiles);



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

    List<Product> getAllByCategoryId(Long categoryId);
    List<ImageResponse> getAllImagesByProductId(Long productId);
    ImageResponse setProductPrimaryImage(Long imageId,Long productId);
}
