package com.ecommerce.eCommerce_App.service.impl;


import com.ecommerce.eCommerce_App.model.dto.ImageResponse;
import com.ecommerce.eCommerce_App.model.entity.Image;
import com.ecommerce.eCommerce_App.model.mapper.ImageMapper;
import com.ecommerce.eCommerce_App.repository.ImageRepo;
import com.ecommerce.eCommerce_App.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
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

    private static final String UPLOAD_DIR = "uploads/images/";

    public ImageResponse toResponse(Image entity) {
        return imageMapper.toResponse(entity);
    }

    public Image convertToImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be null or empty.");
        }

        // Generate a unique file name
        String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR, uniqueFileName);

        // Create and populate the Image entity (without writing the file)
        Image image = new Image();
        image.setFileName(uniqueFileName);
        image.setFileType(file.getContentType());
        image.setFilePath(filePath.toString());

        return image;
    }

    @Override
    public Image add(Image newImage, Object entity) {
        if (newImage == null) {
            throw new IllegalArgumentException("New image must not be null.");
        }

        // Write image file to the file system
        Path filePath = Paths.get(newImage.getFilePath());
        try {
            Files.createDirectories(filePath.getParent());
            Files.write( // Save file content
                    filePath,
                    Files.readAllBytes(Paths.get(newImage.getFilePath()))
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image file", e);
        }

        // Set image field dynamically on the entity
        try {
            Field imageField = entity.getClass().getDeclaredField("image");
            imageField.setAccessible(true);
            imageField.set(entity, newImage);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null; // Skip saving if the entity does not have an image field
        }

        // Save the new image to the database
        return imageRepo.save(newImage);
    }

    @Override
    public Image update(Long imageId, Image newImage) {
        if (newImage == null) {
            throw new IllegalArgumentException("New image must not be null.");
        }

        Image existingImage = getById(imageId);

        // Check if the new image is different (by filename)
        String existingFileName = existingImage.getFileName();
        String newFileName = newImage.getFileName();

        if (existingFileName != null && existingFileName.equals(newFileName)) {
            return existingImage; // No update needed, return existing image
        }

        // Proceed with replacing the image (file handling included)
        return replaceImage(existingImage, newImage);
    }

    private Image replaceImage(Image existingImage, Image newImage) {
        // Delete the old file
        Path oldFilePath = Paths.get(existingImage.getFilePath());
        try {
            Files.deleteIfExists(oldFilePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete old image file", e);
        }

        // Generate new unique file name
        String uniqueFileName = UUID.randomUUID() + "_" + newImage.getFileName();
        Path newFilePath = Paths.get(UPLOAD_DIR, uniqueFileName);

        try {
            Files.createDirectories(newFilePath.getParent());
            Files.write(newFilePath, Files.readAllBytes(Paths.get(newImage.getFilePath()))); // Copy content from provided image path
        } catch (IOException e) {
            throw new RuntimeException("Failed to store new image file", e);
        }

        // Update existing image details
        existingImage.setFileName(uniqueFileName);
        existingImage.setFileType(newImage.getFileType());
        existingImage.setFilePath(newFilePath.toString());

        return imageRepo.save(existingImage);
    }


    @Override
    public void delete(Long id) {
        Image image = getById(id);

        // Delete the file from the system
        Path filePath = Paths.get(image.getFilePath());
        try {
            Files.deleteIfExists(filePath);
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
}
