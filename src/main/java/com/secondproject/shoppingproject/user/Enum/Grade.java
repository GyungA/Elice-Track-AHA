package com.secondproject.shoppingproject.user.Enum;

import lombok.Getter;

@Getter
public enum Grade {
    BRONZE("브론즈"), SILVER("실버"), GOLD("골드"), DIAMOND("다이아");

    private String grade;

    Grade(String grade){
        this.grade=grade;
    }
}
