package com.example.bankapp.models;

public class LoginRequest {

    private String numeroCuenta;
    private String password;

    public LoginRequest(String numeroCuenta, String password) {
        this.numeroCuenta = numeroCuenta;
        this.password = password;
    }
}