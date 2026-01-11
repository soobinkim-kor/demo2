package com.example.demo.controller;

import com.example.demo.request.user.SignUpRequest;
import com.example.demo.request.user.SignInRequest;
import com.example.demo.dto.user.UserDTO;
import com.example.demo.dto.user.UserSession;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
import com.example.demo.service.UserSignUpService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserSignUpService userSignUpService;
    private final AuthService authService;

    @GetMapping(value = "/test")
    public ResponseEntity<String> controllerTest(@RequestBody UserEntity userParam){
        UserEntity user = UserEntity.builder().usrNm("Soobin").build();
        userService.saveUser(user);
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }

    @GetMapping(value = "/api/users/getUser/{usrNo}")
    public ResponseEntity<Optional<UserEntity>> getUser(@PathVariable Long usrNo){
        UserDTO userDTO = UserDTO.builder().usrNo(usrNo).build();

        return new ResponseEntity<>(userService.getUserByUsrNo(userDTO), HttpStatus.OK);
    }

    @PostMapping(value = "/createUser")
    public ResponseEntity<UserEntity> saveUser(@RequestBody UserDTO userParam){
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsrId(UUID.randomUUID().toString());
        signUpRequest.setUsrNm("김수빈");
        signUpRequest.setPassword("1234");
        signUpRequest.setUsrEmail("shb03207@naver.com");
        return new ResponseEntity<>(userSignUpService.signUp(signUpRequest), HttpStatus.OK);
    }

    @GetMapping(value = "/createUser")
    public ResponseEntity<UserEntity> saveUserTest(){
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsrId(UUID.randomUUID().toString());
        signUpRequest.setUsrNm("김수빈");
        signUpRequest.setPassword("1234");
        signUpRequest.setUsrEmail("shb03207@naver.com");
        return new ResponseEntity<>(userSignUpService.signUp(signUpRequest), HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<UserSession> signIn(@RequestBody SignInRequest request,
                         HttpServletRequest httpRequest) {
        // 1️⃣ 인증
        UserSession userSession = authService.signIn(request);

        // 2️⃣ 세션 생성
        HttpSession session = httpRequest.getSession(true);
        session.setAttribute("LOGIN_USER", userSession);
        session.setMaxInactiveInterval(30 * 60); // 30분

        // 3️⃣ 이동
        return new ResponseEntity<>(userSession, HttpStatus.OK);
    }
}
