package com.example.demo.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String usrId;
    private String password;
}
