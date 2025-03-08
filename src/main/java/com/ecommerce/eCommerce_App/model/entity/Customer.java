package com.ecommerce.eCommerce_App.model.entity;

import com.ecommerce.eCommerce_App.address.model.entity.CustomerAddress;
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
@Table(name = "customer")
@PrimaryKeyJoinColumn(name = "customer_id")
public class Customer extends User {

    @Column(name = "loyalty_points")
    private Integer loyaltyPoints; // Example: Loyalty points for rewards


    @OneToMany(
            mappedBy = "customer"
            ,cascade = CascadeType.ALL
            ,orphanRemoval = true
    )
    private List<CustomerAddress> customerAddresses = new ArrayList<>();
}
