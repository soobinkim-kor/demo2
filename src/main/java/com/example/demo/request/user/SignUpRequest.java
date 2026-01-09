package com.example.demo.request.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequest {

    private String usrId;
    private String usrNm;
    private String password;
    private String usrEmail;
}
