package com.ecommerce.eCommerce_App.repository;

import com.ecommerce.eCommerce_App.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepo extends JpaRepository<Image,Long> {
    Optional<Image> findByEntityId(Long entityId);
}
