package com.ecommerce.eCommerce_App.global.repository;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GenericRepositoryFactory {

    private final Repositories repositories;

    @SuppressWarnings("unchecked")
    public <T, ID> JpaRepository<T, ID> getRepository(Class<T> entityClass) {
        return (JpaRepository<T, ID>) repositories.getRepositoryFor(entityClass)
                .orElseThrow(() -> new IllegalStateException("No repository found for entity: " + entityClass.getSimpleName()));
    }
}