package com.ecommerce.eCommerce_App.cart.model.mapper;

import com.ecommerce.eCommerce_App.cart.model.dto.CartItemResponse;
import com.ecommerce.eCommerce_App.cart.model.entity.CartItem;
import com.ecommerce.eCommerce_App.product.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productImageUrl", expression = "java(getImageUrl(cartItem.getProduct()))")
    @Mapping(target = "productPrice", source = "product.price")
    @Mapping(target = "itemTotalAmount", expression = "java(calculateItemTotal(cartItem))")
    CartItemResponse toResponse(CartItem cartItem);

    default String getImageUrl(Product product) {
        // Implement your image URL logic (e.g., from CDN)
        return "/products/" + product.getId() + "/image";
    }

    default BigDecimal calculateItemTotal(CartItem item) {
        return item.getProduct().getPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()));
    }
}