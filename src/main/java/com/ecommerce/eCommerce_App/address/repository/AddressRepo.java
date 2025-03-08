package com.ecommerce.eCommerce_App.address.repository;

import com.ecommerce.eCommerce_App.address.model.entity.Address;
import com.ecommerce.eCommerce_App.repository.GenericRepository;

import java.util.Optional;

public interface AddressRepo extends GenericRepository<Address, Long> {
    @Override
    default Class<Address> getEntityClass() {
        return Address.class;
    }

    Optional<Address> findByCountryIdAndCityIdAndStreetAndStateAndZipCode(Long countryId, Long cityId, String street, String state, String zipCode);
}
