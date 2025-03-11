package com.ecommerce.eCommerce_App.image;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    String toURL(Image entity);
    ImageResponse toResponse(Image entity);

    Image add(MultipartFile file, Long entityId, EntityType entityType);
    Image update(Long entityId,EntityType entityType, MultipartFile newImageFile);
    List<Image> update(Long entityId, EntityType entityType,List<MultipartFile> newImageFiles);
    void deleteById(Long id);
    void deleteAllByEntityIdAndEntityType(Long entityId, EntityType entityType);


    Optional<Image> getOptionalById(Long id);
    Image getById(Long id);

    Optional<Image> getOptionalByEntityIdAndEntityType(Long entityId, EntityType entityType);
    Optional<Image> getOptionalPrimaryImageByEntityIdAndEntityType(Long entityId, EntityType entityType);
    Image getByEntityIdAndEntityType(Long entityId, EntityType entityType);

    List<Image> getAllByEntityIdAndEntityType(Long entityId, EntityType entityType);



    Image setPrimaryImage(Long imageId, Long entityId, EntityType entityType);
    Optional<Image> getPrimaryImageByEntityIdAndEntityType(Long entityId, EntityType entityType);
}
