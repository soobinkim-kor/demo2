package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTO {
    private Long usrNo;
    private String usrId;
}
