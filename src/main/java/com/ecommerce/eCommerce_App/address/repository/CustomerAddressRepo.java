package com.ecommerce.eCommerce_App.address.repository;

import com.ecommerce.eCommerce_App.address.model.entity.CustomerAddress;
import com.ecommerce.eCommerce_App.address.model.enums.AddressType;
import com.ecommerce.eCommerce_App.global.repository.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CustomerAddressRepo extends GenericRepository<CustomerAddress, Long> {
    @Override
    default Class<CustomerAddress> getEntityClass() {
        return CustomerAddress.class;
    }

    List<CustomerAddress> findAllByCustomerIdAndAddressIdAndAddressType(Long customerId, Long addressId, AddressType addressType);

    List<CustomerAddress> findAllByCustomerIdAndAddressId(Long customerId, Long addressId);

    Optional<CustomerAddress> findByCustomerIdAndAddressId(Long customerId, Long addressId);

    List<CustomerAddress> findAllByCustomerId(Long customerId);

    List<CustomerAddress> findAllByCustomerIdAndAddressType(Long customerId, AddressType addressType);
}
