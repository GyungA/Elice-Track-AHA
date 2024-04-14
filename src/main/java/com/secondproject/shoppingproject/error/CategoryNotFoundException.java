package com.secondproject.shoppingproject.error;

import com.secondproject.shoppingproject.order.exception.EntityNotFoundException;


public class CategoryNotFoundException extends EntityNotFoundException {

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
