package com.example.bankapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ContactoAdapter extends RecyclerView.Adapter<ContactoAdapter.ViewHolder> {

    public interface OnContactoClickListener {
        void onContactoClick(Contacto contacto);
    }

    private final List<Contacto> lista;
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
        holder.tvIniciales.setText(c.getIniciales());
        holder.tvNombre.setText(c.getNombre());
        holder.tvInfo.setText(c.getBanco() + " – " + c.getTipo() + " ····" + c.getUltimos());
        holder.itemView.setOnClickListener(v -> listener.onContactoClick(c));
    }

    @Override
    public int getItemCount() { return lista.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIniciales, tvNombre, tvInfo;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIniciales = itemView.findViewById(R.id.tv_iniciales);
            tvNombre    = itemView.findViewById(R.id.tv_nombre);
            tvInfo      = itemView.findViewById(R.id.tv_info);
        }
    }
}