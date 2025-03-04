package com.ecommerce.eCommerce_App.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntityRetrievalService {
    <T, ID> Optional<T> getOptionalById(Class<T> entityClass, ID id);

    <T, ID> T getById(Class<T> entityClass, ID id);
}
