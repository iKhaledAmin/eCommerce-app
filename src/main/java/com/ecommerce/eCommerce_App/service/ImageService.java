package com.ecommerce.eCommerce_App.service;

import com.ecommerce.eCommerce_App.model.entity.Image;
import com.ecommerce.eCommerce_App.model.enums.EntityType;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ImageService {
    String toResponse(Image entity);

    Image add(MultipartFile file, Long entityId, EntityType entityType);
    Image update(Long imageId, MultipartFile newFile);
    void deleteById(Long id);


    Optional<Image> getOptionalById(Long id);
    Image getById(Long id);

    Optional<Image> getOptionalByEntityId(Long entityId);
    Image getByEntityId(Long entityId);
}
