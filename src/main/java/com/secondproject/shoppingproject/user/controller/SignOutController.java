package com.secondproject.shoppingproject.user.controller;

import com.secondproject.shoppingproject.user.service.SignOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signOut")
public class SignOutController {
    private final SignOutService signOutService;
    @Autowired
    public SignOutController(SignOutService signOutService) {
        this.signOutService = signOutService;
    }

    @PostMapping
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        String authToken = token.substring(7); // "Bearer " 부분을 제외한 토큰 추출
        signOutService.logout(authToken); // 사용자 로그아웃 처리
        return ResponseEntity.ok().build(); // 로그아웃 성공 응답 반환
    }

}
