package com.secondproject.shoppingproject.order.dto.order.user;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor //test용
public class OrderUpdateRequestDto {
    private Long userId;
    private Long orderId;
    private String deliveryAddress;
    private String receiverName;

    @Pattern(regexp = "^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$", message = "올바른 전화번호 형식이 아닙니다.")
    private String receiverPhoneNumber;
}
