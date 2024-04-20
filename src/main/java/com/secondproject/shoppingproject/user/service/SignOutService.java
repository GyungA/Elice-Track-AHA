package com.secondproject.shoppingproject.user.service;

import com.secondproject.shoppingproject.user.entity.Blacklist;
import org.springframework.stereotype.Service;

@Service
public class SignOutService {
    public void logout(String token) {
        // 여기서는 토큰을 무효화하는 로직을 구현
        // 실제로는 블랙리스트에 해당 토큰을 추가하거나, 토큰의 만료 시간을 조정하는 등의 방법으로 구현할 수 있습니다.
        // 여기서는 예시로 블랙리스트에 토큰을 추가하는 방식을 사용합니다.
        Blacklist.add(token);
    }
}
