package com.example.bankapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bankapp.api.ApiClient;
import com.example.bankapp.api.ApiService;
import com.example.bankapp.models.SaldoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * CuentaDetailActivity.java
 * CORRECCION: usa getAuthClient(this) para evitar error 401.
 */
public class CuentaDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_detail);

        TextView tvSaldo       = findViewById(R.id.tv_saldo_disponible);
        TextView tvCajitas     = findViewById(R.id.tv_cajitas_saldo);
        TextView tvRendimiento = findViewById(R.id.tv_rendimiento);
        TextView tvMovMonto    = findViewById(R.id.tv_mov_monto);

        SessionManager session = new SessionManager(this);
        String account = session.getAccount();

        if (account == null) { finish(); return; }

        // Llamada con JWT
        ApiService api = ApiClient.getAuthClient(this).create(ApiService.class);

        api.getSaldo(account).enqueue(new Callback<SaldoResponse>() {
            @Override
            public void onResponse(Call<SaldoResponse> call, Response<SaldoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SaldoResponse s = response.body();
                    tvSaldo.setText(String.format("$%.2f", s.currentBalance));
                    tvCajitas.setText(String.format("$%.2f", s.currentBalance * 0.35));
                    tvRendimiento.setText(String.format("+$%.2f", s.currentBalance * 0.01));
                    tvMovMonto.setText(String.format("+$%.2f", s.currentBalance));
                }
            }

            @Override
            public void onFailure(Call<SaldoResponse> call, Throwable t) { }
        });

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
        findViewById(R.id.btn_transferir).setOnClickListener(v ->
                startActivity(new Intent(this, TransferirActivity.class)));
    }
}
