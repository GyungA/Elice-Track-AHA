package com.secondproject.shoppingproject.order.dto.order.user;

import lombok.Getter;

@Getter
public class OrderUpdateRequestDto {
    private Long userId;
    private Long OrderId;
    private String deliveryAddress;
    private String receiverName;
    private String receiverPhoneNumber;
}
