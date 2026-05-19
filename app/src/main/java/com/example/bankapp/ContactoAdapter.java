package com.example.bankapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bankapp.models.Contacto;

import java.util.List;

/**
 * ContactoAdapter.java
 *
 * Adapter para el RecyclerView de contactos frecuentes.
 * Cada ítem muestra:
 *   – Avatar circular con iniciales (ej. "ML")
 *   – Nombre del contacto
 *   – Banco, tipo y últimos 4 dígitos de cuenta
 */
public class ContactoAdapter extends RecyclerView.Adapter<ContactoAdapter.ViewHolder> {

    /** Contrato que implementa la Activity para saber cuándo se toca un contacto. */
    public interface OnContactoClickListener {
        void onContactoClick(Contacto contacto);
    }

    private final List<Contacto>         lista;
    private final OnContactoClickListener listener;

    public ContactoAdapter(List<Contacto> lista, OnContactoClickListener listener) {
        this.lista    = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contacto, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contacto c = lista.get(position);

        // Avatar con iniciales calculadas en el modelo
        holder.tvIniciales.setText(c.getIniciales());

        holder.tvNombre.setText(c.getNombre());

        // "BankApp – Débito ····1234"
        holder.tvInfo.setText(c.getBanco() + " – " + c.getTipo() + " ····" + c.getUltimos());

        holder.itemView.setOnClickListener(v -> listener.onContactoClick(c));
    }

    @Override
    public int getItemCount() { return lista.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIniciales;
        TextView tvNombre;
        TextView tvInfo;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIniciales = itemView.findViewById(R.id.tv_iniciales);
            tvNombre    = itemView.findViewById(R.id.tv_nombre);
            tvInfo      = itemView.findViewById(R.id.tv_info);
        }
    }
}
