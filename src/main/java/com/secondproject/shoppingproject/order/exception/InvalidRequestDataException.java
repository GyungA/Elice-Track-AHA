package com.secondproject.shoppingproject.order.exception;

import com.secondproject.shoppingproject.global.exception.BadRequestException;

public class InvalidRequestDataException extends BadRequestException {
    private String MESSAGE;
    public InvalidRequestDataException(String MESSAGE) {
        super(MESSAGE);
    }
}
