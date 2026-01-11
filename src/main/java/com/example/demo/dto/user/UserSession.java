package com.example.demo.dto.user;

import java.io.Serializable;

public record UserSession(Long userNo, String userId, String userName, String role) implements Serializable {

}
