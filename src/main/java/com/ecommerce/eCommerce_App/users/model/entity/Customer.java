package com.ecommerce.eCommerce_App.users.model.entity;

import com.ecommerce.eCommerce_App.address.model.entity.CustomerAddress;
import com.ecommerce.eCommerce_App.cart.model.entity.Cart;
import com.ecommerce.eCommerce_App.order.model.entity.Order;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "customer")
@PrimaryKeyJoinColumn(name = "customer_id")
public class Customer extends User {

    @Column(name = "loyalty_points")
    private Integer loyaltyPoints; // Example: Loyalty points for rewards

    @Column(name = "total_spent")
    private double totalSpent; // Example: Total amount spent by the customer

    @Column(name = "last_purchase_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate lastPurchaseDate; // Example: Date of the last purchase

    // Relationships

    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToMany(
            mappedBy = "customer"
            ,cascade = CascadeType.ALL
            ,orphanRemoval = true
            ,fetch = FetchType.LAZY
    )
    private List<CustomerAddress> customerAddresses = new ArrayList<>();


    @OneToMany(
            mappedBy = "customer"
            ,cascade = CascadeType.ALL
            ,orphanRemoval = true
            ,fetch = FetchType.LAZY
    )
    private List<Order> orders = new ArrayList<>();
}
