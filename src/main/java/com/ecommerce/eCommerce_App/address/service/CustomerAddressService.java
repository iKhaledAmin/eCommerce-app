package com.ecommerce.eCommerce_App.address.service;

import com.ecommerce.eCommerce_App.address.model.dto.AddCustomerAddressRequest;
import com.ecommerce.eCommerce_App.address.model.dto.CustomerAddressResponse;
import com.ecommerce.eCommerce_App.address.model.dto.UpdatedCustomerAddressRequest;
import com.ecommerce.eCommerce_App.address.model.entity.Address;
import com.ecommerce.eCommerce_App.address.model.entity.CustomerAddress;
import com.ecommerce.eCommerce_App.address.model.enums.AddressType;
import com.ecommerce.eCommerce_App.users.model.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerAddressService {

    CustomerAddressResponse toResponse(CustomerAddress entity);

    CustomerAddress add(Address address, Customer customer, AddressType addressType, boolean isDefault);
    CustomerAddress add(Long customerId, AddCustomerAddressRequest addCustomerAddressRequest);

    CustomerAddress update(Address newAddress, Customer customer, AddressType addressType, boolean isDefault);
    CustomerAddress update(Long customerId, UpdatedCustomerAddressRequest updatedCustomerAddressRequest);


    void delete(Long customerId, Long addressId);

    List<CustomerAddress> getAllByCustomerIdAAddressType(Long customerId, AddressType addressType);
    Optional<CustomerAddress> getByCustomerIdAndAddressId(Long customerId, Long addressId);
}
