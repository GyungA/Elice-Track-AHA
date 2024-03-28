package com.secondproject.shoppingproject.user.service.implement;

import com.secondproject.shoppingproject.user.dto.request.SignInRequest;
import com.secondproject.shoppingproject.user.dto.request.SignUpRequest;
import com.secondproject.shoppingproject.user.dto.response.SignInResponse;
import com.secondproject.shoppingproject.user.dto.response.SignUpResponse;
import com.secondproject.shoppingproject.user.service.UserService;
import org.springframework.http.ResponseEntity;

public class UserServiceImplement implements UserService {


	//TODO
	@Override
	public ResponseEntity<? super SignUpResponse> signUp(SignUpRequest signUpRequest) {

	}

	//TODO
	@Override
	public ResponseEntity<? super SignInResponse> signIn(SignInRequest signInRequest) {

	}
}
