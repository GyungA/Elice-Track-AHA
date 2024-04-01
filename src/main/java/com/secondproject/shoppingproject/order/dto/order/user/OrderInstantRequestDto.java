package com.secondproject.shoppingproject.order.dto.order.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "상품 바로 구매하는 경우 request")
public class OrderInstantRequestDto {
    private Long userId;
    private Long productId;
    private int amount;
    private String deliveryAddress;
    private String receiverName;
    private String receiverPhoneNumber;
}
