package com.secondproject.shoppingproject.user.service;

import com.secondproject.shoppingproject.user.entity.User;
import com.secondproject.shoppingproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInService {

    private final UserRepository userRepository;

    public User getUser(String email, String password){
        if(!userRepository.existsByEmail(email) || !userRepository.existsByPassword(password))
            return null;
        User user = userRepository.findByEmailAndPassword(email, password);
        return user;
    }
}
