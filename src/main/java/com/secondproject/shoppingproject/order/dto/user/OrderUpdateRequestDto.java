package com.secondproject.shoppingproject.order.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class OrderUpdateRequestDto {
    private Long userId;
    private Long OrderId;
    private String deliveryAddress;
}
