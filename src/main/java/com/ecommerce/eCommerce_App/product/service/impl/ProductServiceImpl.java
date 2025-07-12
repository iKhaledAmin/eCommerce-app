package com.ecommerce.eCommerce_App.product.service.impl;


import com.ecommerce.eCommerce_App.global.exception.BadRequestException;
import com.ecommerce.eCommerce_App.global.utils.NonNullBeanUtils;
import com.ecommerce.eCommerce_App.image.ImageResponse;
import com.ecommerce.eCommerce_App.image.ImageServiceImpl;
import com.ecommerce.eCommerce_App.product.model.dto.ProductRequest;
import com.ecommerce.eCommerce_App.product.model.dto.ProductResponse;
import com.ecommerce.eCommerce_App.category.model.entity.Category;
import com.ecommerce.eCommerce_App.image.Image;
import com.ecommerce.eCommerce_App.product.model.entity.Product;
import com.ecommerce.eCommerce_App.image.EntityType;
import com.ecommerce.eCommerce_App.product.model.enums.Status;
import com.ecommerce.eCommerce_App.product.model.mapper.ProductMapper;
import com.ecommerce.eCommerce_App.product.repository.ProductRepo;
import com.ecommerce.eCommerce_App.category.service.CategoryService;
import com.ecommerce.eCommerce_App.product.service.ProductService;
import com.ecommerce.eCommerce_App.global.service.impl.EntityRetrievalServiceImpl;
import com.ecommerce.eCommerce_App.stock.model.enity.StockItem;
import com.ecommerce.eCommerce_App.stock.service.StockService;
import jakarta.persistence.EntityNotFoundException;
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
    private final StockService stockService;
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
    @Transactional
    public Product add(ProductRequest productRequest, List<MultipartFile> imageFiles) {
        // Convert the request to an entity
        Product newProduct = toEntity(productRequest);

        if (productRequest.getStatus() == Status.ACTIVE && productRequest.getSellingPrice() == null) {
            throw new BadRequestException("Selling price must be provided when status is ACTIVE");
        }

        // handle category and link it to the product
        Category category = categoryService.getById(productRequest.getCategoryId());
        newProduct.setCategory(category);
        categoryService.increaseProductsCountByOne(category.getId());


        // Save product to generate ID
        newProduct = save(newProduct);

        // Extract quantity
        long quantity = Optional.ofNullable(productRequest.getStockDetails())
                .map(stockDetails -> stockDetails.getItemQuantity())
                .orElse(0L);

        // Add stock item
        StockItem stockItem = stockService.addStockItemForProduct(newProduct, quantity);
        newProduct.setStockItem(stockItem);


        // Add batch if needed
        if (productRequest.getStockDetails() != null) {
            stockService.addStockBatchForProduct(
                    newProduct.getId(),
                    productRequest.getStockDetails().getItemQuantity(),
                    productRequest.getStockDetails().getItemBuyingPrice(),
                    productRequest.getStockDetails().getItemExpirationDate()
            );
        }

        // Handle images
        if (imageFiles != null && !imageFiles.isEmpty()) {
            for (MultipartFile image : imageFiles) {
                imageService.add(image, newProduct.getId(), EntityType.PRODUCT);
            }
        }

        return newProduct;
    }

    @Override
    @Transactional
    public Product update(Long productId, ProductRequest productRequest, List<MultipartFile> imageFiles) {
        // 1. Fetch the existing product
        Product existingProduct = getById(productId);

        // 2. Map updated values from DTO
        Product updatedProduct = toEntity(productRequest);

        // 3. Load the new category and associate it
        Category newCategory = categoryService.getById(productRequest.getCategoryId());
        updatedProduct.setCategory(newCategory);
        if (!newCategory.getProducts().contains(existingProduct)) {
            newCategory.getProducts().add(existingProduct);
        }

        // 4. Apply business rule: status = ACTIVE requires selling price
        if (productRequest.getStatus() == Status.ACTIVE && productRequest.getSellingPrice() == null) {
            throw new BadRequestException("Selling price must be provided when status is ACTIVE");
        }

        // 5. Copy properties from updatedProduct to existingProduct (excluding "id", "stockItem", and "category")
        nonNullBeanUtils.copyProperties(updatedProduct, existingProduct, "id", "stockItem", "category");

        // 6. Handle image update
        imageService.update(existingProduct.getId(), EntityType.PRODUCT, imageFiles);

        // 7. Save and return
        return save(existingProduct);
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
    public Product getByIdWithLock(Long productId) {
        return productRepo.findByIdForCriticalUpdate(productId).orElseThrow(
                () -> new EntityNotFoundException("Product not found!")
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

    @Override
    @Transactional
    public Product pickProductFromInventory(Long productId, Long quantity) {

        // Lock and load the product for update the product inventory quantity
        Product product = getByIdWithLock(productId);

        // Check inventory
//        long currentQuantity = product.getInventoryQuantity();
//        if (currentQuantity == 0)
//            throw new ZeroInventoryException(product.getName());
//        if (currentQuantity < quantity)
//            throw new InsufficientInventoryException(currentQuantity, quantity);
//
//        // Update inventory (protected by lock)
//        product.setInventoryQuantity(currentQuantity - quantity);

        return save(product);
    }

    @Transactional
    @Override
    public Product restockProductInInventory(Long productId, Long quantity) {
        // Lock and load the product for update the product inventory quantity.
        Product product = getByIdWithLock(productId);
//        try {
//            // Safe addition ( that prevents overflow )
//            long newQuantity = Math.addExact(product.getInventoryQuantity(), quantity);
//            product.setInventoryQuantity(newQuantity);
//        } catch (ArithmeticException e) {
//            throw new ArithmeticException("Product inventory quantity overflow");
//        }

        return save(product);
    }


}
