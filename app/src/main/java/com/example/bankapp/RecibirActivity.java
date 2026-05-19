package com.example.bankapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * RecibirActivity.java
 *
 * Pantalla "Recibir dinero".
 * Muestra el nombre del usuario y su numero de cuenta (10 digitos)
 * para que otra persona pueda hacer una transferencia.
 *
 * Funciones:
 *  - Mostrar numero de cuenta claramente.
 *  - Boton "Copiar" que copia el numero al portapapeles.
 *  - Instrucciones de como recibir dinero.
 */
public class RecibirActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recibir);

        SessionManager session = new SessionManager(this);

        String nombre  = session.getName();
        String cuenta  = session.getAccount();

        // Llenar vistas
        TextView tvNombre  = findViewById(R.id.tv_nombre_titular);
        TextView tvCuenta  = findViewById(R.id.tv_numero_cuenta);
        Button   btnCopiar = findViewById(R.id.btn_copiar_cuenta);

        tvNombre.setText(nombre != null ? nombre : "");
        tvCuenta.setText(cuenta  != null ? cuenta  : "");

        // Copiar numero de cuenta al portapapeles
        btnCopiar.setOnClickListener(v -> {
            if (cuenta == null) return;
            ClipboardManager clipboard =
                    (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("numeroCuenta", cuenta);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this,
                    "Numero de cuenta copiado al portapapeles",
                    Toast.LENGTH_SHORT).show();
        });

        // Regresar
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }
}
