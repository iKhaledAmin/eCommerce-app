package com.ecommerce.eCommerce_App.address.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "address_snapshots")
public class AddressSnapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_snapshot_id")
    private Long id;

    // ========== Immutable address details ========== //
    @Column(name = "street" ,nullable = false, updatable = false)
    private String street;

    @Column(name = "city" ,nullable = false, updatable = false)
    private String city;  // Stored as string to avoid city data changes affecting orders

    @Column(name = "country" ,nullable = false, updatable = false)
    private String country;  // Same as above for country

    @Column(name = "state" ,nullable = false, updatable = false)
    private String state;

    @Column(name = "zip_code",nullable = false ,updatable = false)
    private String zipCode;

    // Reference to original address (for auditing)
    @Column(name = "original_address_id",nullable = false ,updatable = false)
    private Long originalAddressId;

    // When this snapshot was taken
    @Column(name = "snapshot_date", nullable = false, updatable = false)
    private LocalDateTime snapshotDate;

    // ========== Relationships ========== //


    // ========== Mutable address details ========== //

    @PrePersist
    protected void onCreate() {
        this.snapshotDate = LocalDateTime.now();  // Auto-set when created
    }
}
