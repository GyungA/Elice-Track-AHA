package com.secondproject.shoppingproject.user.exception;


import com.secondproject.shoppingproject.global.exception.BadRequestException;

public class DuplicatedPhoneException extends BadRequestException {

    private static final String MESSAGE = "중복된 핸드폰 번호 입니다.";

    public DuplicatedPhoneException() {
        super(MESSAGE);
    }
}
