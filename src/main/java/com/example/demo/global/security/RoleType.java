package com.example.demo.global.security;

public enum RoleType {
    USER_ADMIN("10"),
    USER_DEFAULT("20");

    private final String roleCode;

    RoleType(String roleCode) {
        this.roleCode = roleCode;
    }

    public String roleCode() {
        return roleCode;
    }
}
