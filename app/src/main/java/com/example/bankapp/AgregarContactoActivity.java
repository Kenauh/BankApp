package com.example.bankapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bankapp.api.ApiClient;
import com.example.bankapp.api.ApiService;
import com.example.bankapp.models.Contacto;
import com.example.bankapp.models.ContactoRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * AgregarContactoActivity.java
 *
 * Pantalla para agregar un nuevo contacto por numero de cuenta.
 * Campos: nombre (alias) y numero de cuenta de 10 digitos.
 * El backend valida que la cuenta exista antes de guardar.
 *
 * Al guardar exitosamente, hace finish() y TransferirActivity
 * recarga la lista con el nuevo contacto visible.
 */
public class AgregarContactoActivity extends AppCompatActivity {

    private EditText    etNombre;
    private EditText    etCuenta;
    private Button      btnGuardar;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contacto);

        etNombre    = findViewById(R.id.et_nombre_contacto);
        etCuenta    = findViewById(R.id.et_cuenta_contacto);
        btnGuardar  = findViewById(R.id.btn_guardar_contacto);
        progressBar = findViewById(R.id.progress_agregar);

        btnGuardar.setOnClickListener(v -> guardar());
        findViewById(R.id.btn_back_agregar).setOnClickListener(v -> finish());
    }

    private void guardar() {
        String nombre = etNombre.getText().toString().trim();
        String cuenta = etCuenta.getText().toString().trim();

        // Validaciones locales
        if (nombre.isEmpty()) {
            etNombre.setError("Ingresa un nombre o alias");
            etNombre.requestFocus();
            return;
        }
        if (cuenta.length() != 10 || !cuenta.matches("\\d{10}")) {
            etCuenta.setError("El numero de cuenta debe tener exactamente 10 digitos");
            etCuenta.requestFocus();
            return;
        }

        SessionManager session = new SessionManager(this);
        String userId = session.getUserId();
        String miCuenta = session.getAccount();

        if (userId == null) {
            Toast.makeText(this, "Sesion invalida", Toast.LENGTH_SHORT).show();
            return;
        }
        if (cuenta.equals(miCuenta)) {
            etCuenta.setError("No puedes agregarte a ti mismo como contacto");
            etCuenta.requestFocus();
            return;
        }

        setLoading(true);

        ContactoRequest request = new ContactoRequest(userId, nombre, cuenta, "BankApp", "Debito");

        ApiService api = ApiClient.getAuthClient(this).create(ApiService.class);
        api.guardarContacto(request).enqueue(new Callback<Contacto>() {
            @Override
            public void onResponse(Call<Contacto> call, Response<Contacto> response) {
                setLoading(false);
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(AgregarContactoActivity.this,
                            nombre + " agregado a tus contactos",
                            Toast.LENGTH_SHORT).show();
                    // Regresar a TransferirActivity; ella recargara la lista
                    finish();
                } else if (response.code() == 404) {
                    etCuenta.setError("Esa cuenta no existe en BankApp");
                    etCuenta.requestFocus();
                } else if (response.code() == 409) {
                    etCuenta.setError("Ya tienes este contacto guardado");
                    etCuenta.requestFocus();
                } else {
                    Toast.makeText(AgregarContactoActivity.this,
                            "Error al guardar (" + response.code() + ")",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contacto> call, Throwable t) {
                setLoading(false);
                Toast.makeText(AgregarContactoActivity.this,
                        "Error de conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setLoading(boolean loading) {
        btnGuardar.setEnabled(!loading);
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
    }
}
