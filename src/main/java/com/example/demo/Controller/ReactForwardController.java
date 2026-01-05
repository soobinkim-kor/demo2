package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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