package com.ecommerce.eCommerce_App.order.model.entity;

import com.ecommerce.eCommerce_App.address.model.entity.AddressSnapshot;
import com.ecommerce.eCommerce_App.address.model.entity.CustomerAddress;
import com.ecommerce.eCommerce_App.order.model.enums.OrderStatus;
import com.ecommerce.eCommerce_App.users.model.entity.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "order_number", unique = true, nullable = false, updatable = false)
    private String orderNumber; // Custom generated (e.g., ORD-20240615-0001)

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(name = "total_price", precision = 19, scale = 2, nullable = false)
    private BigDecimal totalPrice;  // Total price of product in order = quantity * unitPrice (before tax/shipping cost)

    @Column(name = "total_tax", precision = 19, scale = 2, nullable = false)
    private BigDecimal totalTax = BigDecimal.ZERO;

    @Column(name = "shipping_cost", precision = 19, scale = 2, nullable = false)
    private BigDecimal shippingCost = BigDecimal.ZERO;

    @Column(name = "discount_amount", precision = 19, scale = 2, nullable = false)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "grand_total", precision = 19, scale = 2, nullable = false)
    private BigDecimal grandTotal;  // Total price of product in order = quantity * unitPrice (before tax/shipping cost)

    @Column(name = "product_count",columnDefinition = "bigint default 0",nullable = false)
    private Long productCount; // Number of distinct products in the order

    @Column(name = "item_count",columnDefinition = "bigint default 0",nullable = false)
    private Long itemCount; // Number of all items in the order

    // ========== Relationships ========== //

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    private Customer customer;

    @OneToOne( cascade = CascadeType.ALL,orphanRemoval = true ,optional = false)
    @JoinColumn(name = "billing_address_id", nullable = false)
    private AddressSnapshot billingAddress;

    @OneToOne( cascade = CascadeType.ALL,orphanRemoval = true ,optional = false)
    @JoinColumn(name = "shipping_address_id", nullable = false)
    private AddressSnapshot shippingAddress;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private final List<OrderItem> items = new ArrayList<>();




    // ========== Business Logic Methods ========== //

    @PrePersist
    public void onPrePersist() {
        generateOrderNumber();
        calculateTotals();
    }

    @PreUpdate
    public void onPreUpdate() {
        calculateTotals();
    }

    /**
     * Generates a unique order number in format ORD-YYYYMMDD-XXXX
     *  where XXXX is a random 4-digit number
     *  YYYYMMDD is the current date
     *  e.g., ORD-20240615-0001
     *  e.g., ORD-20240615-0002
     */
    private void generateOrderNumber() {
        if (this.orderNumber == null) {
            this.orderNumber = "ORD-" +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) +
                    "-" + String.format("%04d", (int)(Math.random() * 10000));
        }
    }

    /**
     * Calculates all monetary totals
     */
    public void calculateTotals() {
        // Calculate sum of all order items
        this.totalPrice = items.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate grand total
        this.grandTotal =
                totalPrice
                .add(totalTax)
                .add(shippingCost)
                .subtract(discountAmount);
    }


}
