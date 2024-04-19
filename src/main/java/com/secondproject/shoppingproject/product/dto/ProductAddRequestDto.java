package com.secondproject.shoppingproject.product.dto;

import lombok.Getter;

@Getter
public class ProductAddRequestDto {
    private int price;
    private String name;
    private String status;
    private int stock;
}
