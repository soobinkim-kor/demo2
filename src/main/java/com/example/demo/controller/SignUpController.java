package com.example.demo.controller;

import com.example.demo.request.SignUpRequest;
import com.example.demo.service.UserSignUpService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUpController {

    private final UserSignUpService userSignUpService;

    public SignUpController(UserSignUpService userSignUpService) {
        this.userSignUpService = userSignUpService;
    }

    @PostMapping("/signup")
    public String signUp(SignUpRequest request) {

        userSignUpService.signUp(request);
        return "redirect:/login";
    }
}
