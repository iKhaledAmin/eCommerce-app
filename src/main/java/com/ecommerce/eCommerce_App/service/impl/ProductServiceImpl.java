package com.ecommerce.eCommerce_App.service.impl;


import com.ecommerce.eCommerce_App.global.utils.NonNullBeanUtils;
import com.ecommerce.eCommerce_App.model.dto.ImageResponse;
import com.ecommerce.eCommerce_App.model.dto.ProductRequest;
import com.ecommerce.eCommerce_App.model.dto.ProductResponse;
import com.ecommerce.eCommerce_App.model.entity.Category;
import com.ecommerce.eCommerce_App.model.entity.Image;
import com.ecommerce.eCommerce_App.model.entity.Product;
import com.ecommerce.eCommerce_App.model.enums.EntityType;
import com.ecommerce.eCommerce_App.model.mapper.ProductMapper;
import com.ecommerce.eCommerce_App.repository.ProductRepo;
import com.ecommerce.eCommerce_App.service.CategoryService;
import com.ecommerce.eCommerce_App.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    private final ImageServiceImpl imageService;
    private final CategoryService categoryService;
    private final NonNullBeanUtils nonNullBeanUtils;
    private final EntityRetrievalServiceImpl entityRetrievalService;

    public Product toEntity(ProductRequest request) {
        return productMapper.toEntity(request);
    }
    public ProductResponse toResponse(Product entity) {
        ProductResponse response = productMapper.toResponse(entity);

        Optional<Image> image = imageService.getPrimaryImageByEntityIdAndEntityType(entity.getId(),EntityType.PRODUCT);
        if (image.isPresent()) {
            response.setImageUrl(imageService.toURL(image.get()));
        }

        return response;
    }

    private Product save(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Product add(Long categoryId,Product newProduct) {

        Category category = categoryService.getById(categoryId);
        categoryService.increaseProductsCountByOne(categoryId);
        newProduct.setCategory(category);
        category.getProducts().add(newProduct);

        return save(newProduct);
    }
    @Override
    public Product add(Long categoryId,Product newProduct,List<MultipartFile> imageFiles) {

        // save product
        Product servedProduct = add(categoryId,newProduct);

        // if no images return
        if (imageFiles == null || imageFiles.isEmpty()) return servedProduct;
        // save images
        imageFiles.forEach(imageFile ->
                imageService.add(imageFile,servedProduct.getId(), EntityType.PRODUCT)
        );

        return servedProduct;
    }
    @Override
    @Transactional
    public Product add(Long categoryId,ProductRequest productRequest,List<MultipartFile> imageFiles) {
        Product newProduct = toEntity(productRequest);
        return add(categoryId,newProduct,imageFiles);
    }

    @Override
    public Product update(Long productId, Product newProduct) {
        Product existingProduct = getById(productId);

        // Copy properties from newProduct to existingProduct, excluding the "id", "category","images", and "inventoryQuantity" properties
        nonNullBeanUtils.copyProperties(newProduct, existingProduct, "id", "category", "images","inventoryQuantity");

        return save(existingProduct);

    }

    @Override
    public Product update(Long productId, Product newProduct,List<MultipartFile> newImageFiles) {
        Product updatedProduct = update(productId,newProduct);
        imageService.update(updatedProduct.getId(),EntityType.PRODUCT,newImageFiles);
        return updatedProduct;
    }

    @Override
    @Transactional
    public Product update(Long productId, ProductRequest productRequest,List<MultipartFile> imageFiles) {
        Product newProduct = toEntity(productRequest);
        return update(productId,newProduct,imageFiles);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Product product = getById(id);

        // decrease products count
        Category category = product.getCategory();
        categoryService.decreaseProductsCountByOne(category.getId());

        // delete images
        List<Image> images = imageService.getAllByEntityIdAndEntityType(id,EntityType.PRODUCT);
        if (images != null && !images.isEmpty()) {
            imageService.deleteAllByEntityIdAndEntityType(id,EntityType.PRODUCT);
        }

        productRepo.deleteById(id);
    }

    @Override
    public Optional<Product> getOptionalById(Long id) {
        return entityRetrievalService.getOptionalById(Product.class,id);
    }

    @Override
    public Product getById(Long id) {
        return entityRetrievalService.getById(Product.class,id);
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

    @Override
    public List<Product> getAllByCategoryId(Long categoryId) {
        categoryService.getById(categoryId);
        return productRepo.findAllByCategory_Id(categoryId);
    }

    @Override
    public List<ImageResponse> getAllImagesByProductId(Long productId) {
        getById(productId);
        List<Image> images = imageService.getAllByEntityIdAndEntityType(productId,EntityType.PRODUCT);
        return images.stream()
                .map(image -> imageService.toResponse(image))
                .collect(Collectors.toList());
    }

    @Override
    public ImageResponse setProductPrimaryImage(Long imageId,Long productId) {
        getById(productId);
        imageService.getById(imageId);
        Image defaultImage = imageService.setPrimaryImage(
                imageId
                ,productId
                ,EntityType.PRODUCT
        );
        return imageService.toResponse(defaultImage);

    }


}
