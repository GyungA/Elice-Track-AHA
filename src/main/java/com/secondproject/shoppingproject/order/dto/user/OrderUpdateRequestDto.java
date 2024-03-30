package com.secondproject.shoppingproject.order.dto.user;

import lombok.Getter;

@Getter
public class OrderUpdateRequestDto {
    private Long userId;
    private Long OrderId;
    private String deliveryAddress;
}
