package com.example.bankapp;

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
    Call<SaldoResponse> getSaldo(@Path("id") String accountId);

    @GET("contactos/{id}")
    Call<List<ContactoResponse>> getContactos(@Path("id") String accountId);

    @POST("transferir")
    Call<TransferResponse> transferir(@Body TransferRequest request);
}
