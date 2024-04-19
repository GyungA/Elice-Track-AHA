package com.secondproject.shoppingproject.cart.controller;

import com.secondproject.shoppingproject.cart.dto.CartItemDto;
import com.secondproject.shoppingproject.cart.entity.CartItem;
import com.secondproject.shoppingproject.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/list")
    public String viewCart() {
        return "cart/cart";
    }

    @PostMapping
    public String addCartItem(@RequestBody CartItemDto cartItemDto) {
        CartItem newCartItem = cartService.addCartItem(cartItemDto);
        return "cart/cart";
    }
}
