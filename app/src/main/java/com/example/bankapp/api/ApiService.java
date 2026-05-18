package com.example.bankapp.api;

import com.example.bankapp.models.Contacto;
import com.example.bankapp.models.LoginRequest;
import com.example.bankapp.models.LoginResponse;
import com.example.bankapp.models.SaldoResponse;
import com.example.bankapp.models.TransferRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @GET("saldo/{id}")
    Call<SaldoResponse> obtenerSaldo(@Path("id") String id);

    @POST("transferir")
    Call<Void> transferir(@Body TransferRequest request);

    @GET("contactos/{id}")
    Call<List<Contacto>> obtenerContactos(@Path("id") String id);

    @POST("contactos")
    Call<Void> guardarContacto(@Body Contacto contacto);
}