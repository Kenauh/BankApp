package com.example.bankapp.models;

public class TransferRequest {

    private String origen;
    private String destino;
    private double monto;

    public TransferRequest(String origen, String destino, double monto) {
        this.origen = origen;
        this.destino = destino;
        this.monto = monto;
    }
}