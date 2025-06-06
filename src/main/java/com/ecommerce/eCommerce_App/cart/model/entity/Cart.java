package com.ecommerce.eCommerce_App.cart.model.entity;

import com.ecommerce.eCommerce_App.users.model.entity.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @Column(name = "product_count",columnDefinition = "bigint default 0")
    private Long productCount; // Number of distinct products in the cart

    @Column(name = "item_count",columnDefinition = "bigint default 0")
    private Long itemCount; // Number of all items in the cart

    @Column(name = "total_amount",columnDefinition = "decimal(10,2) default 0")
    private BigDecimal totalAmount; // Total amount of all items in the cart



    // Relationships

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false,updatable = false)
    private Customer customer;

    @OneToMany(
            mappedBy = "cart"
            ,cascade = CascadeType.ALL
            ,orphanRemoval = true
            ,fetch = FetchType.LAZY
    )
    private List<CartItem> cartItems = new ArrayList<>();

}
