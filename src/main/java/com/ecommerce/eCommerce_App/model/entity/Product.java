package com.ecommerce.eCommerce_App.model.entity;

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
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventoryQuantity;
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

//    @OneToMany(
//            mappedBy = "product"
//            , cascade = CascadeType.ALL
//            , orphanRemoval = true
//    )
//    private List<Image> images = new ArrayList<>();

}
