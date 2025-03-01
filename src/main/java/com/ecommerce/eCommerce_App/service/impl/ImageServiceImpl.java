package com.ecommerce.eCommerce_App.service.impl;


import com.ecommerce.eCommerce_App.model.entity.Image;
import com.ecommerce.eCommerce_App.model.enums.EntityType;
import com.ecommerce.eCommerce_App.model.mapper.ImageMapper;
import com.ecommerce.eCommerce_App.repository.ImageRepo;
import com.ecommerce.eCommerce_App.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepo imageRepo;
    private final ImageMapper imageMapper;

    //@Value("${upload.images.dir}") // Inject value from properties file
    private final String UPLOAD_IMAGE_DIR = "uploads/images/" ;

    public String toResponse(Image entity) {
        return imageMapper.toResponse(entity);
    }

    @Override
    public Image add(MultipartFile file, Long entityId, EntityType entityType) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be null or empty.");
        }

        // Generate unique filename and path
        String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_IMAGE_DIR, uniqueFileName);

        try {
            // Ensure directory exists
            Files.createDirectories(filePath.getParent());
            // Write file to disk
            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image file", e);
        }

        // Create and save Image entity
        Image image = new Image();
        image.setFileName(uniqueFileName);
        image.setFileType(file.getContentType());
        image.setStoragePath(filePath.toString());
        image.setEntityId(entityId);
        image.setEntityType(entityType);

        return imageRepo.save(image);
    }

    @Override
    public Image update(Long imageId, MultipartFile newFile) {
        if (newFile == null || newFile.isEmpty()) {
            throw new IllegalArgumentException("New image must not be null or empty.");
        }

        Image existingImage = getById(imageId);

        // Check if the new image is different (by filename)
        String existingFileName = existingImage.getFileName();
        String newFileName = newFile.getOriginalFilename();

        if (existingFileName != null && existingFileName.equals(newFileName)) {
            return existingImage; // No update needed, return existing image
        }

        // Proceed with replacing the image (file handling included)
        return replaceImage(existingImage, newFile);
    }

    private Image replaceImage(Image existingImage, MultipartFile newFile) {
        // Delete the old file
        Path oldFilePath = Paths.get(existingImage.getStoragePath());
        try {
            Files.deleteIfExists(oldFilePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete old image file", e);
        }

        // Generate new unique file name
        String uniqueFileName = UUID.randomUUID() + "_" + newFile.getOriginalFilename();
        Path newFilePath = Paths.get(UPLOAD_IMAGE_DIR, uniqueFileName);

        try {
            Files.createDirectories(newFilePath.getParent());
            Files.write(newFilePath, newFile.getBytes()); // Store new file content
        } catch (IOException e) {
            throw new RuntimeException("Failed to store new image file", e);
        }

        // Update existing image details
        existingImage.setFileName(uniqueFileName);
        existingImage.setFileType(newFile.getContentType());
        existingImage.setStoragePath(newFilePath.toString());

        return imageRepo.save(existingImage);
    }


    @Override
    public void deleteById(Long id) {
        Image image = getById(id);

        // Delete file from system
        try {
            Files.deleteIfExists(Paths.get(image.getStoragePath()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete image file", e);
        }

        imageRepo.delete(image);
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
    public Optional<Image> getOptionalByEntityId(Long entityId) {
        return imageRepo.findByEntityId(entityId);
    }

    @Override
    public Image getByEntityId(Long entityId) {
        return getOptionalByEntityId(entityId).orElseThrow(
                () -> new NoSuchElementException("Image not found!")
        );
    }
}
