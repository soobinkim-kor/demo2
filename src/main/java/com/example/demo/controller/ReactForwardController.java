package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReactForwardController {

    @RequestMapping({
            "/users/**",
            "/login",
            "/main"
    })
    public String forward() {
        return "forward:/index.html";
    }
}