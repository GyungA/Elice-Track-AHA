package com.secondproject.shoppingproject.product.dto;

import lombok.Getter;

@Getter
public class ProductAddRequestDto {
    private Long categoryId;
    private Long sellerId;
    private int price;
    private String name;
    private String status;
    private int stock;
}
