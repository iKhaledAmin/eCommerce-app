package com.ecommerce.eCommerce_App.repository;

import com.ecommerce.eCommerce_App.model.entity.Image;
import com.ecommerce.eCommerce_App.model.enums.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepo extends JpaRepository<Image,Long> {
    Optional<Image> findByEntityIdAndEntityType(Long entityId, EntityType entityType);

    List<Image> findAllByEntityIdAndEntityType(Long entityId, EntityType entityType);

    boolean existsByImageNameAndEntityIdAndEntityType(String imageName, Long entityId, EntityType entityType);


    Optional<Image> findByEntityIdAndEntityTypeAndIsPrimary(Long entityId, EntityType entityType, boolean isPrimary);
}
