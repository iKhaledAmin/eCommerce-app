package com.ecommerce.eCommerce_App.stock.repository;


import com.ecommerce.eCommerce_App.global.repository.GenericRepository;
import com.ecommerce.eCommerce_App.stock.model.enity.StockItem;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockItemRepo extends GenericRepository<StockItem, Long> {
    @Override
    default Class<StockItem> getEntityClass() {
        return StockItem.class;
    }

    Optional<StockItem> findByProductId(Long productId);
}
