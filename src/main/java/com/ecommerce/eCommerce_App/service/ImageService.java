package com.ecommerce.eCommerce_App.service;

import com.ecommerce.eCommerce_App.model.entity.Image;
import com.ecommerce.eCommerce_App.model.dto.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ImageService {
    ImageResponse toResponse(Image entity);

    Image convertToImage(MultipartFile file);
    Image add(Image newImage, Object entity);
    Image update(Long imageId, Image newImage);
    void delete(Long id);
    Optional<Image> getOptionalById(Long id);
    Image getById(Long id);
}
