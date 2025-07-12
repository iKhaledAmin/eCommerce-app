package com.ecommerce.eCommerce_App.stock.service.impl;

import com.ecommerce.eCommerce_App.global.service.impl.EntityRetrievalServiceImpl;
import com.ecommerce.eCommerce_App.product.model.entity.Product;
import com.ecommerce.eCommerce_App.stock.model.dto.RestockResponse;
import com.ecommerce.eCommerce_App.stock.model.dto.StockBatchResponse;
import com.ecommerce.eCommerce_App.stock.model.enity.StockBatch;
import com.ecommerce.eCommerce_App.stock.model.enity.StockItem;
import com.ecommerce.eCommerce_App.stock.model.mapper.StockMapper;
import com.ecommerce.eCommerce_App.stock.repository.StockItemRepo;
import com.ecommerce.eCommerce_App.stock.repository.StockBatchRepo;
import com.ecommerce.eCommerce_App.stock.service.StockService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockBatchRepo stockBatchRepo;
    private final StockItemRepo stockItemRepo;
    private final StockMapper stockMapper;
    private final EntityRetrievalServiceImpl entityRetrievalService;

    @Override
    public StockBatchResponse toStockBatchResponse(StockBatch stockBatch) {
        return stockMapper.toStockBatchResponse(stockBatch);
    }

    @Override
    public RestockResponse toRestockResponse(StockBatch stockBatch) {
        return stockMapper.toRestockResponse(stockBatch);
    }

    @Override
    public StockItem addStockItemForProduct(Product newProduct, Long quantity) {
        StockItem stockItem = new StockItem();

        stockItem.setProduct(newProduct);
        stockItem.setTotalProductQuantity(quantity);

        return stockItemRepo.save(stockItem);
    }

    @Transactional
    @Override
    public StockBatch addStockBatchForProduct(Long productId, Long itemQuantity, BigDecimal itemBuyingPrice, LocalDate itemExpirationDate) {
        Product product = entityRetrievalService.getById(Product.class, productId);

        StockItem stockItem = getOptionalStockItemByProductId(productId)
                .orElseGet(() -> addStockItemForProduct(product, itemQuantity));

        StockBatch stockBatch = new StockBatch();
        Long nextProductBatchNumber = stockBatchRepo.findMaxBatchNumberByStockItemId(stockItem.getId()) + 1;

        stockBatch.setStockItem(stockItem);
        stockBatch.setItemBuyingPrice(itemBuyingPrice);
        stockBatch.setItemQuantity(itemQuantity);
        stockBatch.setItemExpirationDate(itemExpirationDate);
        stockBatch.setArrivalDate(LocalDateTime.now());
        stockBatch.setProductBatchNumber(nextProductBatchNumber);
        stockBatch.setBatchCode(generateBatchCode(productId, stockItem.getId()));

        stockItem.getStockBatches().add(stockBatch);
        return stockBatchRepo.save(stockBatch);

    }

    @Transactional
    @Override
    public StockBatch restockProduct(Long productId,Long itemQuantity, BigDecimal itemBuyingPrice, LocalDate itemExpirationDate) {
         StockBatch stockBatch = addStockBatchForProduct(
                productId,
                itemQuantity,
                itemBuyingPrice,
                itemExpirationDate
        );

        // update stock item quantity
        getOptionalStockItemByProductId(productId).ifPresent(item -> {
            item.setTotalProductQuantity(item.getTotalProductQuantity() + itemQuantity);
            stockItemRepo.save(item);
        });

        return stockBatch;
    }


    @Override
    public List<StockBatch> getAllStockBatchesOrderedByArrivalDate() {
        return stockBatchRepo.findAllOrderByArrivalDateAsc();
    }

    @Override
    public List<StockBatchResponse> getAllStockBatchResponsesOrderedByArrivalDate() {
        return getAllStockBatchesOrderedByArrivalDate()
                .stream()
                .map(this::toStockBatchResponse)
                .toList();
    }

    @Override
    public List<StockBatch> getStockBatchesByProductIdOrderedByArrivalDate(Long productId) {
        return stockBatchRepo.findAllByStockItemProductIdOrderByArrivalDateAsc(productId);
    }

    @Override
    public List<StockBatchResponse> getStockBatchResponsesByProductIdOrderedByArrivalDate(Long productId) {
        return getStockBatchesByProductIdOrderedByArrivalDate(productId)
                .stream()
                .map(this::toStockBatchResponse)
                .toList();
    }





    private Optional<StockItem> getOptionalStockItemByProductId(Long productId) {
        return stockItemRepo.findByProductId(productId);
    }


    private String generateBatchCode(Long productId,Long nextProductBatchNumber) {

        String batchNumber = String.format("%04d", nextProductBatchNumber);  // 0001, 0002, etc.
        String productIdStr = String.format("%04d", productId);  // 0001, 0002, etc.
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHH")); // date in format YYYYMMDD
        String uuidSuffix = UUID.randomUUID().toString().substring(0, 4).toUpperCase(); // 4 random characters

        return "BATCH-" + batchNumber + "-" + productIdStr + "-" + dateStr + "-" + uuidSuffix;
    }
}
