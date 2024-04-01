package com.secondproject.shoppingproject.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.secondproject.shoppingproject.user.dto.request.SignUpRequest;
import com.secondproject.shoppingproject.user.dto.response.SignUpResponse;
import com.secondproject.shoppingproject.user.exception.DuplicatedEmailException;
import com.secondproject.shoppingproject.user.exception.DuplicatedPhoneException;
import com.secondproject.shoppingproject.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class SignUpServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private SignUpService signUpService;

	private SignUpRequest signUpRequest;

	@BeforeEach
	public void setUp() {
		signUpRequest = createValidSignUpRequest();
	}

	@Test
	@DisplayName("회원가입 성공")
	void signUp_Success() {
		when(userRepository.existsByEmail("Email")).thenReturn(false);
		when(userRepository.existsByPhone("Phone")).thenReturn(false);

		ResponseEntity<? super SignUpResponse> response = signUpService.signUp(
				signUpRequest);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isInstanceOf(SignUpResponse.class);
	}

	@Test
	@DisplayName("회원가입 실패 중복 이메일")
	void signUp_Fail_Duplicated_Email() {
		when(userRepository.existsByEmail("Email")).thenReturn(true);

		assertThatThrownBy(() -> signUpService.signUp(signUpRequest)).isInstanceOf(
				DuplicatedEmailException.class);
	}

	@Test
	@DisplayName("회원가입 실패 중복 휴대폰 번호")
	void signUp_Fail_Duplicated_Phone() {
		when(userRepository.existsByPhone("Phone")).thenReturn(true);

		assertThatThrownBy(() -> signUpService.signUp(signUpRequest)).isInstanceOf(
				DuplicatedPhoneException.class);
	}

	private SignUpRequest createValidSignUpRequest() {
		SignUpRequest signUpRequest = new SignUpRequest();
		signUpRequest.setEmail("Email");
		signUpRequest.setPassword("Password");
		signUpRequest.setName("Name");
		signUpRequest.setBirthdate("BirthDate");
		signUpRequest.setAddress("Address");
		signUpRequest.setPhone("Phone");

		return signUpRequest;
	}

}