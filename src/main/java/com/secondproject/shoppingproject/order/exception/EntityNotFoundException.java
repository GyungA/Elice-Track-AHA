package com.secondproject.shoppingproject.order.exception;

import com.secondproject.shoppingproject.global.exception.NotFoundException;

public class EntityNotFoundException extends NotFoundException {
    private String MESSAGE;
    public EntityNotFoundException(String MESSAGE) {
        super(MESSAGE);
    }
}
