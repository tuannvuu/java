package com.example.demo.dto;

public class LoginRequest {

    private String username;
    private String password;

    // constructor rỗng cho Spring
    public LoginRequest() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
