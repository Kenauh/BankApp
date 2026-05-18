package com.example.bankapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CuentaDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_detail);

        TextView tvSaldo = findViewById(R.id.tv_saldo_disponible);
        TextView tvCajitas = findViewById(R.id.tv_cajitas_saldo);
        TextView tvRendimiento = findViewById(R.id.tv_rendimiento);
        TextView tvMovMonto = findViewById(R.id.tv_mov_monto);

        SessionManager sessionManager = new SessionManager(this);
        String account = sessionManager.getAccount();

        ApiService api = ApiClient.getRetrofit().create(ApiService.class);
        if (account != null) {
            api.getSaldo(account).enqueue(new Callback<SaldoResponse>() {
                @Override
                public void onResponse(Call<SaldoResponse> call, Response<SaldoResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        SaldoResponse saldo = response.body();
                        tvSaldo.setText(String.format("$%.2f", saldo.currentBalance));
                        tvCajitas.setText(String.format("$%.2f", saldo.currentBalance * 0.35));
                        tvRendimiento.setText(String.format("+$%.2f", saldo.currentBalance * 0.01));
                        tvMovMonto.setText(String.format("+$%.2f", saldo.currentBalance));
                    }
                }

                @Override
                public void onFailure(Call<SaldoResponse> call, Throwable t) { }
            });
        }

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
        findViewById(R.id.btn_transferir).setOnClickListener(v ->
                startActivity(new Intent(this, TransferirActivity.class)));
    }
}
