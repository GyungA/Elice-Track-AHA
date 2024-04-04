package com.secondproject.shoppingproject.order.dto.order.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class OrderUpdateRequestDto {
    private Long userId;
    private Long orderId;
    @NotNull
    private String deliveryAddress;
    @NotNull
    private String receiverName;
    @NotNull
    @Pattern(regexp = "^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$", message = "올바른 전화번호 형식이 아닙니다.")
    private String receiverPhoneNumber;
}
