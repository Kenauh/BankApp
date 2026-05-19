package com.example.bankapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bankapp.api.ApiClient;
import com.example.bankapp.api.ApiService;
import com.example.bankapp.models.LoginRequest;
import com.example.bankapp.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * LoginActivity.java
 *
 * CORRECCIONES:
 *  - Al entrar a esta Activity se limpia SIEMPRE la sesion en memoria,
 *    por lo que cada vez que el usuario abre la app parte desde cero.
 *  - Las llamadas autenticadas usan getAuthClient(ctx) para enviar el JWT.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText    etUsuario;
    private EditText    etPassword;
    private Button      btnLogin;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Borrar sesion al entrar: obliga al login cada vez que se abre la app
        new SessionManager(this).clearSession();

        setContentView(R.layout.activity_login);

        etUsuario   = findViewById(R.id.etUsuario);
        etPassword  = findViewById(R.id.etPassword);
        btnLogin    = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);

        btnLogin.setOnClickListener(v -> login());
    }

    private void login() {
        String cuenta   = etUsuario.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (cuenta.isEmpty()) {
            etUsuario.setError("Ingresa tu numero de cuenta");
            etUsuario.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            etPassword.setError("Ingresa tu contrasena");
            etPassword.requestFocus();
            return;
        }

        setLoading(true);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.login(new LoginRequest(cuenta, password))
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call,
                                           Response<LoginResponse> response) {
                        setLoading(false);

                        if (response.isSuccessful() && response.body() != null) {
                            LoginResponse body = response.body();

                            new SessionManager(LoginActivity.this).saveSession(
                                    body.getToken(),
                                    body.getUser().getId(),
                                    body.getUser().getNumeroCuenta(),
                                    body.getUser().getNombre()
                            );

                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();

                        } else {
                            Toast.makeText(LoginActivity.this,
                                    "Credenciales incorrectas",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        setLoading(false);
                        Toast.makeText(LoginActivity.this,
                                "No se pudo conectar al servidor",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void setLoading(boolean loading) {
        btnLogin.setEnabled(!loading);
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
    }
}
