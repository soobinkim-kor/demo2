package com.example.demo.request.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SignInRequest {
    private String usrId;
    private String usrPwd;
}
