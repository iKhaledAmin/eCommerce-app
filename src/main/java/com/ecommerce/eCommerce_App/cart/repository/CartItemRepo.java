package com.ecommerce.eCommerce_App.cart.repository;

import com.ecommerce.eCommerce_App.cart.model.entity.CartItem;
import com.ecommerce.eCommerce_App.global.repository.GenericRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepo extends GenericRepository<CartItem, Long> {
    @Override
    default Class<CartItem> getEntityClass() {
        return CartItem.class;
    }

    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.id = :productId")
    Optional<CartItem> findByCartIdAndProductIdWithLock(Long cartId, Long productId);
}
