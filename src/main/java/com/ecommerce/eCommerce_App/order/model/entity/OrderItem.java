package com.ecommerce.eCommerce_App.order.model.entity;

import com.ecommerce.eCommerce_App.product.model.entity.Product;
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
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @Column(nullable = false,columnDefinition = "bigint default 1")
    private Integer quantity; // Quantity of product in order

    @Column(name = "unit_price", precision = 19, scale = 2, nullable = false, columnDefinition = "decimal(10,2) default 0.00")
    private BigDecimal unitPrice; // Price snapshot at time of order

    @Column(name = "total_price", precision = 19, scale = 2, nullable = false, columnDefinition = "decimal(10,2) default 0.00")
    private BigDecimal totalPrice;  // Total price of product in order = quantity * unitPrice (before tax/shipping cost)

    // ========== Relationships ========== //

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false, updatable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false, updatable = false)
    private Product product;




    // ========== Business Logic Methods ========== //

    @PrePersist
    public void calculateTotalPrice() {
        this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
    }


}
