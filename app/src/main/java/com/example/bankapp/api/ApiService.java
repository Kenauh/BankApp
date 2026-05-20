package com.example.bankapp.api;

import com.example.bankapp.models.Contacto;
import com.example.bankapp.models.ContactoRequest;
import com.example.bankapp.models.LoginRequest;
import com.example.bankapp.models.LoginResponse;
import com.example.bankapp.models.SaldoResponse;
import com.example.bankapp.models.TransferRequest;
import com.example.bankapp.models.TransferResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ApiService {

    /** POST /api/login  →  autentica al usuario y devuelve el JWT */
    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest request);

    /** GET /api/saldo/{numeroCuenta}  →  saldo y crédito del usuario */
    @GET("saldo/{numeroCuenta}")
    Call<SaldoResponse> getSaldo(@Path("numeroCuenta") String numeroCuenta);

    /** POST /api/transferir  →  envía dinero entre cuentas */
    @POST("transferir")
    Call<TransferResponse> transferir(@Body TransferRequest request);

    /** GET /api/contactos/{usuarioId}  →  lista de contactos frecuentes */
    @GET("contactos/{usuarioId}")
    Call<List<Contacto>> getContactos(@Path("usuarioId") String usuarioId);

    /** POST /api/contactos  →  guarda un nuevo contacto */
    @POST("contactos")
    Call<Contacto> guardarContacto(@Body ContactoRequest request);
}
