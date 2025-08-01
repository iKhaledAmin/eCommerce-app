package com.ecommerce.eCommerce_App.cart.model.entity;

import com.ecommerce.eCommerce_App.product.model.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;


    @Column(nullable = false ,columnDefinition = "bigint default 1")
    private Long quantity; // Quantity of product in cart



    // ======================== Relationships ======================== //

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false,updatable = false)
    private Cart cart;

    // One direction relationship from CartItem to Product
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false,updatable = false)
    private Product product;


}
