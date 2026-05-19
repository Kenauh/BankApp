package com.example.bankapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Cuerpo del POST /api/transferir.
 * { "origen": "1234567890", "destino": "0987654321", "monto": 150.0 }
 */
public class TransferRequest {

    @SerializedName("origen")
    private final String origen;

    @SerializedName("destino")
    private final String destino;

    @SerializedName("monto")
    private final double monto;

    public TransferRequest(String origen, String destino, double monto) {
        this.origen  = origen;
        this.destino = destino;
        this.monto   = monto;
    }
}
