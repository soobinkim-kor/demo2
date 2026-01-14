package com.example.demo.service;

import com.example.demo.dto.auth.TokenPair;
import com.example.demo.global.error.AuthErrorCode;
import com.example.demo.global.error.BusinessException;
import com.example.demo.security.jwt.JwtProvider;
import com.example.demo.request.user.LoginRequest;
import com.example.demo.request.user.SignInRequest;
import com.example.demo.dto.user.UserSession;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserBaseRepository;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserBaseRepository userBaseRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    public TokenPair login(LoginRequest request) {

        UserEntity user = userBaseRepository.findByUsrId(request.getUsrId())
                .orElseThrow(() -> new BusinessException(AuthErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getUsrPwd())) {
            throw new BusinessException(AuthErrorCode.USER_NOT_FOUND);
        }

        // 1️⃣ Access Token
        String accessToken = jwtProvider.createAccessToken(
                String.valueOf(user.getUsrNo()),
                user.getRole()
        );

        // 2️⃣ Refresh Token
        String refreshToken = jwtProvider.createRefreshToken(
                String.valueOf(user.getUsrNo())
        );

        // 3️⃣ Redis 저장
        refreshTokenService.save(refreshToken, user.getUsrNo());

        return new TokenPair(accessToken, refreshToken);
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
