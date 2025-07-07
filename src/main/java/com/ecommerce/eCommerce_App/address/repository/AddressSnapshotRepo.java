package com.ecommerce.eCommerce_App.address.repository;

import com.ecommerce.eCommerce_App.address.model.entity.AddressSnapshot;
import com.ecommerce.eCommerce_App.global.repository.GenericRepository;

public interface AddressSnapshotRepo extends GenericRepository<AddressSnapshot, Long> {
    @Override
    default Class<AddressSnapshot> getEntityClass() {
        return AddressSnapshot.class;
    }
}
