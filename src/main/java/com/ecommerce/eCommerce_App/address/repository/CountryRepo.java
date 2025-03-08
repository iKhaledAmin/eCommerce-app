package com.ecommerce.eCommerce_App.address.repository;

import com.ecommerce.eCommerce_App.address.model.entity.Country;
import com.ecommerce.eCommerce_App.model.entity.Category;
import com.ecommerce.eCommerce_App.repository.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepo extends GenericRepository<Country, Long> {
    @Override
    default Class<Country> getEntityClass() {
        return Country.class;
    }
}
