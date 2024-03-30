package com.secondproject.shoppingproject.order.dto.user;

import lombok.Getter;

@Getter
public class OrderCancelRequestDto {
    private Long userId;
    private Long OrderId;
}
