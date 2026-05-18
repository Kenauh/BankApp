package com.example.bankapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CuentaDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_detail);

        TextView tvSaldo       = findViewById(R.id.tv_saldo_disponible);
        TextView tvCajitas     = findViewById(R.id.tv_cajitas_saldo);
        TextView tvRendimiento = findViewById(R.id.tv_rendimiento);
        TextView tvMovMonto    = findViewById(R.id.tv_mov_monto);

        tvSaldo.setText("$0.00");
        tvCajitas.setText("$1,054.81");
        tvRendimiento.setText("+$10.96");
        tvMovMonto.setText("+$311.16");

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
        findViewById(R.id.btn_transferir).setOnClickListener(v ->
                startActivity(new Intent(this, TransferirActivity.class)));
    }
}