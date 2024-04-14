package com.secondproject.shoppingproject.user.controller;

import com.secondproject.shoppingproject.user.dto.request.SignUpRequest;
import com.secondproject.shoppingproject.user.dto.response.SignUpResponse;
import com.secondproject.shoppingproject.user.service.SignUpService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

//@RestController
@Controller
@RequestMapping("/signUp")
public class SignUpController {
    private final SignUpService signUpService;

    @Autowired
    public SignUpController(SignUpService signUpService){
        this.signUpService=signUpService;
    }

//    @PostMapping
//    public ResponseEntity<? super SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest){
//        return signUpService.signUp(signUpRequest);
//    }
    @GetMapping
    public String getSignUp(Model model){
        model.addAttribute("signUpRequest", new SignUpRequest());
        return "member-join/memberJoin";
    }

//    @PostMapping
//    public String postNewSignUp(@Valid @ModelAttribute("signUpRequest") SignUpRequest signUpRequest,
//                                BindingResult bindingResult){
////        if (bindingResult.hasErrors()) {
////            return "member-join/memberJoin"; // 유효성 검사 에러 발생 시 다시 회원가입 폼으로 이동
////        }
//
//        signUpService.signUp(signUpRequest);
//        return "redirect:/"; // 회원가입 성공 후 로그인 페이지로 이동
//    }
    @PostMapping
    public String postNewSignUp(@RequestBody SignUpRequest signUpRequest,
                            BindingResult bindingResult){
//        if (bindingResult.hasErrors()) {
//            return "member-join/memberJoin"; // 유효성 검사 에러 발생 시 다시 회원가입 폼으로 이동
//        }

        signUpService.signUp(signUpRequest);
        return "redirect:/"; // 회원가입 성공 후 로그인 페이지로 이동
    }
}
