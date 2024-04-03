package com.secondproject.shoppingproject.order.dto.order.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
@Getter
@Schema(description = "상품을 결제하는 경우 request")
public class PayProductRequestDto {
    private Long userId;

    @Schema(description = "이미 구매하려는 상품이 저장돼 있는 order id")
    private Long orderId;

    //배송지 정보
    private String deliveryAddress;
    private String receiverName;
    private String receiverPhoneNumber;
}
