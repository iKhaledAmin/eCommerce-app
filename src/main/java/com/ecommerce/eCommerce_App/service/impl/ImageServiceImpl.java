package com.ecommerce.eCommerce_App.service.impl;

import com.ecommerce.eCommerce_App.model.dto.ImageResponse;
import com.ecommerce.eCommerce_App.model.entity.Image;
import com.ecommerce.eCommerce_App.model.enums.EntityType;
import com.ecommerce.eCommerce_App.model.mapper.ImageMapper;
import com.ecommerce.eCommerce_App.repository.ImageRepo;
import com.ecommerce.eCommerce_App.service.ImageService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepo imageRepo;
    private final ImageMapper imageMapper;

    private final String UPLOAD_IMAGE_DIR = "uploads/images/" ;

    private static final List<String> ALLOWED_IMAGE_TYPES = List.of("image/png", "image/jpeg", "image/jpg" ,"image/gif");

    @Override
    public String toURL(Image entity) {
        return imageMapper.toURL(entity);
    }
    @Override
    public ImageResponse toResponse(Image entity) {
        return imageMapper.toResponse(entity);
    }

    @Override
    public Image add(MultipartFile imageFile, Long entityId, EntityType entityType) {
        if (imageFile == null || imageFile.isEmpty()) {
            throw new IllegalArgumentException("Image file must not be empty.");
        }
        validateImageType(imageFile);

        String uniqueFileName = generateUniqueFileName(imageFile.getOriginalFilename(), entityType);
        Path filePath = Paths.get(UPLOAD_IMAGE_DIR, uniqueFileName);

        saveFileToDisk(imageFile, filePath);

        Image image = new Image();
        image.setImageName(imageFile.getOriginalFilename());
        image.setImageType(imageFile.getContentType());
        image.setStoragePath(filePath.toString());
        image.setEntityId(entityId);
        image.setEntityType(entityType);
        image.setUniqueFileName(uniqueFileName);
        image = save(image);

        setPrimaryImage(image.getId(), entityId, entityType);

        return image;
    }

    private Image updateImage(Long imageId, MultipartFile newImageFile) {
        validateImageType(newImageFile);

        Image existingImage = getById(imageId);
        String uniqueFileName = generateUniqueFileName(newImageFile.getOriginalFilename(),existingImage.getEntityType());
        Path newFilePath = Paths.get(UPLOAD_IMAGE_DIR, uniqueFileName);

        saveFileToDisk(newImageFile, newFilePath);

        // Delete the old file only after the new file is successfully saved
        deleteFileFromDisk(existingImage.getStoragePath());

        // Update the existing image details
        existingImage.setImageName(newImageFile.getOriginalFilename());
        existingImage.setImageType(newImageFile.getContentType());
        existingImage.setStoragePath(newFilePath.toString());
        existingImage.setUniqueFileName(uniqueFileName);

        return imageRepo.save(existingImage);
    }
    @Override
    @Transactional
    public Image update(Long entityId, EntityType entityType, MultipartFile newImageFile) {
        Optional<Image> existingImage = getOptionalPrimaryImageByEntityIdAndEntityType(entityId, entityType);

        if (newImageFile != null && !newImageFile.isEmpty()) {
            // If newImageFile is not empty and not null
            return existingImage.map(image -> updateImage(image.getId(), newImageFile))
                    .orElseGet(() -> add(newImageFile, entityId, entityType));
        } else {
            // If newImageFile is null or empty
            existingImage.ifPresent(image -> deleteById(image.getId()));
            return null; // No image to return
        }
    }

    @Override
    @Transactional
    public List<Image> update(Long entityId, EntityType entityType, List<MultipartFile> newImageFiles) {
        // Fetch existing images for the entity before adding new ones
        List<Image> existingImages = getAllByEntityIdAndEntityType(entityId, entityType);
        List<Image> updatedImages = new ArrayList<>();

        // Add new images
        if (newImageFiles != null && !newImageFiles.isEmpty()) {
            updatedImages = newImageFiles.stream()
                    .map(imageFile -> add(imageFile, entityId, entityType))
                    .collect(Collectors.toList());
        }

        // Delete old images after new images are successfully added
        if (existingImages.isEmpty()) {
            existingImages.forEach(this::deleteImage);
        }


        return updatedImages;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Image image = getById(id);
        deleteImage(image);
    }

    @Override
    @Transactional
    public void deleteAllByEntityIdAndEntityType(Long entityId, EntityType entityType) {
        List<Image> images = getAllByEntityIdAndEntityType(entityId, entityType);
        images.forEach(this::deleteImage);
    }

    @Override
    public Optional<Image> getOptionalById(Long id) {
        return imageRepo.findById(id);
    }

    @Override
    public Image getById(Long id) {
        return getOptionalById(id).orElseThrow(
                () -> new NoSuchElementException("Image not found!")
        );
    }

    @Override
    public Optional<Image> getOptionalByEntityIdAndEntityType(Long entityId, EntityType entityType) {
        return imageRepo.findByEntityIdAndEntityType(entityId, entityType);
    }

    public Optional<Image> getOptionalPrimaryImageByEntityIdAndEntityType(Long entityId, EntityType entityType){
        return imageRepo.findByEntityIdAndEntityTypeAndIsPrimary(entityId, entityType, true);
    }

    @Override
    public Image getByEntityIdAndEntityType(Long entityId, EntityType entityType) {
        return getOptionalByEntityIdAndEntityType(entityId, entityType).orElseThrow(
                () -> new NoSuchElementException("Image not found!")
        );
    }

    @Override
    public List<Image> getAllByEntityIdAndEntityType(Long entityId, EntityType entityType) {
        return imageRepo.findAllByEntityIdAndEntityType(entityId, entityType);
    }

    @Override
    @Transactional
    public Image setPrimaryImage(Long imageId, Long entityId, EntityType entityType) {
        Image image = getById(imageId);

        // Unset any existing primary image for the same entity
        Optional<Image> existingPrimaryImage = getPrimaryImageByEntityIdAndEntityType(entityId, entityType);
        if (existingPrimaryImage.isPresent()) {
            existingPrimaryImage.get().setPrimary(false);
            imageRepo.save(existingPrimaryImage.get());
        }

        // Set the new primary image
        image.setPrimary(true);
        return imageRepo.save(image);
    }

    @Override
    public Optional<Image> getPrimaryImageByEntityIdAndEntityType(Long entityId, EntityType entityType) {
        return imageRepo.findByEntityIdAndEntityTypeAndIsPrimary(entityId, entityType, true);
    }




    // Helper Methods

    private Image save(Image image) {
        return imageRepo.save(image);
    }

    private void validateImageType(MultipartFile imageFile) {
        if (!ALLOWED_IMAGE_TYPES.contains(imageFile.getContentType())) {
            throw new IllegalArgumentException("Invalid image type. Allowed types are: " + ALLOWED_IMAGE_TYPES);
        }
    }

    private String generateUniqueFileName(String originalFileName,EntityType entityType) {
        int lastDotIndex = originalFileName.lastIndexOf('.');
        String fileNameWithoutExtension;
        String fileExtension;

        // extract the file name without extension and the file extension
        if (lastDotIndex != -1) {
            fileNameWithoutExtension = originalFileName.substring(0, lastDotIndex);
            fileExtension = originalFileName.substring(lastDotIndex);
        } else {
            fileNameWithoutExtension = originalFileName;
            fileExtension = "";
        }

        return fileNameWithoutExtension + "_" + entityType.toString() + "_" + UUID.randomUUID() + fileExtension;
    }

    private void saveFileToDisk(MultipartFile file, Path filePath) {
        try {
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image file", e);
        }
    }

    private void deleteFileFromDisk(String filePath) {
        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete image file", e);
        }
    }

    private void deleteImage(Image image) {
        deleteFileFromDisk(image.getStoragePath());
        imageRepo.delete(image);
    }








}
