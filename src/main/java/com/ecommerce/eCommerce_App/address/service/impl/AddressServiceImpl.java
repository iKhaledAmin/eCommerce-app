package com.ecommerce.eCommerce_App.address.service.impl;

import com.ecommerce.eCommerce_App.address.model.dto.AddressRequest;
import com.ecommerce.eCommerce_App.address.model.dto.AddressResponse;
import com.ecommerce.eCommerce_App.address.model.entity.Address;
import com.ecommerce.eCommerce_App.address.model.entity.City;
import com.ecommerce.eCommerce_App.address.model.entity.Country;
import com.ecommerce.eCommerce_App.address.model.mapper.AddressMapper;
import com.ecommerce.eCommerce_App.address.repository.AddressRepo;
import com.ecommerce.eCommerce_App.address.service.AddressService;
import com.ecommerce.eCommerce_App.global.service.EntityRetrievalService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepo addressRepo;
    private final AddressMapper addressMapper;
    private final EntityRetrievalService entityRetrievalService;

    @Override
    public AddressResponse toResponse(Address address) {
        return addressMapper.toResponse(address);
    }


    @Override
    public Address toEntity(AddressRequest request) {
        return addressMapper.toEntity(request);
    }

    private Address save(Address address) {
        return addressRepo.save(address);
    }

    @Override
    public Address add(Address newAddress) {
        return getOptionalByCountryIdAndCityIdAndStreetAndStateAndZipCode(
                newAddress.getCountry().getId(),
                newAddress.getCity().getId(),
                newAddress.getStreet(),
                newAddress.getState(), newAddress.getZipCode()
        ).orElseGet(() -> {
            return save(newAddress);
        });
    }
    @Transactional
    @Override
    public Address add(AddressRequest addressRequest) {
        Country country = entityRetrievalService.getById(Country.class, addressRequest.getCountryId());
        City city = entityRetrievalService.getById(City.class, addressRequest.getCityId());

        Address newAddress = toEntity(addressRequest);
        newAddress.setCountry(country);
        newAddress.setCity(city);

        return add(newAddress);
    }

    @Override
    public Address update(Long addressId, Address newAddress) {
        return add(newAddress);
    }
    @Transactional
    @Override
    public Address update(Long addressId, AddressRequest addressRequest) {
        Country country = entityRetrievalService.getById(Country.class, addressRequest.getCountryId());
        City city = entityRetrievalService.getById(City.class, addressRequest.getCityId());

        Address newAddress = toEntity(addressRequest);
        newAddress.setCountry(country);
        newAddress.setCity(city);

        return update(addressId, newAddress);
    }


    @Transactional
    @Override
    public void delete(Long addressId) {
        getById(addressId);
        addressRepo.deleteById(addressId);
    }

    @Override
    public Optional<Address> getOptionalById(Long addressId) {
        return entityRetrievalService.getOptionalById(Address.class, addressId);
    }
    @Override
    public Address getById(Long addressId) {
        return entityRetrievalService.getById(Address.class, addressId);
    }
    @Override
    public List<Address> getAll() {
        return addressRepo.findAll();
    }
    @Override
    public Optional<Address> getOptionalByCountryIdAndCityIdAndStreetAndStateAndZipCode(Long countryId, Long cityId, String street, String state, String zipCode) {
        return addressRepo.findByCountryIdAndCityIdAndStreetAndStateAndZipCode(countryId, cityId, street, state, zipCode);
    }

}
