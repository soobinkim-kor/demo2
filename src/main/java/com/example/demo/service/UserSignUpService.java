package com.example.demo.service;

import com.example.demo.request.user.SignUpRequest;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.user.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserSignUpService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserSignUpService(UserRepository userRepository,
                             PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserEntity signUp(SignUpRequest request) {

        // 1️⃣ 아이디 중복 체크
        if (userRepository.existsByUsrId(request.getUsrId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디");
        }

        // 2️⃣ 비밀번호 BCrypt 암호화
        String encodedPwd = passwordEncoder.encode(request.getPassword());

        // 3️⃣ 엔티티 생성
        UserEntity user =
            UserEntity.builder()
                    .usrId(request.getUsrId())
                    .usrNm(request.getUsrNm())
                    .usrPwd(encodedPwd)
                    .usrEmail(request.getUsrEmail())
                    .role("ADMIN")
                    .build();

        // 4️⃣ 저장
        return userRepository.save(user);
    }
}
