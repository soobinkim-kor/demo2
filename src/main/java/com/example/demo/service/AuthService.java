package com.example.demo.service;

import com.example.demo.request.user.LoginRequest;
import com.example.demo.request.user.SignInRequest;
import com.example.demo.dto.user.UserSession;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserBaseRepository;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserBaseRepository userBaseRepository;
    private final PasswordEncoder passwordEncoder;

    public UserSession login(LoginRequest request) {

        UserEntity user = userBaseRepository.findByUsrId(request.getUsrId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자"));

        // ⚠️ 실제 서비스에서는 BCrypt 사용
        if (!passwordEncoder.matches(request.getPassword(), user.getUsrPwd())) {
            throw new IllegalArgumentException("비밀번호 불일치");
        }

        return new UserSession(
                user.getUsrNo(),
                user.getUsrId(),
                user.getUsrNm(),
                user.getRole()
        );
    }

    public UserSession signIn(SignInRequest request) {

        // 1️⃣ 사용자 조회
        UserEntity user = userBaseRepository.findByUsrId(request.getUsrId())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호 오류"));

        // 2️⃣ BCrypt 검증 (핵심)
        if (!passwordEncoder.matches(
                request.getUsrPwd(),
                user.getUsrPwd()
        )) {
            throw new IllegalArgumentException("아이디 또는 비밀번호 오류");
        }

        // 3️⃣ 세션용 DTO 생성
        return new UserSession(
                user.getUsrNo(),
                user.getUsrId(),
                user.getUsrNm(),
                user.getRole()
        );
    }
}
