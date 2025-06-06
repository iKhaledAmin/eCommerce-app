package com.ecommerce.eCommerce_App.cart.service.impl;

import com.ecommerce.eCommerce_App.cart.exception.CartOperationException;
import com.ecommerce.eCommerce_App.cart.model.dto.CartItemResponse;
import com.ecommerce.eCommerce_App.cart.model.dto.CartResponse;
import com.ecommerce.eCommerce_App.cart.model.entity.Cart;
import com.ecommerce.eCommerce_App.cart.model.entity.CartItem;
import com.ecommerce.eCommerce_App.cart.model.mapper.CartItemMapper;
import com.ecommerce.eCommerce_App.cart.model.mapper.CartMapper;
import com.ecommerce.eCommerce_App.cart.repository.CartItemRepo;
import com.ecommerce.eCommerce_App.cart.repository.CartRepo;
import com.ecommerce.eCommerce_App.cart.service.CartService;
import com.ecommerce.eCommerce_App.product.model.entity.Product;
import com.ecommerce.eCommerce_App.global.service.EntityRetrievalService;
import com.ecommerce.eCommerce_App.product.service.ProductService;
import com.ecommerce.eCommerce_App.users.model.entity.Customer;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;


@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;
    private final ProductService productService;
    private final EntityRetrievalService entityRetrievalService;


    @Override
    public CartResponse toResponse(Cart cart) {
        return cartMapper.toResponse(cart);
    }

    @Override
    public CartItemResponse toResponse(CartItem cartItem) {
        return cartItemMapper.toResponse(cartItem);
    }

    @Override
    public Cart addCart(Long customerId) {
        Cart cart = createCart();

        Customer customer = entityRetrievalService.getById(Customer.class, customerId);
        cart.setCustomer(customer);

        return cartRepo.save(cart);
    }

    @Override
    @Transactional
    public CartItem addProductToCart(Long customerId, Long productId, Long quantity) {

        Customer customer = entityRetrievalService.getById(Customer.class, customerId);
        Product product = entityRetrievalService.getById(Product.class, productId);

        // Lock the cart first
        Cart cart = getOptionalCartByCustomerIdWithLock(customer.getId())
                .orElseGet(() -> addCart(customerId));

        Product updatedProduct = null;
        try {
            // Lock and update inventory
            updatedProduct = productService.pickProductFromInventory(product.getId(), quantity);

            // Find existing cart item with lock
            Optional<CartItem> optionalCartItem = getOptionalCartItemByCartIdAndProductIdWithLock(
                    cart.getId(), product.getId()
            );

            // Add or update cart item
            CartItem cartItem;
            if (optionalCartItem.isEmpty()) {
                cartItem = new CartItem();
                cartItem.setCart(cart);
                cartItem.setProduct(updatedProduct);
                cartItem.setQuantity(quantity);
                cartItemRepo.save(cartItem);        // Persists CartItem first
                cart.getCartItems().add(cartItem);  // Then adds to collection
            } else {
                cartItem = optionalCartItem.get();
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItem.setProduct(updatedProduct);
            }

            // Update cart aggregates
            cart = updateCartAggregates(cart);
            return cartItem;

            // Rollback inventory if anything fails
        } catch (Exception e) {
            // Check if product was picked from inventory
            if (updatedProduct != null) {
                try {
                    productService.restockProductInInventory(updatedProduct.getId(), quantity);
                } catch (Exception ex) {
                    //log.error("Failed to restock product {} after cart operation failed", productId, ex);
                }
            }
            throw new CartOperationException("Failed to add product to cart");
        }
    }


    @Override
    @Transactional
    public void deleteProductFromCart(Long customerId, Long productId, Long quantity) {

        Customer customer = entityRetrievalService.getById(Customer.class, customerId);
        Product product = entityRetrievalService.getById(Product.class, productId);

        // Lock the cart
        Optional<Cart> optionalCart = getOptionalCartByCustomerIdWithLock(customer.getId());
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();

            // Lock the cart item
            CartItem cartItem = getOptionalCartItemByCartIdAndProductIdWithLock(cart.getId(), product.getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            String.format(" %s not found in cart", product.getName())));

            // Validate quantity
            if (quantity > cartItem.getQuantity()) {
                throw new CartOperationException(
                        String.format("Requested %d items but only %d in cart",
                                quantity, cartItem.getQuantity()));
            }

            // Try to decrease item quantity Or remove it from cart
            try {

                // Remove cart item
                if (quantity > cartItem.getQuantity())
                    throw new IllegalArgumentException(product.getName() + " quantity in cart is less than " + quantity);
                else if (quantity.equals(cartItem.getQuantity()))
                    cart.getCartItems().remove(cartItem);
                else
                    cartItem.setQuantity(cartItem.getQuantity() - quantity);

                // Update aggregates
                updateCartAggregates(cart);
                cartRepo.save(cart);

                // Restock inventory (only after successful cart update)
                productService.restockProductInInventory(product.getId(), quantity);

            } catch (Exception e) {
                //log.error("Failed to remove product from cart", e);
                throw new CartOperationException("Failed to remove product from cart");
            }
        }

    }

    @Transactional
    @Override
    public void clearCart(Long customerId) {

        Customer customer = entityRetrievalService.getById(Customer.class, customerId);
        Optional<Cart> optionalCart = getOptionalCartByCustomerIdWithLock(customer.getId());
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            cart.getCartItems().clear();
            cart.setItemCount(0L);
            cart.setProductCount(0L);
            cart.setTotalAmount(BigDecimal.valueOf(0.0));
            cartRepo.save(cart);
        }
    }


    @Override
    public Optional<Cart> getOptionalCartByCustomerId(Long customerId) {
        return cartRepo.findByCustomerId(customerId);
    }

    @Override
    public Cart getCartByCustomerId(Long customerId) {
        return getOptionalCartByCustomerId(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));
    }

    @Override
    public Optional<Cart> getOptionalCartByCustomerIdWithLock(Long customerId) {
        return cartRepo.findByCustomerIdWithLock(customerId);
    }

    @Override
    public Optional<CartItem> getOptionalCartItemByCartIdAndProductId(Long cartId, Long productId) {
        return cartItemRepo.findByCartIdAndProductId(cartId, productId);
    }

    @Override
    public Optional<CartItem> getOptionalCartItemByCartIdAndProductIdWithLock(Long cartId, Long productId) {
        return cartItemRepo.findByCartIdAndProductIdWithLock(cartId, productId);
    }





    private Cart createCart() {
        Cart cart = new Cart();
        cart.setItemCount(0L);
        cart.setProductCount(0L);
        cart.setTotalAmount(BigDecimal.valueOf(0.0));
        return cart;
    }

    private Long calculateProductCount(Cart cart) {
        return (long) cart.getCartItems().size(); // the number of distinct products is the size of the cart items list
    }

    private Long calculateItemCount(Cart cart) {
        return cart.getCartItems()
                .stream()
                .mapToLong(CartItem::getQuantity)
                .sum();
    }

    private BigDecimal calculateTotalAmount(Cart cart) {
        return cart.getCartItems().stream()
                .map(item -> item.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Cart updateCartAggregates(Cart cart) {
        cart.setProductCount(calculateProductCount(cart));
        cart.setItemCount(calculateItemCount(cart));
        cart.setTotalAmount(calculateTotalAmount(cart));

        return cartRepo.save(cart);
    }

}
