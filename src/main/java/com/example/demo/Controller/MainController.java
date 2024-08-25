package com.example.demo.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MainController {
    @GetMapping(value = "/")
    public ResponseEntity<String> getUser(){
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }
}
