package com.secondproject.shoppingproject.order.dto.order.admin;

import com.secondproject.shoppingproject.order.status.OrderStatus;
import lombok.Getter;

@Getter
public class AdminOrderUpdateRequestDto {
    private Long userId; //관리자 아이디
    private Long orderId;
    private OrderStatus orderStatus;
}
