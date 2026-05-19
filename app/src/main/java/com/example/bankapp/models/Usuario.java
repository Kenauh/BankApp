package com.example.bankapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Objeto anidado dentro de LoginResponse.
 * Refleja los campos que el backend devuelve en el campo "user".
 */
public class Usuario {

    @SerializedName("_id")
    private String id;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("numeroCuenta")
    private String numeroCuenta;

    @SerializedName("saldo")
    private double saldo;

    public String getId()           { return id; }
    public String getNombre()       { return nombre; }
    public String getNumeroCuenta() { return numeroCuenta; }
    public double getSaldo()        { return saldo; }
}
