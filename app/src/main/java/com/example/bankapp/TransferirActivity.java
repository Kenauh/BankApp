package com.example.bankapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bankapp.api.ApiClient;
import com.example.bankapp.api.ApiService;
import com.example.bankapp.models.Contacto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * TransferirActivity.java
 * CORRECCION: usa getAuthClient(this) para evitar error 401.
 */
public class TransferirActivity extends AppCompatActivity
        implements ContactoAdapter.OnContactoClickListener {

    private final List<Contacto> listaCompleta = new ArrayList<>();
    private final List<Contacto> listaFiltrada = new ArrayList<>();
    private ContactoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferir);

        RecyclerView rv = findViewById(R.id.rv_contactos);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactoAdapter(listaFiltrada, this);
        rv.setAdapter(adapter);

        cargarContactos();

        EditText etBuscar = findViewById(R.id.et_buscar);
        etBuscar.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrar(s.toString());
            }
            public void afterTextChanged(Editable s) {}
        });

        findViewById(R.id.btn_cerrar).setOnClickListener(v -> finish());
    }

    private void cargarContactos() {
        SessionManager session = new SessionManager(this);
        String userId = session.getUserId();
        if (userId == null) { finish(); return; }

        // Llamada con JWT
        ApiService api = ApiClient.getAuthClient(this).create(ApiService.class);

        api.getContactos(userId).enqueue(new Callback<List<Contacto>>() {
            @Override
            public void onResponse(Call<List<Contacto>> call, Response<List<Contacto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaCompleta.clear();
                    listaCompleta.addAll(response.body());
                    listaFiltrada.clear();
                    listaFiltrada.addAll(listaCompleta);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Contacto>> call, Throwable t) {
                Toast.makeText(TransferirActivity.this,
                        "No se pudo cargar contactos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filtrar(String query) {
        listaFiltrada.clear();
        if (query.isEmpty()) {
            listaFiltrada.addAll(listaCompleta);
        } else {
            String q = query.toLowerCase();
            for (Contacto c : listaCompleta) {
                if (c.getNombre().toLowerCase().contains(q)) listaFiltrada.add(c);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onContactoClick(Contacto contacto) {
        Intent intent = new Intent(this, TransferMontoActivity.class);
        intent.putExtra("nombre",       contacto.getNombre());
        intent.putExtra("banco",        contacto.getBanco());
        intent.putExtra("tipo",         contacto.getTipo());
        intent.putExtra("ultimos",      contacto.getUltimos());
        intent.putExtra("numeroCuenta", contacto.getNumeroCuenta());
        startActivity(intent);
    }
}
