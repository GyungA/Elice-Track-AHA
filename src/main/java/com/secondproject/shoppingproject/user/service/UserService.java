package com.secondproject.shoppingproject.user.service;

import com.secondproject.shoppingproject.user.dto.request.SignInRequest;
import com.secondproject.shoppingproject.user.dto.request.SignUpRequest;
import com.secondproject.shoppingproject.user.dto.response.SignInResponse;
import com.secondproject.shoppingproject.user.dto.response.SignUpResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

	ResponseEntity<? super SignUpResponse> signUp(SignUpRequest signUpRequest);

	ResponseEntity<?super SignInResponse> signIn(SignInRequest signInRequest);
}
