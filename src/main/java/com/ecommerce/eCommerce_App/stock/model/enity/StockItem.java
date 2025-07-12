package com.ecommerce.eCommerce_App.stock.model.enity;

import com.ecommerce.eCommerce_App.product.model.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(name = "stock_items")
public class StockItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_product_quantity", nullable = false, columnDefinition = "bigint default 0")
    private Long totalProductQuantity = 0L;


    // ======================== Relationships ======================== //

    @OneToOne(mappedBy = "stockItem",optional = false)
    private Product product;

    @OneToMany(
            mappedBy = "stockItem",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<StockBatch> stockBatches = new ArrayList<>();
}
