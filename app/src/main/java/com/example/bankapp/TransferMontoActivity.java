package com.example.bankapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class TransferMontoActivity extends AppCompatActivity {

    private double monto = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_monto);

        String nombre  = getIntent().getStringExtra("nombre");
        String banco   = getIntent().getStringExtra("banco");
        String tipo    = getIntent().getStringExtra("tipo");
        String ultimos = getIntent().getStringExtra("ultimos");

        TextView tvNombre  = findViewById(R.id.tv_destinatario);
        TextView tvSubInfo = findViewById(R.id.tv_sub_info);

        tvNombre.setText("Transferir a\n" + nombre);
        tvSubInfo.setText(banco + " · " + tipo + " ····" + ultimos);

        EditText etMonto     = findViewById(R.id.et_monto);
        Button   btnContinuar = findViewById(R.id.btn_continuar);

        btnContinuar.setEnabled(false);
        btnContinuar.setAlpha(0.4f);

        etMonto.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    monto = Double.parseDouble(s.toString());
                    boolean valido = monto > 0;
                    btnContinuar.setEnabled(valido);
                    btnContinuar.setAlpha(valido ? 1f : 0.4f);
                } catch (NumberFormatException e) {
                    btnContinuar.setEnabled(false);
                    btnContinuar.setAlpha(0.4f);
                }
            }
            public void afterTextChanged(Editable s) {}
        });

        btnContinuar.setOnClickListener(v -> {
            Toast.makeText(this,
                String.format("Transferencia de $%.2f a %s confirmada", monto, nombre),
                Toast.LENGTH_LONG).show();
            finish();
        });

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }
}