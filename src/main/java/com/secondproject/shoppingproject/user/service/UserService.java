package com.secondproject.shoppingproject.user.service;

import com.secondproject.shoppingproject.user.dto.LoginDto;
import com.secondproject.shoppingproject.user.dto.SignUpDto;
import com.secondproject.shoppingproject.user.entity.User;
import com.secondproject.shoppingproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(SignUpDto userDto){
        return userRepository.save(userDto.toEntity());
    }

    public User updateUser(Long userId, SignUpDto signUpDto){
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setEmail(signUpDto.getEmail());
            user.setPassword(signUpDto.getPassword());
            user.setName(signUpDto.getName());
            user.setBirthdate(signUpDto.getBirthdate());
            //  user.setGrade(signUpDto.getGrade());
            user.setStatus(signUpDto.getStatus());
            user.setAddress(signUpDto.getAddress());
            user.setPhone(signUpDto.getPhone());
            // user.setRole(signUpDto.getRole());
            return userRepository.save(user);
        }
        return null;
    }

    public void deleteUser(Long userId){
        User user = userRepository.findById(userId).orElse(null);
        userRepository.delete(user);
    }
    public User registerUser(SignUpDto signUpDto) {
        User user = userRepository.save(signUpDto.toEntity());
        return user;
    }
    public User loginUser(LoginDto loginDtoDto) {
        return userRepository.findByEmailAndPassword(loginDtoDto.getEmail(), loginDtoDto.getPassword());
    }
//    public User loginUser(String email, String password) {
//        return userRepository.findByEmailAndPassword(email, password);
//    }
}
