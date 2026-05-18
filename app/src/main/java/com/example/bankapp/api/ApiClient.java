package com.example.bankapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    // IMPORTANTE:
    // 10.0.2.2 apunta al localhost de la PC
    // cuando se usa el emulador Android.

    private static final String BASE_URL = "http://10.0.2.2:5000/api/";

    private static Retrofit retrofit;

    public static Retrofit getClient() {

        if(retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}