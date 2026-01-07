package com.example.demo.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MainController {
    @GetMapping("/api/hello")
    public String test() {
        return "Hello, world!";
    }
}
