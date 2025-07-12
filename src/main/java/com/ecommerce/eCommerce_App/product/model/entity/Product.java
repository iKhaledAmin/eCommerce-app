package com.ecommerce.eCommerce_App.product.model.entity;

import com.ecommerce.eCommerce_App.category.model.entity.Category;
import com.ecommerce.eCommerce_App.product.model.enums.Status;
import com.ecommerce.eCommerce_App.stock.model.enity.StockItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = true)
    private String name;

    @Column(nullable = false, updatable = true)
    private String description;

    @Column(nullable = true, updatable = true)
    private String brand;

    @Column(name = "selling_price", nullable = true, updatable = true, columnDefinition = "decimal(10,2) default 0.00")
    private BigDecimal sellingPrice ;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, updatable = true, columnDefinition = "VARCHAR(20) DEFAULT 'INACTIVE'")
    private Status status = Status.INACTIVE;


    // ======================== Relationships ======================== //


    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToOne(cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "stock_item_id", nullable = false)
    private StockItem stockItem;

}
