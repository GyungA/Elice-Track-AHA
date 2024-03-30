package com.secondproject.shoppingproject.user.controller;

import com.secondproject.shoppingproject.user.dto.LoginDto;
import com.secondproject.shoppingproject.user.dto.SignUpDto;
import com.secondproject.shoppingproject.user.entity.User;
import com.secondproject.shoppingproject.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        List<User> userList = userService.getAllUsers();
        if(userList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long userId){
        User user = userService.getUserById(userId);
        if(user==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> postUser(@RequestBody SignUpDto signUpDto){
        User newUser = userService.saveUser(signUpDto);
        return new ResponseEntity<>(newUser,HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody LoginDto loginDtoDto){
        User loggedInUser = userService.loginUser(loginDtoDto);
        if(loggedInUser == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
    }
    //    @PostMapping("/login")
//    public ResponseEntity<User> loginUser(@RequestParam("email") String email, @RequestParam("password") String password){
//        User loggedInUser = userService.loginUser(email, password);
//        if(loggedInUser == null) {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//        return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
//    }
    @PutMapping("/{userId}")
    public ResponseEntity<User> putUser(@PathVariable("userId") Long userId, @RequestBody SignUpDto signUpDto){
        User user = userService.updateUser(userId,signUpDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUserById(@PathVariable("userId") Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}