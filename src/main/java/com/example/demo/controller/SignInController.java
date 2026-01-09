package com.example.demo.controller;

import com.example.demo.request.SignInRequest;
import com.example.demo.dto.UserSession;
import com.example.demo.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignInController {

    private final AuthService authService;

    @PostMapping("/login2")
    public String signIn(SignInRequest request,
                         HttpServletRequest httpRequest) {

        // 1️⃣ 인증
        UserSession userSession = authService.signIn(request);

        // 2️⃣ 세션 생성
        HttpSession session = httpRequest.getSession(true);
        session.setAttribute("LOGIN_USER", userSession);
        session.setMaxInactiveInterval(30 * 60); // 30분

        // 3️⃣ 이동
        return "redirect:/main";
    }

    @PostMapping("/logout2")
    public String logout2(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {


        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/login";
    }
}
