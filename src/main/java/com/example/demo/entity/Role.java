package com.example.demo.entity;

public enum Role {
    USER,
    ADMIN;

    // Helper method để lấy authorities cho Spring Security
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}