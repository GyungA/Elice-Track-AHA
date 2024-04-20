package com.secondproject.shoppingproject.order.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderStatus {
    ORDER_PENDING("주문 중", 1),
    ORDER_COMPLETE("주문완료", 2),
    ON_DELIVERY("배송중", 3),
    DELIVERY_OVER("배송완료", 4),
    CANCELLATION_COMPLETE("취소완료", 5);

    private final String name;
    private final int number;
}
