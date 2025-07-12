package com.ecommerce.eCommerce_App.stock.service;

import com.ecommerce.eCommerce_App.product.model.entity.Product;
import com.ecommerce.eCommerce_App.stock.model.dto.RestockResponse;
import com.ecommerce.eCommerce_App.stock.model.dto.StockBatchResponse;
import com.ecommerce.eCommerce_App.stock.model.enity.StockBatch;
import com.ecommerce.eCommerce_App.stock.model.enity.StockItem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface StockService {

    StockBatchResponse toStockBatchResponse(StockBatch stockBatch);
    RestockResponse toRestockResponse(StockBatch stockBatch);

    StockItem addStockItemForProduct(Product newProduct, Long quantity);
    StockBatch addStockBatchForProduct(Long productId, Long itemQuantity, BigDecimal itemBuyingPrice, LocalDate itemExpirationDate);
    StockBatch restockProduct(Long productId,Long itemQuantity, BigDecimal itemBuyingPrice, LocalDate itemExpirationDate);



    List<StockBatch> getAllStockBatchesOrderedByArrivalDate();
    List<StockBatchResponse> getAllStockBatchResponsesOrderedByArrivalDate();

    List<StockBatch> getStockBatchesByProductIdOrderedByArrivalDate(Long productId);
    public List<StockBatchResponse> getStockBatchResponsesByProductIdOrderedByArrivalDate(Long productId);

}
