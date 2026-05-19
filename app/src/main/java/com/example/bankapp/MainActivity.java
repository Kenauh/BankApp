package com.example.bankapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bankapp.api.ApiClient;
import com.example.bankapp.api.ApiService;
import com.example.bankapp.models.SaldoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MainActivity.java
 *
 * CORRECCIONES:
 *  - Usa getAuthClient(this) para enviar el JWT y no recibir error 401.
 *  - No verifica sesion persistente (no hay); si no hay sesion en memoria
 *    redirige a Login.
 *  - Boton "btn_recibir" abre RecibirActivity (nueva pantalla).
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SessionManager session = new SessionManager(this);
        if (!session.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        TextView tvHola         = findViewById(R.id.tv_hola);
        TextView tvSaldo        = findViewById(R.id.tv_saldo_cuenta);
        TextView tvSaldoCredito = findViewById(R.id.tv_saldo_credito);
        TextView tvLimite       = findViewById(R.id.tv_limite_disponible);

        tvHola.setText("Hola, " + session.getName());

        // ── Llamada autenticada con JWT ──────────────────────
        ApiService api = ApiClient.getAuthClient(this).create(ApiService.class);

        api.getSaldo(session.getAccount()).enqueue(new Callback<SaldoResponse>() {
            @Override
            public void onResponse(Call<SaldoResponse> call, Response<SaldoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SaldoResponse s = response.body();
                    tvSaldo.setText(String.format("$%.2f", s.currentBalance));
                    tvSaldoCredito.setText(String.format("$%.2f", s.creditBalance));
                    tvLimite.setText(String.format("Limite disponible: $%.2f", s.availableCredit));
                } else {
                    Toast.makeText(MainActivity.this,
                            "Error al cargar saldo (codigo " + response.code() + ")",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SaldoResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,
                        "Sin conexion: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // ── Navegacion ────────────────────────────────────────
        findViewById(R.id.btn_cuenta).setOnClickListener(v ->
                startActivity(new Intent(this, CuentaDetailActivity.class)));

        findViewById(R.id.btn_transferir).setOnClickListener(v ->
                startActivity(new Intent(this, TransferirActivity.class)));

        // "Recibir" ahora abre la pantalla de numero de cuenta
        findViewById(R.id.btn_recibir).setOnClickListener(v ->
                startActivity(new Intent(this, RecibirActivity.class)));
    }
}
