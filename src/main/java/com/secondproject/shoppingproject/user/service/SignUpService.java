package com.secondproject.shoppingproject.user.service;

import com.secondproject.shoppingproject.user.constant.Grade;
import com.secondproject.shoppingproject.user.constant.Role;
import com.secondproject.shoppingproject.user.dto.request.SignUpRequest;
import com.secondproject.shoppingproject.user.dto.response.SignUpResponse;
import com.secondproject.shoppingproject.user.entity.User;
import com.secondproject.shoppingproject.user.exception.DuplicatedEmailException;
import com.secondproject.shoppingproject.user.exception.DuplicatedPhoneException;
import com.secondproject.shoppingproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public ResponseEntity<? super SignUpResponse> signUp(SignUpRequest signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new DuplicatedEmailException();
		}
		if (userRepository.existsByPhone(signUpRequest.getPhone())) {
			throw new DuplicatedPhoneException();
		}

//        User user = User.builder()
//                .email(signUpRequest.getEmail())
//                .password(passwordEncoder.encode(signUpRequest.getPassword()))
//                .name(signUpRequest.getName())
//                .birthdate(signUpRequest.getBirthdate())
//                .status(signUpRequest.isStatus())
//                .address(signUpRequest.getAddress())
//                .phone(signUpRequest.getPhone()).build();
		User user = User.builder()
				.email(signUpRequest.getEmail())
				//.password(passwordEncoder.encode(signUpRequest.getPassword()))
				.password(signUpRequest.getPassword())
				.name(signUpRequest.getName())
				.birthdate(signUpRequest.getBirthdate())
				.address(signUpRequest.getAddress())
				.phone(signUpRequest.getPhone())
				.grade(Grade.BRONZE) // 기본값 설정
				.role(Role.USER) // 기본값 설정
				.build();

		userRepository.save(user);
		return SignUpResponse.success();
	}
}
