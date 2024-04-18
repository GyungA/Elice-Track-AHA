package com.secondproject.shoppingproject.error;

public class InvalidParentCategoryException extends RuntimeException {
    public InvalidParentCategoryException(String message) {
        super(message);
    }
}
