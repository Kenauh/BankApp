package com.example.bankapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String USUARIO = "Jonathan";
    private static final double SALDO_CUENTA = 311.16;
    private static final double SALDO_CREDITO = 681.06;
    private static final double LIMITE_DISPONIBLE = 218.94;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvHola          = findViewById(R.id.tv_hola);
        TextView tvSaldo         = findViewById(R.id.tv_saldo_cuenta);
        TextView tvSaldoCredito  = findViewById(R.id.tv_saldo_credito);
        TextView tvLimite        = findViewById(R.id.tv_limite_disponible);

        tvHola.setText("Hola, " + USUARIO);
        tvSaldo.setText(String.format("$%.2f", SALDO_CUENTA));
        tvSaldoCredito.setText(String.format("$%.2f", SALDO_CREDITO));
        tvLimite.setText(String.format("Límite disponible: $%.2f", LIMITE_DISPONIBLE));

        findViewById(R.id.btn_cuenta).setOnClickListener(v ->
                startActivity(new Intent(this, CuentaDetailActivity.class)));
        findViewById(R.id.btn_transferir).setOnClickListener(v ->
                startActivity(new Intent(this, TransferirActivity.class)));
        findViewById(R.id.btn_recibir).setOnClickListener(v ->
                startActivity(new Intent(this, CuentaDetailActivity.class)));
    }
}