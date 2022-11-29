package com.example.calendariocapivara2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendariocapivara2.Objetos.Calendario;
import com.example.calendariocapivara2.R;

import java.util.ArrayList;

public class RcCalendario extends RecyclerView.Adapter<RcCalendario.MyViewHolder> {
    Context context;
    ArrayList<Calendario> listaCalendario;
    RcMateria.OnItemClickListener listener;

    public RcCalendario(Context context, ArrayList<Calendario> listaCalendario, RcMateria.OnItemClickListener listener) {
        this.context = context;
        this.listaCalendario = listaCalendario;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate( R.layout.layoutcalendario,parent,false ); //encher a lista
        return new RcCalendario.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Calendario calen = listaCalendario.get(position);
        holder.coisa.setText(calen.getCoisaParaFazer());
        holder.horario.setText(calen.getHorario());

    }

    @Override
    public int getItemCount() {
        return listaCalendario.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView coisa,horario;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            coisa = itemView.findViewById(R.id.coisa);
            horario = itemView.findViewById(R.id.horario);
        }
    }
}
