package com.example.bankapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        cargarContactosDesdeApi();

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

    private void cargarContactosDesdeApi() {
        SessionManager sessionManager = new SessionManager(this);
        String account = sessionManager.getAccount();
        if (account == null) return;

        ApiService api = ApiClient.getRetrofit().create(ApiService.class);
        api.getContactos(account).enqueue(new Callback<List<ContactoResponse>>() {
            @Override
            public void onResponse(Call<List<ContactoResponse>> call, Response<List<ContactoResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaCompleta.clear();
                    for (ContactoResponse c : response.body()) {
                        String ultimos = c.destinationAccount.length() >= 4
                                ? c.destinationAccount.substring(c.destinationAccount.length() - 4)
                                : c.destinationAccount;
                        listaCompleta.add(new Contacto(c.name, c.bank, c.type, ultimos));
                    }
                    listaFiltrada.clear();
                    listaFiltrada.addAll(listaCompleta);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<ContactoResponse>> call, Throwable t) { }
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
        intent.putExtra("nombre", contacto.getNombre());
        intent.putExtra("banco", contacto.getBanco());
        intent.putExtra("tipo", contacto.getTipo());
        intent.putExtra("ultimos", contacto.getUltimos());
        startActivity(intent);
    }
}
