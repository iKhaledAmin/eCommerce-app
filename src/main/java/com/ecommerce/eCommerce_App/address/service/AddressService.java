package com.ecommerce.eCommerce_App.address.service;

import com.ecommerce.eCommerce_App.address.model.dto.AddressRequest;
import com.ecommerce.eCommerce_App.address.model.dto.AddressResponse;
import com.ecommerce.eCommerce_App.address.model.dto.CustomerAddressResponse;
import com.ecommerce.eCommerce_App.address.model.entity.Address;
import com.ecommerce.eCommerce_App.address.model.entity.CustomerAddress;
import com.ecommerce.eCommerce_App.address.model.enums.AddressType;


import java.util.List;
import java.util.Optional;

public interface AddressService {
    AddressResponse toResponse(Address address);
    CustomerAddressResponse toResponse(CustomerAddress address);
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

    CustomerAddress addCustomerAddress(AddressRequest addressRequest, Long customerId, AddressType addressType, boolean isDefault);
    CustomerAddress updateCustomerAddress(Long addressId, AddressRequest addressRequest, Long customerId, AddressType addressType, boolean isDefault);
    void deleteCustomerAddress(Long addressId, Long customerId);
    List<CustomerAddress> getAllByCustomerId(Long customerId);

}
