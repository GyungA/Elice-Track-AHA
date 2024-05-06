package com.secondproject.shoppingproject.user.exception;

import com.secondproject.shoppingproject.global.exception.BadRequestException;

public class InvalidPasswordException extends BadRequestException {

    private static final String MESSAGE = "잘못된 비밀번호 입니다.";

    public InvalidPasswordException(){
        super(MESSAGE);
    }
}
