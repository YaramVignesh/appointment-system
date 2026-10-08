package com.vignesh01.appointment_system.dto;

public class LoginResponse {

    private final String jwt;
    public LoginResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
