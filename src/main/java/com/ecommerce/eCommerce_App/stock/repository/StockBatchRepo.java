package com.ecommerce.eCommerce_App.stock.repository;

import com.ecommerce.eCommerce_App.global.repository.GenericRepository;
import com.ecommerce.eCommerce_App.stock.model.enity.StockBatch;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockBatchRepo extends GenericRepository<StockBatch, Long> {
    @Override
    default Class<StockBatch> getEntityClass() {
        return StockBatch.class;
    }

    @Query("SELECT COALESCE(MAX(sb.productBatchNumber), 0) FROM StockBatch sb WHERE sb.stockItem.id = :stockItemId")
    Long findMaxBatchNumberByStockItemId(Long stockItemId);

    @Query("SELECT sb FROM StockBatch sb ORDER BY sb.arrivalDate ASC")
    List<StockBatch> findAllOrderByArrivalDateAsc();
    List<StockBatch> findAllByStockItemProductIdOrderByArrivalDateAsc(Long productId);


}
