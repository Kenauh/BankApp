package com.example.bankapp.models;

import com.google.gson.annotations.SerializedName;


public class Contacto {

    @SerializedName("_id")
    private String id;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("numeroCuenta")
    private String numeroCuenta;

    @SerializedName("banco")
    private String banco;

    @SerializedName("tipo")
    private String tipo;

    // ── Getters ────────────────────────────────────────────────

    public String getId()           { return id; }
    public String getNombre()       { return nombre; }
    public String getNumeroCuenta() { return numeroCuenta; }
    public String getBanco()        { return banco != null ? banco : "BankApp"; }
    public String getTipo()         { return tipo  != null ? tipo  : "Débito"; }


    public String getIniciales() {
        if (nombre == null || nombre.isEmpty()) return "?";
        String[] partes = nombre.trim().split("\\s+");
        if (partes.length == 1) return String.valueOf(partes[0].charAt(0)).toUpperCase();
        return (String.valueOf(partes[0].charAt(0)) + String.valueOf(partes[1].charAt(0))).toUpperCase();
    }


    public String getUltimos() {
        if (numeroCuenta == null || numeroCuenta.length() < 4) return numeroCuenta;
        return numeroCuenta.substring(numeroCuenta.length() - 4);
    }
}
