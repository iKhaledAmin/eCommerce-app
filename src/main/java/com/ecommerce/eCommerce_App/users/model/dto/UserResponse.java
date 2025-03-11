package com.ecommerce.eCommerce_App.users.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("account")
    private String account;

    @JsonProperty("password")
    private String password;

    @JsonProperty("email_address")
    private String emailAddress;

    @JsonProperty("birthday")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthday;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("profession")
    private String profession;


    @JsonProperty("profile_image_url")
    private String profileImageUrl;

    @JsonProperty("user_role")
    private String userRole; // Enum for roles (e.g., ROLE_ADMIN, ROLE_CUSTOMER)

    @JsonProperty("date_of_joining")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateOfJoining;

}
