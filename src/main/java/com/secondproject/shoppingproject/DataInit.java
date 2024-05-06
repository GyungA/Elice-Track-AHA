package com.secondproject.shoppingproject;

import com.secondproject.shoppingproject.category.repository.CategoryRepository;
import com.secondproject.shoppingproject.product.entity.ProductRepository;
import com.secondproject.shoppingproject.user.constant.Grade;
import com.secondproject.shoppingproject.user.constant.Role;
import com.secondproject.shoppingproject.user.dto.request.SignUpRequest;
import com.secondproject.shoppingproject.user.entity.User;
import com.secondproject.shoppingproject.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class DataInit {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;
//    String[] name = new String[] {"Apple", "Banana", "Cherry", "Donut", "EarRing", "SoccerBall", "Bat", "Chocolate", "Skirt", "Coat", "Pant", "Book", "Paper", "Stapler", "Eraser", "Skin","Lotto"};
//    @PostConstruct
//    public void init() {
//
//        SignUpRequest signup1 = new SignUpRequest("asdf@naver.com", "asdf1234","Kim","900101","Seoul","01012345678");
//        SignUpRequest signup2 = new SignUpRequest("qwer@google.com", "qwer5678","Lee","001212","Busan","01098765432");
//        SignUpRequest signup3 = new SignUpRequest("zxcv@daum.com", "zxcv9876","Park","850802","Jeju","01043219876");
//
//        User user1 = User.builder()
//                .email(signup1.getEmail())
//                .password(passwordEncoder.encode(signup1.getPassword()))
//                .name(signup1.getName())
//                .birthdate(signup1.getBirthdate())
//                .address(signup1.getAddress())
//                .phone(signup1.getPhone())
//                .grade(Grade.BRONZE) // 기본값 설정
//                .role(Role.USER) // 기본값 설정
//                .build();
//
//        User user2 = User.builder()
//                .email(signup2.getEmail())
//                .password(passwordEncoder.encode(signup2.getPassword()))
//                .name(signup2.getName())
//                .birthdate(signup2.getBirthdate())
//                .address(signup2.getAddress())
//                .phone(signup2.getPhone())
//                .grade(Grade.BRONZE) // 기본값 설정
//                .role(Role.USER) // 기본값 설정
//                .build();
//
//        User user3 = User.builder()
//                .email(signup3.getEmail())
//                .password(passwordEncoder.encode(signup3.getPassword()))
//                .name(signup3.getName())
//                .birthdate(signup3.getBirthdate())
//                .address(signup3.getAddress())
//                .phone(signup3.getPhone())
//                .grade(Grade.BRONZE) // 기본값 설정
//                .role(Role.USER) // 기본값 설정
//                .build();
//        userRepository.save(user1);
//        userRepository.save(user2);
//        userRepository.save(user3);
//        for(int i=0; i<name.length; i++){
//
//        }
//    }
}
