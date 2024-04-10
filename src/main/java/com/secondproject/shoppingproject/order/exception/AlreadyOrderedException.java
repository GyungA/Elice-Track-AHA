package com.secondproject.shoppingproject.order.exception;

import com.secondproject.shoppingproject.global.exception.ConflictException;

public class AlreadyOrderedException extends ConflictException {
    private static final String MESSAGE = "이미 결제한 주문입니다.";
    public AlreadyOrderedException() {
        super(MESSAGE);
    }
}