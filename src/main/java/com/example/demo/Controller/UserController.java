package com.example.demo.Controller;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Entity.UserBase;
import com.example.demo.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping(value = "/test")
    public ResponseEntity<String> controllerTest(@RequestBody UserBase userParam){
        UserBase user = UserBase.builder().usrNm("Soobin").build();
        userService.saveUser(user);
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }

    @GetMapping(value = "/getUser")
    public ResponseEntity<UserBase> getUser(@RequestParam String usrId){
        UserDTO userDTO = UserDTO.builder().usrId(usrId).build();

        return new ResponseEntity<>(userService.getUserByUsrId(userDTO), HttpStatus.OK);
    }

    @PostMapping(value = "/createUser")
    public ResponseEntity<UserBase> saveUser(@RequestBody UserDTO userParam){
        UserBase user = UserBase.builder()
                .usrNm("Soobin")
                .usrId("shb03207")
                .build();
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/createUser")
    public ResponseEntity<UserBase> saveUserTest(){
        UserBase user = UserBase.builder()
                .usrNm("Soobin")
                .usrEmail("shb03207@gmail.com")
                .usrPwd("1234")
                .build();
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
