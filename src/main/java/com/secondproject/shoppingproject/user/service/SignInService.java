package com.secondproject.shoppingproject.user.service;

import com.nimbusds.jose.Algorithm;
import com.secondproject.shoppingproject.user.dto.request.SignInRequest;
import com.secondproject.shoppingproject.user.dto.response.SignInResponse;
import com.secondproject.shoppingproject.user.entity.User;
import com.secondproject.shoppingproject.user.exception.InvalidEmailException;
import com.secondproject.shoppingproject.user.exception.InvalidPasswordException;
import com.secondproject.shoppingproject.user.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

import static org.springframework.security.config.Elements.JWT;


@Service
@RequiredArgsConstructor
public class SignInService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String secretKey = "testSecretKey20230327testSecretKey20230327testSecretKey20230327";
//    public final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS512;
//    public User getUser(String email, String password){
//        if(!userRepository.existsByEmail(email) || !userRepository.existsByPassword(password))
//            return null;
//        User user = userRepository.findByEmailAndPassword(email, password);
//        return user;
//    }

    public ResponseEntity<? super SignInResponse> signIn(SignInRequest signInRequest){
        String email = signInRequest.getEmail();
        //String password = passwordEncoder.encode((signInRequest.getPassword()));
        String password = signInRequest.getPassword();
        User user = authenticateUser(email, password);

        String token = generateToken(email);
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

    private String generateToken(String email) {
        // JWT 토큰 생성
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date()) // 토큰 발행 시간 설정
                .setExpiration(new Date(System.currentTimeMillis() + 3600_000)) // 토큰 만료 시간 설정 (현재 시간 + 1시간)
                .signWith(key) // 시크릿 키로 토큰 서명
                .compact(); // 토큰 생성 및 반환
    }

}
