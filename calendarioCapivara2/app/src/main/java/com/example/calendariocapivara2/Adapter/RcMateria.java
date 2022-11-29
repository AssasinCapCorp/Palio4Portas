package com.example.calendariocapivara2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendariocapivara2.Objetos.BlocosEstudados;
import com.example.calendariocapivara2.Utilitis.Constants;
import com.example.calendariocapivara2.Utilitis.Muleta;
import com.example.calendariocapivara2.Objetos.Blocos;
import com.example.calendariocapivara2.R;
import com.example.calendariocapivara2.Utilitis.PreferenceManager;

import java.util.ArrayList;

public class RcMateria extends RecyclerView.Adapter<RcMateria.MyViewHolder> {
    Context context;
    ArrayList<Blocos> listaBlocos;
    ArrayList<String> listaDeBlocosEstudados;
    RcMateria.OnItemClickListener listener;
    PreferenceManager preferenceManager;


    public RcMateria(Context context, ArrayList<Blocos> listaBlocos, ArrayList<String> listaDeBlocosEstudados, OnItemClickListener listener) {
        this.context = context;
        this.listaBlocos = listaBlocos;
        this.listaDeBlocosEstudados = listaDeBlocosEstudados;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layoutblocos, parent, false); //encher a lista
        return new RcMateria.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        preferenceManager = new PreferenceManager(context);
        Blocos bloco = listaBlocos.get(position);
        holder.titulo.setText(bloco.nome);
        boolean estudado = false;
        String topicos = "";
        try {
            if (listaDeBlocosEstudados.isEmpty()) {
                for (String t : bloco.getTopicos()) {
                    topicos += t + "\n";
                }
                holder.topicos.setText(topicos);
            } else {
                for (String b : listaDeBlocosEstudados) {
                    if (b.equals(bloco.getNome())) {estudado = true;}
                }
                if (estudado) {
                    holder.topicos.setText("ESTUDADO");
                } else {
                    for (String t : bloco.getTopicos()) {
                        topicos += t + "\n";
                    }
                    holder.topicos.setText(topicos);
                }
            }
        } catch (Exception e) {
            Toast.makeText(context, "Deu ruim " + bloco.getNome(), Toast.LENGTH_LONG).show();
            for (String t : bloco.getTopicos()) {
                topicos += t + "\n";
            }
            holder.topicos.setText(topicos);
        }

        holder.itemView.setOnClickListener(v -> {
            if (!holder.topicos.getText().toString().equals("ESTUDADO")) {
                holder.topicos.setText("ESTUDADO");
                listaDeBlocosEstudados.add(bloco.getNome());
            } else {
                String to = "";
                for (String t : bloco.getTopicos()) {
                    to += t + "\n";
                }
                holder.topicos.setText(to);
                listaDeBlocosEstudados.remove(bloco.getNome());

//                int i = 0;
//                for (Blocos fdp : listaDeBlocosEstudados) { //jeito que fiz para remover blocos
//                    if (fdp.getNome().equals(bloco.getNome())) {
//                        break;
//                    }
//
//                    i++;
//                }
//                listaDeBlocosEstudados.set(i, null);
//                ArrayList<Blocos> precaussao = new ArrayList<>();
//                for (Blocos puto : listaDeBlocosEstudados) {
//                    try {
//                        if (!puto.getNome().equals("rogerio SENE O MELHOR INSANO DE TODO MUNDO")) {
//                            precaussao.add(puto);
//                        }
//                    } catch (NullPointerException e) {
//                    }
//
//                }
//                listaDeBlocosEstudados = (precaussao);
            }
            String t = "";
            for(String i :listaDeBlocosEstudados){
                if(i.equals(listaDeBlocosEstudados.get(0))){t=i;}else{t+=";"+i;}
            }
            preferenceManager.putString(Constants.KEY_TOPICOS_ESTUDADOS,t);
        });
    }

    @Override
    public int getItemCount() {
        return listaBlocos.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Blocos bloco);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, topicos;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.tituloDoBloco);
            topicos = itemView.findViewById(R.id.topicos);

        }
    }


}
