package com.ecommerce.eCommerce_App.category.model.entity;

import com.ecommerce.eCommerce_App.product.model.entity.Product;
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
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "products_count",nullable = false ,columnDefinition = "boolean default false") // Default value in DB
    private int productsCount ; // Default value

    @OneToMany(
            mappedBy = "category"
    )
    private List<Product> products = new ArrayList<>();
}
