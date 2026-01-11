package com.example.demo.controller;

import com.example.demo.entity.UserEntity;
import com.example.demo.request.user.SignUpRequest;
import com.example.demo.service.UserSignUpService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SignUpController {

    private final UserSignUpService userSignUpService;

    public SignUpController(UserSignUpService userSignUpService) {
        this.userSignUpService = userSignUpService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserEntity> signUp(@RequestBody SignUpRequest request) {

        UserEntity userEntity = userSignUpService.signUp(request);
        return new ResponseEntity<>(userEntity, HttpStatus.CREATED);
    }
}
