package com.ecommerce.eCommerce_App.cart.model.mapper;

import com.ecommerce.eCommerce_App.cart.model.dto.CartResponse;
import com.ecommerce.eCommerce_App.cart.model.entity.Cart;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring",
        uses = {CartItemMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CartMapper {

    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "items", source = "cartItems")
    CartResponse toResponse(Cart cart);

}