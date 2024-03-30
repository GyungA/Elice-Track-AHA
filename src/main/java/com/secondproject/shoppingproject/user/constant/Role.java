package com.secondproject.shoppingproject.user.constant;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("관리자"), USER("사용자");

    private String role;

    Role(String role){
        this.role=role;
    }
}
