package com.example.bankapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Contacto.java
 *
 * Modelo de contacto frecuente.
 * Campos devueltos por GET /api/contactos/{id}:
 * {
 *   "_id"          : "mongo-id",
 *   "nombre"       : "María López",
 *   "numeroCuenta" : "0987654321",
 *   "banco"        : "BankApp",
 *   "tipo"         : "Débito"
 * }
 *
 * Los campos "iniciales" y "ultimos" se calculan en el Adapter
 * a partir de nombre y numeroCuenta respectivamente.
 */
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

    /**
     * Devuelve las iniciales del nombre para el avatar circular.
     * "María López" → "ML"
     */
    public String getIniciales() {
        if (nombre == null || nombre.isEmpty()) return "?";
        String[] partes = nombre.trim().split("\\s+");
        if (partes.length == 1) return String.valueOf(partes[0].charAt(0)).toUpperCase();
        return (String.valueOf(partes[0].charAt(0)) + String.valueOf(partes[1].charAt(0))).toUpperCase();
    }

    /**
     * Devuelve los últimos 4 dígitos de la cuenta para mostrar "····1234".
     */
    public String getUltimos() {
        if (numeroCuenta == null || numeroCuenta.length() < 4) return numeroCuenta;
        return numeroCuenta.substring(numeroCuenta.length() - 4);
    }
}
