package com.ecommerce.eCommerce_App.address.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Size(max = 255)
    @Column(name = "street", nullable = false)
    private String street;

    @Size(max = 100)
    @Column(name = "state", nullable = true)
    private String state;

    @Size(max = 20)
    @Column(name = "zip_code", nullable = true)
    private String zipCode;



    @OneToMany(
            mappedBy = "address"
            ,cascade = CascadeType.ALL
            , orphanRemoval = true
            , fetch = FetchType.LAZY
    )
    private List<CustomerAddress> customerAddresses = new ArrayList<>();

}
