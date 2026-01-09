package com.example.demo.controller;

import com.example.demo.request.LoginRequest;
import com.example.demo.dto.UserSession;
import com.example.demo.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(LoginRequest request, HttpServletRequest httpRequest) {

        // 1. 사용자 인증
        UserSession userSession = authService.login(request);

        // 2. 세션 생성 (없으면 생성)
        HttpSession session = httpRequest.getSession(true);

        // 3. 세션에 로그인 정보 저장
        session.setAttribute("LOGIN_USER", userSession);

        // 4. 세션 유지 시간 (선택)
        session.setMaxInactiveInterval(30 * 60); // 30분

        return "redirect:/main";
    }
}
