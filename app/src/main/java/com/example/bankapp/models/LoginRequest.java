// ── LoginRequest.java ─────────────────────────────────────────────────────────
package com.example.bankapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Cuerpo del POST /api/login.
 * Los campos coinciden con lo que espera el backend (server.js).
 */
public class LoginRequest {

    @SerializedName("numeroCuenta")
    private final String numeroCuenta;

    @SerializedName("password")
    private final String password;

    public LoginRequest(String numeroCuenta, String password) {
        this.numeroCuenta = numeroCuenta;
        this.password     = password;
    }
}
