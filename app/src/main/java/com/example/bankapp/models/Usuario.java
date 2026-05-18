package com.example.bankapp.models;

public class Usuario {

    private String _id;
    private String nombre;
    private String numeroCuenta;
    private double saldo;

    public String getId() {
        return _id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }
}