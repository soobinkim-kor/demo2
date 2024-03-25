package com.example.demo.Controller;

import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MainController {

    private final UserService userService;
    @GetMapping(value = "/test")
    public ResponseEntity<String> controllerTest(@RequestBody User userParam){
        User user = User.builder().userName("Soobin").build();
        userService.saveUser(user);
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }

    @GetMapping(value = "/")
    public ResponseEntity<User> controllerTest(){
        User user = User.builder().userName("Soobin").build();
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
