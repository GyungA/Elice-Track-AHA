package com.secondproject.shoppingproject.user.constant;

import lombok.Getter;

@Getter
public enum Grade {
    BRONZE("브론즈", 0, 1.0), SILVER("실버", 100_0000, 0.99), GOLD("골드", 500_0000, 0.97), DIAMOND("다이아", 1000_0000, 0.95);

    private String grade;
    private long minPurchaseAmount;
    private double sale;

    Grade(String grade, long minPurchaseAmount, double sale){
        this.grade = grade;
        this.minPurchaseAmount = minPurchaseAmount;
        this.sale=sale;
    }
}