package com.example.cap5;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RcMateria extends RecyclerView.Adapter<RcMateria.MyViewHolder> {
    Context context;
    ArrayList<Blocos> listaBlocos;
    RcMateria.OnItemClickListener listener;

    public RcMateria(Context context, ArrayList<Blocos> listaBlocos, OnItemClickListener listener) {
        this.context = context;
        this.listaBlocos = listaBlocos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate( R.layout.layoutblocos,parent,false ); //encher a lista
        return new RcMateria.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    Blocos bloco = listaBlocos.get(position);
    holder.titulo.setText(bloco.nome);
        String topicos = "";
    for(Blocos block : listaBlocos){
        for(String t : block.getTopicos()) {
            topicos += t + "\n";
        }
    }
    holder.topicos.setText(topicos);
    }

    @Override
    public int getItemCount() {
        return listaBlocos.size();
    }
    public interface OnItemClickListener {
        void onItemClick (Blocos bloco);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titulo,topicos;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.tituloDoBloco);
            topicos = itemView.findViewById(R.id.topicos);

        }
    }


}
