package com.ecommerce.eCommerce_App.model.entity;

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
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name = "products_count",nullable = false ,columnDefinition = "boolean default false") // Default value in DB
    private int productsCount ; // Default value

    @OneToMany(
            mappedBy = "category"
    )
    private List<Product> products = new ArrayList<>();
}
