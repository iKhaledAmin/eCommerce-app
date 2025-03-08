package com.ecommerce.eCommerce_App.address.service.impl;

import com.ecommerce.eCommerce_App.address.model.dto.AddressRequest;
import com.ecommerce.eCommerce_App.address.model.dto.AddressResponse;
import com.ecommerce.eCommerce_App.address.model.dto.CustomerAddressResponse;
import com.ecommerce.eCommerce_App.address.model.entity.Address;
import com.ecommerce.eCommerce_App.address.model.entity.City;
import com.ecommerce.eCommerce_App.address.model.entity.Country;
import com.ecommerce.eCommerce_App.address.model.entity.CustomerAddress;
import com.ecommerce.eCommerce_App.address.model.enums.AddressType;
import com.ecommerce.eCommerce_App.address.model.mapper.AddressMapper;
import com.ecommerce.eCommerce_App.address.repository.AddressRepo;
import com.ecommerce.eCommerce_App.address.service.AddressService;
import com.ecommerce.eCommerce_App.address.service.CustomerAddressService;
import com.ecommerce.eCommerce_App.model.entity.Customer;
import com.ecommerce.eCommerce_App.service.EntityRetrievalService;
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
    private final CustomerAddressService customerAddressService;

    @Override
    public AddressResponse toResponse(Address address) {
        return addressMapper.toResponse(address);
    }

    @Override
    public CustomerAddressResponse toResponse(CustomerAddress address) {
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
        Country country = entityRetrievalService.getById(Country.class, newAddress.getCountry().getId());
        City city = entityRetrievalService.getById(City.class, newAddress.getCity().getId());

        return getOptionalByCountryIdAndCityIdAndStreetAndStateAndZipCode(
                country.getId(), city.getId(), newAddress.getStreet(),
                newAddress.getState(), newAddress.getZipCode()
        ).orElseGet(() -> {
            newAddress.setCountry(country);
            newAddress.setCity(city);
            return save(newAddress);
        });
    }
    @Transactional
    @Override
    public Address add(AddressRequest addressRequest) {
        Address newAddress = toEntity(addressRequest);
        return add(newAddress);
    }


    @Override
    public Address update(Long addressId, Address newAddress) {
        getById(addressId);
        return add(newAddress);
    }
    @Transactional
    @Override
    public Address update(Long addressId, AddressRequest addressRequest) {
        Address newAddress = toEntity(addressRequest);
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


    @Transactional
    @Override
    public CustomerAddress addCustomerAddress(AddressRequest addressRequest, Long customerId, AddressType addressType, boolean isDefault) {
        // Fetch the Customer entity
        Customer customer = entityRetrievalService.getById(Customer.class, customerId);

        // Add the new Address
        Address newAddress = add(addressRequest);

        // Link the Address to the Customer
        return customerAddressService.add(newAddress, customer, addressType, isDefault);
    }

    @Transactional
    @Override
    public CustomerAddress updateCustomerAddress(Long addressId, AddressRequest addressRequest, Long customerId, AddressType addressType, boolean isDefault) {
        // Fetch the Customer entity
        Customer customer = entityRetrievalService.getById(Customer.class, customerId);

        // Update the Address
        Address newAddress = update(addressId, addressRequest);

        // Update the CustomerAddress entity
        return customerAddressService.update(newAddress, customer, addressType, isDefault);
    }

    @Transactional
    @Override
    public void deleteCustomerAddress(Long addressId, Long customerId) {
        customerAddressService.delete(addressId, customerId);
    }
    public List<CustomerAddress> getAllByCustomerId(Long customerId) {
        return customerAddressService.getAllByCustomerId(customerId);
    }
}
