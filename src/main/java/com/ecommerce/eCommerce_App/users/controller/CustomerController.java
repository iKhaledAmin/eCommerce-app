package com.ecommerce.eCommerce_App.users.controller;

import com.ecommerce.eCommerce_App.address.model.dto.AddCustomerAddressRequest;
import com.ecommerce.eCommerce_App.address.model.dto.CustomerAddressResponse;
import com.ecommerce.eCommerce_App.address.model.dto.UpdatedCustomerAddressRequest;
import com.ecommerce.eCommerce_App.address.model.entity.CustomerAddress;
import com.ecommerce.eCommerce_App.address.model.enums.AddressType;
import com.ecommerce.eCommerce_App.address.service.CustomerAddressService;
import com.ecommerce.eCommerce_App.users.model.dto.CustomerResponse;
import com.ecommerce.eCommerce_App.users.model.dto.RegistrationRequest;
import com.ecommerce.eCommerce_App.users.model.dto.UserRequest;
import com.ecommerce.eCommerce_App.users.model.entity.Customer;
import com.ecommerce.eCommerce_App.users.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerAddressService customerAddressService;



    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> registerCustomer(@Valid @RequestBody RegistrationRequest registrationRequest) {
        // Call the service method to register the customer
        Customer registeredCustomer = customerService.register(registrationRequest);

        // Convert the registered customer to a response DTO
        CustomerResponse customerResponse = customerService.toResponse(registeredCustomer);

        // Return the response with HTTP status 201 (Created)
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }


    @PutMapping("/{customerId}/profile")
    public ResponseEntity<CustomerResponse> editProfile(
            @PathVariable Long customerId,
            @Valid @RequestPart(required = true, name = "user_request") UserRequest userRequest,
            @RequestPart(required = false, name = "new_image_file") MultipartFile newImageFile
    ) {

        // Call the service method to edit the customer's profile and convert the updated customer to a response DTO
        CustomerResponse customerResponse = customerService.toResponse(
                customerService.editProfile(customerId, userRequest, newImageFile)
        );

        // Return the response with HTTP status 200 (OK)
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }


    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long customerId) {

        // fetch the customer and then Convert the customer to a response DTO
        CustomerResponse customerResponse = customerService.toResponse(customerService.getById(customerId));

        // Return the response with HTTP status 200 (OK)
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {

        // Fetch all customers and Convert the list of customers to a list of response DTOs
        List<CustomerResponse> customerResponses = customerService.getAll()
                .stream()
                .map(customerService::toResponse)
                .toList();

        // Return the response with HTTP status 200 (OK)
        return new ResponseEntity<>(customerResponses, HttpStatus.OK);
    }


    @PostMapping("/{customerId}/addresses")
    public ResponseEntity<CustomerAddressResponse> addAddress(
            @PathVariable Long customerId,
            @Valid @RequestBody AddCustomerAddressRequest addCustomerAddressRequest) {

        // Add the address and Convert the address to a response DTO
         CustomerAddressResponse response = customerAddressService.toResponse(
                 customerAddressService.add(customerId, addCustomerAddressRequest)
         );

        // Return the response with HTTP status 201 (Created)
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PutMapping("/{customerId}/addresses")
    public ResponseEntity<CustomerAddressResponse> updateAddress(
            @PathVariable Long customerId,
            @Valid @RequestBody UpdatedCustomerAddressRequest UpdatedCustomerAddressRequest
    ) {

        // Add the address and Convert the address to a response DTO
        CustomerAddressResponse response = customerAddressService.toResponse(
                customerAddressService.update(customerId, UpdatedCustomerAddressRequest)
        );

        // Return the response with status 200 OK
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{customerId}/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long customerId, @PathVariable Long addressId) {
        customerAddressService.delete(customerId, addressId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{customerId}/addresses")
    public ResponseEntity<List<CustomerAddressResponse>> getAllAddressesByCustomerIdAndType (
            @PathVariable Long customerId,
            @RequestParam(required = false,name = "address_type" ) AddressType addressType
    ) {

        // Fetch all addresses and Convert the list of addresses to a list of response DTOs
        List<CustomerAddressResponse>  customerAddressResponses = customerAddressService.getAllByCustomerIdAAddressType(customerId, addressType)
                .stream()
                .map(customerAddressService::toResponse)
                .toList();

        // Return the response with HTTP status 200 (OK)
        return new ResponseEntity<>(customerAddressResponses, HttpStatus.OK);
    }



}
