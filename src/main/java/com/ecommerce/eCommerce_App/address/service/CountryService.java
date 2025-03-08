package com.ecommerce.eCommerce_App.address.service;

import com.ecommerce.eCommerce_App.address.model.dto.CityResponse;
import com.ecommerce.eCommerce_App.address.model.dto.CountryResponse;
import com.ecommerce.eCommerce_App.address.model.entity.City;
import com.ecommerce.eCommerce_App.address.model.entity.Country;

import java.util.List;

public interface CountryService {

    CountryResponse toResponse(Country country);
    CityResponse toResponse(City city);

    List<Country> getAllCountries();
    List<City> getAllCities();
    List<City> getCitiesByCountry(Long countryId);
}
