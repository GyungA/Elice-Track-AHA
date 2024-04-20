package com.secondproject.shoppingproject.order.exception;

import com.secondproject.shoppingproject.global.exception.ConflictException;

public class OrderModificationDeniedException extends ConflictException {
    private String MESSAGE;

    public OrderModificationDeniedException(String MESSAGE) {
        super("이미 상품이 배송 중이거나, 또는 배송지에 도착하였기 때문에 주문 " + MESSAGE + "이(가) 불가합니다.");
    }
}
