package com.secondproject.shoppingproject.user.controller;

import com.secondproject.shoppingproject.user.dto.request.SignUpRequest;
import com.secondproject.shoppingproject.user.dto.response.SignUpResponse;
import com.secondproject.shoppingproject.user.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signUp")
public class SignUpController {
    private final SignUpService signUpService;

    @Autowired
    public SignUpController(SignUpService signUpService){
        this.signUpService=signUpService;
    }

    @PostMapping
    public ResponseEntity<? super SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest){
        return signUpService.signUp(signUpRequest);
    }

}
