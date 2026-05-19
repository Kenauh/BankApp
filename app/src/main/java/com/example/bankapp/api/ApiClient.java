package com.example.bankapp.api;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.bankapp.SessionManager;

/**
 * ApiClient.java
 *
 * getClient()         -> sin token  (solo para /login)
 * getAuthClient(ctx)  -> envia "Authorization: Bearer <token>" en cada peticion
 *
 * CAMBIA BASE_URL segun tu entorno:
 *   Emulador local   ->  http://10.0.2.2:5000/api/
 *   Dispositivo fisico -> http://192.168.1.XX:5000/api/
 *   Nube (Render)    ->  https://tu-app.onrender.com/api/
 */
public class ApiClient {

    private static final String BASE_URL = "http://10.0.2.2:5000/api/";

    /** Sin token -- solo para el endpoint /login */
    public static Retrofit getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Con token JWT en Authorization.
     * Usar en MainActivity, CuentaDetailActivity, RecibirActivity,
     * TransferirActivity y TransferMontoActivity.
     */
    public static Retrofit getAuthClient(Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        String token = new SessionManager(context).getToken();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request req = chain.request().newBuilder()
                                .header("Authorization", "Bearer " + (token != null ? token : ""))
                                .build();
                        return chain.proceed(req);
                    }
                })
                .addInterceptor(logging)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /** Alias de compatibilidad -- sin token */
    public static Retrofit getRetrofit() {
        return getClient();
    }
}
