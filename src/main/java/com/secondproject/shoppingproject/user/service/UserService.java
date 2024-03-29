package com.secondproject.shoppingproject.user.service;

import com.secondproject.shoppingproject.user.dto.request.SignInRequest;
import com.secondproject.shoppingproject.user.dto.request.SignUpRequest;
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

//    public User saveUser(SignUpRequest userDto){
//        return userRepository.save(userDto.toEntity());
//    }

    public User updateUser(Long userId, SignUpRequest signUpRequest){
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setEmail(signUpRequest.getEmail());
            user.setPassword(signUpRequest.getPassword());
            user.setName(signUpRequest.getName());
            user.setBirthdate(signUpRequest.getBirthdate());
          //  user.setGrade(signUpDto.getGrade());
//            user.setStatus(signUpRequest.isStatus());
            user.setAddress(signUpRequest.getAddress());
            user.setPhone(signUpRequest.getPhone());
           // user.setRole(signUpDto.getRole());
            return userRepository.save(user);
        }
        return null;
    }

    public void deleteUser(Long userId){
        User user = userRepository.findById(userId).orElse(null);
        userRepository.delete(user);
    }

//    public User loginUser(SignInRequest signInRequest) {
//        return userRepository.findByEmailAndPassword(signInRequest.getEmail(), signInRequest.getPassword());
//    }
//    public User loginUser(String email, String password) {
//        return userRepository.findByEmailAndPassword(email, password);
//    }
}
