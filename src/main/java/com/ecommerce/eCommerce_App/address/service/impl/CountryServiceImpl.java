package com.ecommerce.eCommerce_App.address.service.impl;

import com.ecommerce.eCommerce_App.address.model.dto.CityResponse;
import com.ecommerce.eCommerce_App.address.model.dto.CountryResponse;
import com.ecommerce.eCommerce_App.address.model.entity.City;
import com.ecommerce.eCommerce_App.address.model.entity.Country;
import com.ecommerce.eCommerce_App.address.model.mapper.CityMapper;
import com.ecommerce.eCommerce_App.address.model.mapper.CountryMapper;
import com.ecommerce.eCommerce_App.address.repository.CityRepo;
import com.ecommerce.eCommerce_App.address.repository.CountryRepo;
import com.ecommerce.eCommerce_App.address.service.CountryService;
import com.ecommerce.eCommerce_App.service.EntityRetrievalService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepo countryRepo;
    private final CityRepo cityRepo;
    private final CountryMapper countryMapper;
    private final CityMapper cityMapper;
    private final EntityRetrievalService entityRetrievalService;


    public CountryResponse toResponse(Country country) {
        return countryMapper.toResponse(country);
    }

    public CityResponse toResponse(City city) {
        return cityMapper.toResponse(city);
    }

    public List<Country> getAllCountries() {
        return countryRepo.findAll();
    }

    @Override
    public List<City> getAllCities() {
        return cityRepo.findAll();
    }

    public List<City> getCitiesByCountry(Long countryId) {
        entityRetrievalService.getById(Country.class,countryId);
        return cityRepo.findAllByCountryId(countryId);
    }
}
