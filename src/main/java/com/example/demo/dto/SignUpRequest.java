package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpRequest {

    private String usrId;
    private String usrNm;
    private String password;

}
