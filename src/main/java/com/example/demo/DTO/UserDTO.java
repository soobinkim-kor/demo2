package com.example.demo.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTO {
    private Long usrNo;
    private String usrId;
}
