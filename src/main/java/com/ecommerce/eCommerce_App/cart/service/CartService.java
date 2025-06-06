package com.ecommerce.eCommerce_App.cart.service;


import com.ecommerce.eCommerce_App.cart.model.dto.CartItemResponse;
import com.ecommerce.eCommerce_App.cart.model.dto.CartResponse;
import com.ecommerce.eCommerce_App.cart.model.entity.Cart;
import com.ecommerce.eCommerce_App.cart.model.entity.CartItem;
import java.util.Optional;

public interface CartService {

    CartResponse toResponse(Cart cart);
    CartItemResponse toResponse(CartItem cartItem);

    Cart addCart(Long customerId);

    CartItem addProductToCart(Long customerId, Long productId, Long quantity);
    void deleteProductFromCart(Long customerId, Long productId, Long quantity);
    void clearCart(Long customerId);


    Optional<Cart> getOptionalCartByCustomerId(Long customerId);
    Cart getCartByCustomerId(Long customerId);
    Optional<Cart> getOptionalCartByCustomerIdWithLock(Long customerId);

    Optional<CartItem> getOptionalCartItemByCartIdAndProductId(Long cartId, Long productId);
    Optional<CartItem> getOptionalCartItemByCartIdAndProductIdWithLock(Long cartId, Long productId);


}
