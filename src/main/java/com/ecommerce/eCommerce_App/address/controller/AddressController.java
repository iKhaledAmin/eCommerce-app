package com.ecommerce.eCommerce_App.address.controller;

import com.ecommerce.eCommerce_App.address.model.dto.*;
import com.ecommerce.eCommerce_App.address.model.entity.City;
import com.ecommerce.eCommerce_App.address.model.entity.Country;
import com.ecommerce.eCommerce_App.address.model.enums.AddressType;
import com.ecommerce.eCommerce_App.address.service.AddressService;
import com.ecommerce.eCommerce_App.address.service.CountryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/addresses")
@AllArgsConstructor
public class AddressController {
    private final AddressService addressService;
    private final CountryService countryService ;

    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(@Valid @RequestBody AddressRequest addressRequest) {
        // Add the new address
        AddressResponse addressResponse = addressService.toResponse(addressService.add(addressRequest));

        // Return the response with status 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(addressResponse);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponse> updateAddress(
            @PathVariable Long addressId,
            @Valid @RequestBody AddressRequest addressRequest
    ) {
        // Update the address
        AddressResponse addressResponse = addressService.toResponse(addressService.update(addressId, addressRequest));

        // Return the response with status 200 OK
        return ResponseEntity.ok(addressResponse);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable Long addressId) {
        // Fetch the address
        AddressResponse addressResponse = addressService.toResponse(addressService.getById(addressId));

        // Return the response with status 200 OK
        return ResponseEntity.ok(addressResponse);
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> getAllAddresses() {
        // Fetch all addresses
        List<AddressResponse> addressResponses = addressService.getAll().stream()
                .map(addressService::toResponse)
                .collect(Collectors.toList());

        // Return the response with status 200 OK
        return ResponseEntity.ok(addressResponses);
    }


    @GetMapping("/countries")
    public ResponseEntity<List<CountryResponse>> getAllCountries() {
        List<Country> countries = countryService.getAllCountries();
        List<CountryResponse> response = countries
                .stream()
                .map(countryService::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/countries/{countryId}/cities")
    public ResponseEntity<List<CityResponse>> getCitiesByCountry(@PathVariable Long countryId) {
        List<City> cities = countryService.getCitiesByCountry(countryId);
        List<CityResponse> response = cities
                .stream()
                .map(countryService::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/countries/cities")
    public ResponseEntity<List<CityResponse>> getAllCities() {
        List<City> cities = countryService.getAllCities();
        List<CityResponse> response = cities
                .stream()
                .map(countryService::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }


    // ==================== Customer-Specific Address Endpoints ====================

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<CustomerAddressResponse> addCustomerAddress(
            @PathVariable Long customerId,
            @Valid @RequestBody AddressRequest addressRequest,
            @RequestParam AddressType addressType,
            @RequestParam boolean isDefault
    ) {
        CustomerAddressResponse customerAddressResponse = addressService.toResponse(
                addressService.addCustomerAddress(addressRequest, customerId, addressType, isDefault)
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(customerAddressResponse);
    }

    @PutMapping("/customers/{customerId}/addresses/{addressId}")
    public ResponseEntity<CustomerAddressResponse> updateCustomerAddress(
            @PathVariable Long customerId,
            @PathVariable Long addressId,
            @Valid @RequestBody AddressRequest addressRequest,
            @RequestParam AddressType addressType,
            @RequestParam boolean isDefault
    ) {
        CustomerAddressResponse customerAddressResponse = addressService.toResponse(
                addressService.updateCustomerAddress(addressId, addressRequest, customerId, addressType, isDefault)
        );
        return ResponseEntity.ok(customerAddressResponse);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<CustomerAddressResponse>> getAllCustomerAddresses(@PathVariable Long customerId) {
        List<CustomerAddressResponse> customerAddressResponses = addressService.getAllByCustomerId(customerId).stream()
                .map(addressService::toResponse)
                .toList();
        return ResponseEntity.ok(customerAddressResponses);
    }

    @DeleteMapping("/{addressId}/customer/{customerId}")
    public ResponseEntity<Void> deleteCustomerAddress(
            @PathVariable Long addressId,
            @PathVariable Long customerId
    ) {
        addressService.deleteCustomerAddress(addressId, customerId);
        return ResponseEntity.noContent().build();
    }

}
