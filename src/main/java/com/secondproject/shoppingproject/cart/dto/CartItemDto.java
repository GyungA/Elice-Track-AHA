package com.secondproject.shoppingproject.cart.dto;

import lombok.Getter;

@Getter
public class CartItemDto {
    private Long userId;
    private Long productId;
    private int count;
}
