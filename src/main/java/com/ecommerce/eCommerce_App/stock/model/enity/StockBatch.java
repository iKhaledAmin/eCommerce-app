package com.ecommerce.eCommerce_App.stock.model.enity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(
        name = "stock_batches",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"stock_item_id", "product_batch_number"}
        )
)
public class StockBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "batch_code", nullable = false, updatable = false, unique = true)
    private String batchCode;

    @Column(name = "product_batch_number", nullable = false, updatable = false)
    private Long productBatchNumber;// for tracking specific shipments

    @Column(name = "arrival_date", nullable = false, updatable = false)
    private LocalDateTime arrivalDate;  // when this stock batch arrived

    @Column(name = "item_quantity", nullable = false, columnDefinition = "bigint default 0")
    private Long itemQuantity;

    @Column(name = "item_buying_price", nullable = false, updatable = false, columnDefinition = "decimal(10,2) default 0.00")
    private BigDecimal itemBuyingPrice;

    @Column(name = "item_expiration_date", nullable = true)
    private LocalDate itemExpirationDate;   // if products can expire







    // ======================== Relationships ======================== //


    @ManyToOne(optional = false)
    @JoinColumn(name = "stock_item_id", nullable = false, updatable = false)
    private StockItem stockItem;

}
