package com.ecommerce.eCommerce_App.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.support.Repositories;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class RepositoryConfig {

    @Bean
    public Repositories repositories(WebApplicationContext webApplicationContext) {
        return new Repositories(webApplicationContext);
    }
}