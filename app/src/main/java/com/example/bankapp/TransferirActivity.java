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

public class TransferirActivity extends AppCompatActivity
        implements ContactoAdapter.OnContactoClickListener {

    private final List<Contacto> listaCompleta = new ArrayList<>();
    private final List<Contacto> listaFiltrada = new ArrayList<>();
    private ContactoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferir);

        cargarContactos();

        RecyclerView rv = findViewById(R.id.rv_contactos);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactoAdapter(listaFiltrada, this);
        rv.setAdapter(adapter);

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
        listaCompleta.add(new Contacto("jhon",   "Mercado Pago W", "CLABE",  "9328"));
        listaCompleta.add(new Contacto("Mamá",   "AZTECA",         "CLABE",  "8908"));
        listaCompleta.add(new Contacto("mari",   "BBVA MEXICO",    "Débito", "9611"));
        listaCompleta.add(new Contacto("oscar",  "SANTANDER",      "Débito", "6603"));
        listaCompleta.add(new Contacto("Poncho", "NU MEXICO",      "CLABE",  "2200"));
        listaCompleta.add(new Contacto("R",      "NU MEXICO",      "CLABE",  "3988"));
        listaFiltrada.addAll(listaCompleta);
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
        intent.putExtra("nombre",  contacto.getNombre());
        intent.putExtra("banco",   contacto.getBanco());
        intent.putExtra("tipo",    contacto.getTipo());
        intent.putExtra("ultimos", contacto.getUltimos());
        startActivity(intent);
    }
}