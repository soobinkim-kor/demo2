package com.example.demo.dto.user;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserSession implements Serializable {

    private final String userId;
    private final String userName;
    private final String role;

    public UserSession(String userId, String userName, String role) {
        this.userId = userId;
        this.userName = userName;
        this.role = role;
    }
}
