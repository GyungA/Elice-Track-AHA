package com.secondproject.shoppingproject.category.service;

public class InvalidParentCategoryException extends RuntimeException {
    public InvalidParentCategoryException(String message) {
        super(message);
    }
}
