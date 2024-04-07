package com.secondproject.shoppingproject;

import com.secondproject.shoppingproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DataInit {
    private final UserRepository userRepository;
}
