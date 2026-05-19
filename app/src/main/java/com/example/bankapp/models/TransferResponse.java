package com.example.bankapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Respuesta del POST /api/transferir.
 * { "success": true, "message": "Transferencia de $150.00 realizada con éxito",
 *   "nuevoSaldoOrigen": 4850.0 }
 */
public class TransferResponse {

    @SerializedName("success")
    public boolean success;

    @SerializedName("message")
    public String message;

    @SerializedName("nuevoSaldoOrigen")
    public double nuevoSaldoOrigen;
}
