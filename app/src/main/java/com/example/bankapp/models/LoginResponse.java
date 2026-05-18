package com.example.bankapp.models;

public class LoginResponse {

    private String token;
    private Usuario user;

    public String getToken() {
        return token;
    }

    public Usuario getUser() {
        return user;
    }
}