package com.ecommerce.eCommerce_App.address.repository;

import com.ecommerce.eCommerce_App.address.model.entity.City;
import com.ecommerce.eCommerce_App.address.model.entity.Country;
import com.ecommerce.eCommerce_App.repository.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepo extends GenericRepository<City, Long> {
    @Override
    default Class<City> getEntityClass() {
        return City.class;
    }

    List<City> findAllByCountryId(Long countryId);
}
