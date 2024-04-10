package com.secondproject.shoppingproject.order.dto.order.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
@Getter
@Schema(description = "상품을 결제하는 경우 request")
public class PayProductRequestDto {
    private Long userId;

    @Schema(description = "이미 구매하려는 상품이 저장돼 있는 order id")
    private Long orderId;

    //배송지 정보
    @NotNull
    private String deliveryAddress;
    @NotNull
    private String receiverName;
    @NotNull
    @Pattern(regexp = "^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$", message = "올바른 전화번호 형식이 아닙니다.")
    private String receiverPhoneNumber;
}
