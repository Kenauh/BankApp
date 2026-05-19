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
 * FIX: el saldo se carga en onResume() en lugar de onCreate().
 * onResume se ejecuta cada vez que esta pantalla queda en primer plano,
 * incluyendo cuando el usuario regresa desde TransferMontoActivity,
 * por lo que el saldo siempre muestra el valor actualizado.
 */
public class MainActivity extends AppCompatActivity {

    // Campos para acceder desde onResume sin volver a buscarlos
    private TextView tvSaldo;
    private TextView tvSaldoCredito;
    private TextView tvLimite;

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

        TextView tvHola = findViewById(R.id.tv_hola);
        tvSaldo         = findViewById(R.id.tv_saldo_cuenta);
        tvSaldoCredito  = findViewById(R.id.tv_saldo_credito);
        tvLimite        = findViewById(R.id.tv_limite_disponible);

        tvHola.setText("Hola, " + session.getName());

        // Navegacion
        findViewById(R.id.btn_cuenta).setOnClickListener(v ->
                startActivity(new Intent(this, CuentaDetailActivity.class)));

        findViewById(R.id.btn_transferir).setOnClickListener(v ->
                startActivity(new Intent(this, TransferirActivity.class)));

        findViewById(R.id.btn_recibir).setOnClickListener(v ->
                startActivity(new Intent(this, RecibirActivity.class)));
    }

    /**
     * onResume se llama al entrar Y al regresar de otra Activity.
     * Esto actualiza el saldo automaticamente despues de una transferencia.
     */
    @Override
    protected void onResume() {
        super.onResume();
        cargarSaldo();
    }

    private void cargarSaldo() {
        SessionManager session = new SessionManager(this);
        String account = session.getAccount();
        if (account == null || tvSaldo == null) return;

        ApiService api = ApiClient.getAuthClient(this).create(ApiService.class);
        api.getSaldo(account).enqueue(new Callback<SaldoResponse>() {
            @Override
            public void onResponse(Call<SaldoResponse> call, Response<SaldoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SaldoResponse s = response.body();
                    tvSaldo.setText(String.format("$%.2f", s.currentBalance));
                    tvSaldoCredito.setText(String.format("$%.2f", s.creditBalance));
                    tvLimite.setText(String.format("Limite disponible: $%.2f", s.availableCredit));
                } else {
                    Toast.makeText(MainActivity.this,
                            "Error al cargar saldo (" + response.code() + ")",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SaldoResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,
                        "Sin conexion al actualizar saldo", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
