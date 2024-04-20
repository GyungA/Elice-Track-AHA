package com.secondproject.shoppingproject.user.controller;

import com.secondproject.shoppingproject.user.dto.request.SignInRequest;
import com.secondproject.shoppingproject.user.entity.User;
import com.secondproject.shoppingproject.user.service.SignInService;
import com.secondproject.shoppingproject.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signIn")
public class SignInController {
    private final SignInService signInService;

    @Autowired
    public SignInController(SignInService signInService){
        this.signInService=signInService;
    }

    @GetMapping
    public ResponseEntity<User> get_User(@RequestBody SignInRequest signInRequest){
        User user = signInService.getUser(signInRequest.getEmail(), signInRequest.getPassword());
        if(user==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
