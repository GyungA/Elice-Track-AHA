package com.secondproject.shoppingproject.order.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderStatus {
    ORDER_PENDING("주문 중"),
    ORDER_COMPLETE("주문완료"),
    ON_DELIVERY("배송중"),
    DELIVERY_OVER("배송완료"),
    //    REQUEST_CANCELLATION("취소신청"),
    CANCELLATION_COMPLETE("취소완료");
//    DELETE("주문내역 삭제");

    private final String name;
}
