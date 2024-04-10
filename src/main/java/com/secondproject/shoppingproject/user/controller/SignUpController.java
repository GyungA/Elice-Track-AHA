package com.secondproject.shoppingproject.user.controller;

import com.secondproject.shoppingproject.user.dto.request.SignUpRequest;
import com.secondproject.shoppingproject.user.dto.response.SignUpResponse;
import com.secondproject.shoppingproject.user.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//@RestController
@Controller
@RequestMapping("/signUp")
public class SignUpController {
    private final SignUpService signUpService;

    @Autowired
    public SignUpController(SignUpService signUpService){
        this.signUpService=signUpService;
    }

//    @PostMapping
//    public ResponseEntity<? super SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest){
//        return signUpService.signUp(signUpRequest);
//    }
    @GetMapping
    public String getSignUp(Model model){
        return "memberJoin/memberJoin";
    }

    @PostMapping
    public String postSignUp(@RequestBody SignUpRequest signUpRequest){
        signUpService.signUp(signUpRequest);
        return "redirect:/success"; // 회원가입 성공 후 이동할 페이지 설정
    }
}
