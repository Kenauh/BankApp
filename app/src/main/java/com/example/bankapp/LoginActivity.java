package com.example.bankapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SessionManager sessionManager = new SessionManager(this);
        if (sessionManager.getAccount() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        EditText etCuenta = findViewById(R.id.et_cuenta_usuario);
        EditText etPassword = findViewById(R.id.et_password);
        Button btnIngresar = findViewById(R.id.btn_ingresar);

        btnIngresar.setOnClickListener(v -> {
            String account = etCuenta.getText().toString().trim();
            String password = etPassword.getText().toString();

            if (account.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Ingresa cuenta y contraseña", Toast.LENGTH_SHORT).show();
                return;
            }

            ApiService api = ApiClient.getRetrofit().create(ApiService.class);
            api.login(new LoginRequest(account, password)).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (!response.isSuccessful() || response.body() == null || !response.body().success) {
                        Toast.makeText(LoginActivity.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    LoginResponse data = response.body();
                    sessionManager.saveLogin(data.user.accountNumber, data.user.name);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "No se pudo conectar al backend", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
