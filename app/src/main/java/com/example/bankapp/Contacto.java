package com.example.bankapp;

public class Contacto {
    private final String nombre, banco, tipo, ultimos;


    public Contacto(String nombre, String banco, String tipo, String ultimos) {
        this.nombre  = nombre;
        this.banco   = banco;
        this.tipo    = tipo;
        this.ultimos = ultimos;
    }

    public String getNombre()  { return nombre; }
    public String getBanco()   { return banco; }
    public String getTipo()    { return tipo; }
    public String getUltimos() { return ultimos; }

    public String getIniciales() {
        return nombre.isEmpty() ? "?" : String.valueOf(nombre.charAt(0)).toUpperCase();
    }
}