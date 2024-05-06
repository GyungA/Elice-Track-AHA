package com.secondproject.shoppingproject.user.controller;

import com.secondproject.shoppingproject.user.dto.request.SignInRequest;
import com.secondproject.shoppingproject.user.dto.request.SignUpRequest;
import com.secondproject.shoppingproject.user.dto.response.SignInResponse;
import com.secondproject.shoppingproject.user.entity.User;
import com.secondproject.shoppingproject.user.exception.InvalidEmailException;
import com.secondproject.shoppingproject.user.exception.InvalidPasswordException;
import com.secondproject.shoppingproject.user.service.SignInService;
import com.secondproject.shoppingproject.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class SignInController {
    private final SignInService signInService;

    @Autowired
    public SignInController(SignInService signInService){
        this.signInService=signInService;
    }

    //    @GetMapping
//    public ResponseEntity<User> get_User(@RequestBody SignInRequest signInRequest){
//        User user = signInService.getUser(signInRequest.getEmail(), signInRequest.getPassword());
//        if(user==null){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }
//    @GetMapping
//    public ResponseEntity<? super SignInResponse> get_User(@RequestBody SignInRequest signInRequest){
//        return signInService.signIn(signInRequest);
//    }
    @GetMapping("/signIn")
    public String getSignIn(Model model){
        model.addAttribute("signInRequest", new SignInRequest());
        return "login";
    }

    @PostMapping("/signIn")
    public String signIn(@RequestBody SignInRequest signInRequest, Model model, HttpServletRequest request) {
        try {
            ResponseEntity<? super SignInResponse> responseEntity = signInService.signIn(signInRequest);
            SignInResponse signInResponse = (SignInResponse) responseEntity.getBody();
            model.addAttribute("message", "로그인 성공");
            model.addAttribute("token", signInResponse.getToken()); // 로그인 성공시 토큰을 받아서 저장하는 예시
            HttpSession session = request.getSession();
            User user = signInService.get_User(signInRequest.getEmail());
            session.setAttribute("user", user);
            return "redirect:/";  // 로그인 성공 후 홈페이지로 이동
           // return "login/login";
        } catch (InvalidEmailException | InvalidPasswordException e) {
            model.addAttribute("error", "이메일 또는 비밀번호가 올바르지 않습니다.");
            return "login";
        }
//        signInService.signIn(signInRequest);
//        return "redirect:/";
    }
}
