package com.secondproject.shoppingproject.user.service;

import com.secondproject.shoppingproject.global.jwt.provider.JwtProvider;
import com.secondproject.shoppingproject.user.dto.request.SignInRequest;
import com.secondproject.shoppingproject.user.dto.request.SignUpRequest;
import com.secondproject.shoppingproject.user.dto.response.SignInResponse;
import com.secondproject.shoppingproject.user.entity.User;
import com.secondproject.shoppingproject.user.exception.InvalidEmailException;
import com.secondproject.shoppingproject.user.exception.InvalidPasswordException;
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

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
@ExtendWith(MockitoExtension.class)
public class SignInServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private SignUpService signUpService;

    @InjectMocks
    private SignInService signInService;

    private SignInRequest signInRequest;

    @BeforeEach
    public void setUp(){
        signUpService.signUp(create_Temp_User());
    }

    @Test
    @DisplayName("로그인 성공")
    void signIn_Success(){
        this.signInRequest = createValidSignInRequest();
        User mockUser = createMockUser(); // Mock 사용자 객체 생성
        when(userRepository.findByEmail(signInRequest.getEmail())).thenReturn(mockUser);
        when(passwordEncoder.matches(signInRequest.getPassword(), mockUser.getPassword())).thenReturn(true);
        ResponseEntity<? super SignInResponse> response = signInService.signIn(signInRequest);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isInstanceOf(SignInResponse.class);
    }

    @Test
    @DisplayName("존재하지 않는 이메일")
    void signIn_Fail_Invalid_Email(){
        this.signInRequest=createInvalidEmailSignInRequest();
        when(userRepository.findByEmail(signInRequest.getEmail())).thenReturn(null);
        assertThatThrownBy(() -> signInService.signIn(signInRequest))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessageContaining("존재하지 않는 이메일 입니다.");
    }

    @Test
    @DisplayName("잘못된 비밀번호")
    void signIn_Fail_Invalid_Password(){
        this.signInRequest=createInvalidPasswordSignInRequest();
        User mockUser = createMockUser(); // Mock 사용자 객체 생성
        when(userRepository.findByEmail(signInRequest.getEmail())).thenReturn(mockUser);
        when(passwordEncoder.matches(signInRequest.getPassword(), mockUser.getPassword())).thenReturn(false);
        assertThatThrownBy(() -> signInService.signIn(signInRequest))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessageContaining("잘못된 비밀번호 입니다.");
    }

    private SignUpRequest create_Temp_User(){
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("Email");
        signUpRequest.setPassword("Password");
        signUpRequest.setName("Name");
        signUpRequest.setBirthdate("BirthDate");
        signUpRequest.setAddress("Address");
        signUpRequest.setPhone("Phone");

        return signUpRequest;
    }
    private SignInRequest createValidSignInRequest(){
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmail("Email");
        signInRequest.setPassword("Password");

        return signInRequest;
    }
    private SignInRequest createInvalidEmailSignInRequest(){
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmail("Non_Existing_Email");
        signInRequest.setPassword("Password");

        return signInRequest;
    }
    private SignInRequest createInvalidPasswordSignInRequest(){
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmail("Email");
        signInRequest.setPassword("Non_Exist_Password");

        return signInRequest;
    }
    private User createMockUser() {
        User user = new User("Email", "Password", "Name", "BirthDate", true, "Address", "Phone");
        return user;
    }
}
