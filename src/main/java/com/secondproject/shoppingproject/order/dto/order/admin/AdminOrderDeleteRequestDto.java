package com.secondproject.shoppingproject.order.dto.order.admin;

import lombok.Getter;

@Getter
public class AdminOrderDeleteRequestDto {
    private Long userId; //관리자 아이디
    private Long orderId;
}
