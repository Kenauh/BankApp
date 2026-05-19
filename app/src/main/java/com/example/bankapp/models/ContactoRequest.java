package com.example.bankapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * ContactoRequest.java
 * Cuerpo del POST /api/contactos para crear un contacto nuevo.
 */
public class ContactoRequest {

    @SerializedName("usuarioId")
    private final String usuarioId;

    @SerializedName("nombre")
    private final String nombre;

    @SerializedName("numeroCuenta")
    private final String numeroCuenta;

    @SerializedName("banco")
    private final String banco;

    @SerializedName("tipo")
    private final String tipo;

    public ContactoRequest(String usuarioId, String nombre,
                           String numeroCuenta, String banco, String tipo) {
        this.usuarioId    = usuarioId;
        this.nombre       = nombre;
        this.numeroCuenta = numeroCuenta;
        this.banco        = banco;
        this.tipo         = tipo;
    }
}
