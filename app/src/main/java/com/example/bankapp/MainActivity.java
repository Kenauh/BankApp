package com.example.bankapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SessionManager sessionManager = new SessionManager(this);
        String account = sessionManager.getAccount();
        if (account == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        TextView tvHola = findViewById(R.id.tv_hola);
        TextView tvSaldo = findViewById(R.id.tv_saldo_cuenta);
        TextView tvSaldoCredito = findViewById(R.id.tv_saldo_credito);
        TextView tvLimite = findViewById(R.id.tv_limite_disponible);

        tvHola.setText("Hola, " + sessionManager.getName());

        ApiService api = ApiClient.getRetrofit().create(ApiService.class);
        api.getSaldo(account).enqueue(new Callback<SaldoResponse>() {
            @Override
            public void onResponse(Call<SaldoResponse> call, Response<SaldoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SaldoResponse saldo = response.body();
                    tvSaldo.setText(String.format("$%.2f", saldo.currentBalance));
                    tvSaldoCredito.setText(String.format("$%.2f", saldo.creditBalance));
                    tvLimite.setText(String.format("Límite disponible: $%.2f", saldo.availableCredit));
                }
            }

            @Override
            public void onFailure(Call<SaldoResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "No se pudo cargar el saldo", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_cuenta).setOnClickListener(v ->
                startActivity(new Intent(this, CuentaDetailActivity.class)));
        findViewById(R.id.btn_transferir).setOnClickListener(v ->
                startActivity(new Intent(this, TransferirActivity.class)));
        findViewById(R.id.btn_recibir).setOnClickListener(v ->
                startActivity(new Intent(this, CuentaDetailActivity.class)));
    }
}
