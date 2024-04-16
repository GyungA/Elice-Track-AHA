package com.secondproject.shoppingproject.user.service;

import com.secondproject.shoppingproject.global.jwt.provider.JwtProvider;
import com.secondproject.shoppingproject.user.dto.request.SignInRequest;
import com.secondproject.shoppingproject.user.dto.response.SignInResponse;
import com.secondproject.shoppingproject.user.entity.User;
import com.secondproject.shoppingproject.user.exception.InvalidEmailException;
import com.secondproject.shoppingproject.user.exception.InvalidPasswordException;
import com.secondproject.shoppingproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SignInService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public ResponseEntity<? super SignInResponse> signIn(SignInRequest signInRequest){
        String email = signInRequest.getEmail();
        String password = signInRequest.getPassword();
        User user = authenticateUser(email, password);

        String token = jwtProvider.generate(email);
        return SignInResponse.success(token, 60*60*1000);
    }

    private User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);

        if(user==null){
            throw new InvalidEmailException();
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException();
        }
        return user;
    }

    public User get_User(String email){
        return userRepository.findByEmail(email);
    }
}
