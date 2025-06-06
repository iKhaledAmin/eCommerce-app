package com.ecommerce.eCommerce_App.cart.repository;

import com.ecommerce.eCommerce_App.cart.model.entity.Cart;
import com.ecommerce.eCommerce_App.global.repository.GenericRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends GenericRepository<Cart, Long> {
    @Override
    default Class<Cart> getEntityClass() {
            return Cart.class;
        }

    Optional<Cart> findByCustomerId(Long customerId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Cart c WHERE c.customer.id = :customerId")
    Optional<Cart> findByCustomerIdWithLock(Long customerId);
}
