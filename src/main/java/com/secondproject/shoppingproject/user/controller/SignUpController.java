package com.secondproject.shoppingproject.user.controller;

import com.secondproject.shoppingproject.user.dto.request.SignInRequest;
import com.secondproject.shoppingproject.user.dto.request.SignUpRequest;
import com.secondproject.shoppingproject.user.entity.User;
import com.secondproject.shoppingproject.user.service.SignUpService;
import com.secondproject.shoppingproject.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<User> postUser(@RequestBody SignUpRequest signUpRequest){
        User newUser = signUpService.saveUser(signUpRequest);
        return new ResponseEntity<>(newUser,HttpStatus.CREATED);
    }

}
