package com.secondproject.shoppingproject.order.dto.order.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor //test
public class OrderCancelRequestDto {
    private Long userId;
    private Long orderId;
}
