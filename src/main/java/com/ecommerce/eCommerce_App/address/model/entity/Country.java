package com.ecommerce.eCommerce_App.address.model.entity;

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
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "code", nullable = false, unique = true)
    private String code; // ISO country code (e.g., "US" for United States)

    @OneToMany(
            mappedBy = "country"
            ,cascade = CascadeType.ALL
            ,orphanRemoval = true
            ,fetch = FetchType.LAZY
    )
    private List<City> cities = new ArrayList<>();
}