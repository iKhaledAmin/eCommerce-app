package com.ecommerce.eCommerce_App.cart.controller;

import com.ecommerce.eCommerce_App.cart.model.dto.CartItemResponse;
import com.ecommerce.eCommerce_App.cart.model.dto.CartResponse;
import com.ecommerce.eCommerce_App.cart.model.entity.Cart;
import com.ecommerce.eCommerce_App.cart.model.entity.CartItem;
import com.ecommerce.eCommerce_App.cart.service.CartService;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/{customerId}")
    public ResponseEntity<CartResponse> getCart(@PathVariable Long customerId) {
        Cart cart = cartService.getCartByCustomerId(customerId);
        return ResponseEntity.ok(cartService.toResponse(cart));
    }

    // add product to cart

    @PostMapping("/{customerId}/{productId}")
    public ResponseEntity<CartItemResponse> addToCart(
            @PathVariable Long customerId,
            @PathVariable Long productId,
            @RequestParam(defaultValue = "1", required = false) @Min(1) Long quantity
    ) {

        CartItem cartItem = cartService.addProductToCart(customerId, productId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.toResponse(cartItem));

    }

    @DeleteMapping("/{customerId}/{productId}")
    public ResponseEntity<Void> deleteProductFromCart(
            @PathVariable Long customerId,
            @PathVariable Long productId,
            @RequestParam(defaultValue = "1", required = false) @Min(1) Long quantity
    ) {
        cartService.deleteProductFromCart(customerId, productId, quantity);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long customerId) {
        cartService.clearCart(customerId);
        return ResponseEntity.noContent().build();
    }
}
