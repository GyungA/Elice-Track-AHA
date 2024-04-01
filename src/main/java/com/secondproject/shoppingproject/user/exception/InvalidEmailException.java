package com.secondproject.shoppingproject.user.exception;

import com.secondproject.shoppingproject.global.exception.BadRequestException;

public class InvalidEmailException extends BadRequestException {
    private static final String MESSAGE = "존재하지 않는 이메일 입니다.";

    public InvalidEmailException(){
        super(MESSAGE);
    }
}
