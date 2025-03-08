package com.ecommerce.eCommerce_App.address.model.entity;

import com.ecommerce.eCommerce_App.address.model.enums.AddressType;
import com.ecommerce.eCommerce_App.model.entity.Customer;
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
@Table(
    name = "customer_address",
    uniqueConstraints = {
            @UniqueConstraint(columnNames = {"customer_id", "address_id"})
    }
)
public class CustomerAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_address_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false)
    private Address address;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_type", nullable = false)
    private AddressType addressType; // Enum for address type (e.g., SHIPPING, BILLING)

    @Column(name = "is_default",nullable = false ,columnDefinition = "boolean default false") // Default value in DB
    private boolean isDefault ;// Example: Indicates if this is the user's default address

}
