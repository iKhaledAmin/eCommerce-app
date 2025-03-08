package com.ecommerce.eCommerce_App.address.service;

import com.ecommerce.eCommerce_App.address.model.entity.Address;
import com.ecommerce.eCommerce_App.address.model.entity.CustomerAddress;
import com.ecommerce.eCommerce_App.address.model.enums.AddressType;
import com.ecommerce.eCommerce_App.model.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerAddressService {
    CustomerAddress add(Address address, Customer customer, AddressType addressType, boolean isDefault);
    //CustomerAddress add(AddressRequest addressRequest, Long customerId, AddressType addressType, boolean isDefault);

    CustomerAddress update(Address newAddress, Customer customer, AddressType addressType, boolean isDefault);
    //CustomerAddress update(Long addressId, AddressRequest addressRequest, Long customerId, AddressType addressType, boolean isDefault);


    void delete(Long addressId, Long customerId);

    List<CustomerAddress> getAllByCustomerId(Long customerId);
    List<CustomerAddress> getAllByCustomerIdAAddressType(Long customerId, AddressType addressType);
    Optional<CustomerAddress> getByCustomerIdAndAddressId(Long customerId, Long addressId);
}
