package com.example.demo.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

public class MainInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {
        HttpSession session = request.getSession(false);

        // 로그인 여부 체크 예시
        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           org.springframework.web.servlet.ModelAndView modelAndView) {

        // 필요 시 공통 데이터 추가
        if (modelAndView != null) {
            modelAndView.addObject("serverTime", System.currentTimeMillis());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {

        if (ex != null) {
            System.out.println("Interceptor Exception: " + ex.getMessage());
        }
    }
}
