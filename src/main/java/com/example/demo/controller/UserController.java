package com.example.demo.controller;

import com.example.demo.dto.user.UserDTO;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/api/users/getUser/{usrNo}")
    public ResponseEntity<Optional<UserEntity>> getUser(@PathVariable Long usrNo) {
        UserDTO userDTO = UserDTO.builder().usrNo(usrNo).build();
        return new ResponseEntity<>(userService.getUserByUsrNo(userDTO), HttpStatus.OK);
    }
}
