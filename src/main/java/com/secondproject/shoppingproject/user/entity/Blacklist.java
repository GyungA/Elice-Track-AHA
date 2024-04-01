package com.secondproject.shoppingproject.user.entity;

import java.util.HashSet;
import java.util.Set;

public class Blacklist {
    private static final Set<String> blacklistedTokens = new HashSet<>();

    // 토큰을 블랙리스트에 추가하는 메서드
    public static void add(String token) {
        blacklistedTokens.add(token);
    }

    // 토큰이 블랙리스트에 있는지 여부를 확인하는 메서드
    public static boolean isBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }

    // 토큰을 블랙리스트에서 제거하는 메서드
    public static void remove(String token) {
        blacklistedTokens.remove(token);
    }
}
