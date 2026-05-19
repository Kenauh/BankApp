package com.example.bankapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Respuesta del POST /api/login.
 * { "token": "...", "user": { "_id": "...", "nombre": "...", "numeroCuenta": "...", "saldo": 0.0 } }
 */
public class LoginResponse {

    @SerializedName("token")
    private String token;

    @SerializedName("user")
    private Usuario user;

    public String getToken()  { return token; }
    public Usuario getUser()  { return user; }
}
