package com.ecommerce.eCommerce_App.repository;

import com.ecommerce.eCommerce_App.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepo extends JpaRepository<Image,Long> {
}
