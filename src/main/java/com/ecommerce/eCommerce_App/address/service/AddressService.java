package com.ecommerce.eCommerce_App.address.service;

import com.ecommerce.eCommerce_App.address.model.dto.AddressRequest;
import com.ecommerce.eCommerce_App.address.model.dto.AddressResponse;

import com.ecommerce.eCommerce_App.address.model.entity.Address;
import com.ecommerce.eCommerce_App.address.model.entity.AddressSnapshot;
import com.ecommerce.eCommerce_App.address.model.enums.AddressType;


import java.util.List;
import java.util.Optional;

public interface AddressService {
    AddressResponse toResponse(Address address);
    Address toEntity(AddressRequest request);


    Address add(Address newAddress);
    Address add(AddressRequest addressRequest);

    Address update(Long addressId, Address newAddress);
    Address update(Long addressId, AddressRequest addressRequest);

    void delete(Long addressId);


    Optional<Address> getOptionalById(Long addressId);
    Address getById(Long addressId);
    List<Address> getAll();
    Optional<Address> getOptionalByCountryIdAndCityIdAndStreetAndStateAndZipCode(Long countryId, Long cityId, String street, String state, String zipCode);

    AddressSnapshot createAddressSnapshot(Address address);

}
