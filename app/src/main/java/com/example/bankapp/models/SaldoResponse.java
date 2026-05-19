package com.example.bankapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Respuesta del GET /api/saldo/{numeroCuenta}.
 * {
 *   "currentBalance"  : 5000.00,
 *   "creditBalance"   : 3200.50,
 *   "availableCredit" : 6799.50
 * }
 */
public class SaldoResponse {

    @SerializedName("currentBalance")
    public double currentBalance;

    @SerializedName("creditBalance")
    public double creditBalance;

    @SerializedName("availableCredit")
    public double availableCredit;
}
