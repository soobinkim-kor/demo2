package com.example.demo.controller;

import com.example.demo.dto.SignUpRequest;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.UserBase;
import com.example.demo.service.UserService;
import com.example.demo.service.UserSignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserSignUpService userSignUpService;

    @GetMapping(value = "/test")
    public ResponseEntity<String> controllerTest(@RequestBody UserBase userParam){
        UserBase user = UserBase.builder().usrNm("Soobin").build();
        userService.saveUser(user);
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }

    @GetMapping(value = "/api/users/getUser/{usrNo}")
    public ResponseEntity<Optional<UserBase>> getUser(@PathVariable Long usrNo){
        UserDTO userDTO = UserDTO.builder().usrNo(usrNo).build();

        return new ResponseEntity<>(userService.getUserByUsrNo(userDTO), HttpStatus.OK);
    }

    @PostMapping(value = "/createUser")
    public ResponseEntity<UserBase> saveUser(@RequestBody UserDTO userParam){
        UserBase user = UserBase.builder()
                .usrNm("Soobin")
                .usrId("shb03207")
                .build();
        SignUpRequest signUpRequest =
                SignUpRequest.builder()
                        .usrId("shb03207")
                        .usrNm("김수빈")
                        .password("1234")
                        .build();
        userSignUpService.signUp(signUpRequest);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/createUser")
    public ResponseEntity<UserBase> saveUserTest(){
        UserBase user = UserBase.builder()
                .usrId("shb03207")
                .usrNm("Soobin")
                .usrEmail("shb03207@gmail.com")
                .usrPwd("1234")
                .build();
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
