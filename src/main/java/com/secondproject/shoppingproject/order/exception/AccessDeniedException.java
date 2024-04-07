package com.secondproject.shoppingproject.order.exception;

import com.secondproject.shoppingproject.global.exception.ForbiddenException;

public class AccessDeniedException extends ForbiddenException {
    private String MESSAGE;
    public AccessDeniedException(String MESSAGE) {
        super(MESSAGE);
    }
}
