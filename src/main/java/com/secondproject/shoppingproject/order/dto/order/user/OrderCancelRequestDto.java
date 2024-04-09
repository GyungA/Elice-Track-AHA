package com.secondproject.shoppingproject.order.dto.order.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor //test
@AllArgsConstructor //test
public class OrderCancelRequestDto {
    private Long userId;
    private Long orderId; //없애기??
    private Long orderDetailId;
}
