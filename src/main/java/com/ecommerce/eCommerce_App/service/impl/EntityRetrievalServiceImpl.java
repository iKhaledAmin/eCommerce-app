package com.ecommerce.eCommerce_App.service.impl;

import com.ecommerce.eCommerce_App.repository.GenericRepositoryFactory;
import com.ecommerce.eCommerce_App.service.EntityRetrievalService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EntityRetrievalServiceImpl implements EntityRetrievalService {

    private final GenericRepositoryFactory genericRepositoryFactory;

    public <T, ID> Optional<T> getOptionalById(Class<T> entityClass, ID id) {
        JpaRepository<T, ID> repository = genericRepositoryFactory.getRepository(entityClass);
        if (repository == null) {
            throw new IllegalStateException("No repository found for entity: " + entityClass.getSimpleName());
        }
        return repository.findById(id);
    }

    public <T, ID> T getById(Class<T> entityClass, ID id) {
        return getOptionalById(entityClass, id)
                .orElseThrow(() -> new EntityNotFoundException(entityClass.getSimpleName() + " not found!"));
    }
}
