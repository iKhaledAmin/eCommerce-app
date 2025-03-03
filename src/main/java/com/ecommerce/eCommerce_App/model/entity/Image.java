package com.ecommerce.eCommerce_App.model.entity;

import com.ecommerce.eCommerce_App.model.enums.EntityType;
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
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageName;

    @Column(nullable = false)
    private String imageType; // e.g., "image/png", "image/jpeg"

    @Column(nullable = false)
    private String storagePath; // The path where the image is stored in the file system or cloud service.

    @Column(nullable = false)
    private Long entityId; // Stores the ID of the associated entity

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EntityType entityType; // Enum for strict type control

    @Column(nullable = false)
    private String uniqueFileName; // Unique file name to avoid collisions

    @Column(nullable = false)
    private boolean isPrimary = false; // Default to false
}