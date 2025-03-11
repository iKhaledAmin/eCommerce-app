package com.ecommerce.eCommerce_App.users.model.entity;

import com.ecommerce.eCommerce_App.users.model.enums.Gender;
import com.ecommerce.eCommerce_App.users.model.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public  class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "first_name" ,nullable = false)
    private String firstName;

    @Column(name = "last_name" ,nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false,updatable = false)
    private String account;

    @Column(nullable = false)
    private String password;

    @Column(name = "email_address" ,unique = true)
    private String emailAddress;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "phone_number")
    private String phoneNumber;
    private String profession;


    @Column(name = "profile_image_url")
    private String profileImageUrl;

    // Role-based access control (RBAC)
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role" ,nullable = false)
    private UserRole userRole; // Enum for roles (e.g., ROLE_ADMIN, ROLE_CUSTOMER)

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateOfJoining;


}
