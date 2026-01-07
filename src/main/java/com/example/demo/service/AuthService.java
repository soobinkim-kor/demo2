package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.UserSession;
import com.example.demo.entity.UserBase;
import com.example.demo.repository.UserBaseRepository;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserBaseRepository userBaseRepository;

    public AuthService(UserBaseRepository userBaseRepository) {
        this.userBaseRepository = userBaseRepository;
    }

    public UserSession login(LoginRequest request) {

        UserBase user = userBaseRepository.findByUsrId(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자"));

        // ⚠️ 실제 서비스에서는 BCrypt 사용
        if (!user.getUsrPwd().equals(request.getPassword())) {
            throw new IllegalArgumentException("비밀번호 불일치");
        }

        return new UserSession(
                user.getUsrId(),
                user.getUsrNm(),
                user.getRole()
        );
    }
}
