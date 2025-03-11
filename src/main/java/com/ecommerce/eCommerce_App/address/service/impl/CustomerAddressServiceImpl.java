package com.ecommerce.eCommerce_App.address.service.impl;

import com.ecommerce.eCommerce_App.address.model.dto.AddCustomerAddressRequest;
import com.ecommerce.eCommerce_App.address.model.dto.CustomerAddressResponse;
import com.ecommerce.eCommerce_App.address.model.dto.UpdatedCustomerAddressRequest;
import com.ecommerce.eCommerce_App.address.model.entity.Address;
import com.ecommerce.eCommerce_App.address.model.entity.CustomerAddress;
import com.ecommerce.eCommerce_App.address.model.enums.AddressType;
import com.ecommerce.eCommerce_App.address.model.mapper.CustomerAddressMapper;
import com.ecommerce.eCommerce_App.address.repository.CustomerAddressRepo;
import com.ecommerce.eCommerce_App.address.service.AddressService;
import com.ecommerce.eCommerce_App.address.service.CustomerAddressService;
import com.ecommerce.eCommerce_App.exception.ConflictException;
import com.ecommerce.eCommerce_App.users.model.entity.Customer;
import com.ecommerce.eCommerce_App.service.EntityRetrievalService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomerAddressServiceImpl implements CustomerAddressService {
    private final CustomerAddressRepo customerAddressRepo;
    private final CustomerAddressMapper customerAddressMapper;
    private final AddressService addressService;
    private final EntityRetrievalService entityRetrievalService;


    @Override
    public CustomerAddressResponse toResponse(CustomerAddress entity) {
        return customerAddressMapper.toResponse(entity);
    }

    private CustomerAddress save(CustomerAddress customerAddress) {
        return customerAddressRepo.save(customerAddress);
    }

    private CustomerAddress create(Address address, Customer customer, AddressType addressType, boolean isDefault) {
        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setAddress(address);
        customerAddress.setCustomer(customer);
        customerAddress.setAddressType(addressType);
        customerAddress.setDefault(isDefault);
        return customerAddress;
    }
    private void setDefaultAddress(Long customerId, AddressType addressType, CustomerAddress newDefaultAddress) {
        entityRetrievalService.getById(Customer.class,customerId);
        // Fetch all CustomerAddress entities for the Customer and AddressType
        List<CustomerAddress> customerAddresses = getAllByCustomerIdAAddressType(customerId, addressType);

        // Set all other addresses as non-default
        customerAddresses.forEach(address -> {
            if (!address.getId().equals(newDefaultAddress.getId())) {
                address.setDefault(false);
                save(address);
            }
        });

        // Set the new address as default
        newDefaultAddress.setDefault(true);
    }

    @Override
    public CustomerAddress add(Address newAddress, Customer customer, AddressType addressType, boolean isDefault) {
        // Check if the CustomerAddress already exists
         getByCustomerIdAndAddressId(customer.getId(), newAddress.getId()).ifPresent(address -> {
             throw new ConflictException("Customer address already exists!");
         });

        // Create a new CustomerAddress entity
        CustomerAddress customerAddress = create(newAddress, customer, addressType, isDefault);

        // Set default address if necessary
        if (isDefault) {
            setDefaultAddress(customer.getId(), addressType, customerAddress);
        }

        // Add the CustomerAddress to the Customer and Address entities
        newAddress.getCustomerAddresses().add(customerAddress);
        customer.getCustomerAddresses().add(customerAddress);

        // Save the CustomerAddress entity
        return save(customerAddress);
    }
    @Transactional
    @Override
    public CustomerAddress add(Long customerId, AddCustomerAddressRequest addCustomerAddressRequest) {
        // Fetch the Customer entity
        Customer customer = entityRetrievalService.getById(Customer.class, customerId);

        // Add the new Address
        Address newAddress = addressService.add(addCustomerAddressRequest);

        // Link the Address to the Customer
        return add(
                newAddress,
                customer,
                addCustomerAddressRequest.getAddressType(),
                addCustomerAddressRequest.isDefault()
        );
    }

    @Override
    public CustomerAddress update(Address newAddress, Customer customer, AddressType addressType, boolean isDefault) {
        // Fetch the existing CustomerAddress entity
        CustomerAddress existingCustomerAddress = getByCustomerIdAndAddressId(customer.getId(), newAddress.getId())
                .orElseGet(() -> add(newAddress, customer, addressType, isDefault));

        // Set default address if necessary
        if (isDefault) {
            setDefaultAddress(customer.getId(), addressType, existingCustomerAddress);
        }

        // Update the CustomerAddress entity
        existingCustomerAddress.setAddress(newAddress);
        existingCustomerAddress.setAddressType(addressType);
        existingCustomerAddress.setDefault(isDefault);

        // Save the updated CustomerAddress entity
        return save(existingCustomerAddress);
    }

    @Transactional
    @Override
    public CustomerAddress update(Long customerId, UpdatedCustomerAddressRequest updatedCustomerAddressRequest) {
        // Fetch the Customer entity
        Customer customer = entityRetrievalService.getById(Customer.class, customerId);

        // Update the Address
        Address newAddress = addressService.update(
                updatedCustomerAddressRequest.getAddressId(),
                updatedCustomerAddressRequest
        );

        // Update the CustomerAddress entity
        return update(
                newAddress,
                customer,
                updatedCustomerAddressRequest.getAddressType(),
                updatedCustomerAddressRequest.isDefault()
        );
    }

    @Transactional
    @Override
    public void delete(Long customerId, Long addressId) {
        // Fetch the Customer and Address entities
        Customer customer = entityRetrievalService.getById(Customer.class, customerId);
        Address address = entityRetrievalService.getById(Address.class, addressId);

        // Fetch the CustomerAddress entity
        CustomerAddress customerAddress = getByCustomerIdAndAddressId(customerId, addressId)
                .orElseThrow(() -> new NoSuchElementException("Customer address not found!"));

        // Remove the CustomerAddress from the Customer and Address entities
        address.getCustomerAddresses().remove(customerAddress);
        customer.getCustomerAddresses().remove(customerAddress);

        // Delete the CustomerAddress entity
        customerAddressRepo.delete(customerAddress);
    }

    public List<CustomerAddress> getAllByCustomerIdAAddressType(Long customerId, AddressType addressType) {
        entityRetrievalService.getById(Customer.class,customerId);

        if (addressType == null)
            return customerAddressRepo.findAllByCustomerId(customerId);
        return customerAddressRepo.findAllByCustomerIdAndAddressType(customerId, addressType);
    }
    @Override
    public Optional<CustomerAddress> getByCustomerIdAndAddressId(Long customerId, Long addressId) {
        return customerAddressRepo.findByCustomerIdAndAddressId(customerId, addressId);
    }



}
