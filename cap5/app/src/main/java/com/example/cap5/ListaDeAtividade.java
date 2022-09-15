package com.example.cap5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListaDeAtividade extends AppCompatActivity {
    ImageView imagem;
    TextView nomeDaMateria;
    static ArrayList<Blocos> listBlocos;
    RecyclerView rv;
    RcMateria adapter;
    static String materia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_lista_de_atividade);
        imagem = findViewById(R.id.fundinho);
        nomeDaMateria = findViewById(R.id.nomeMateria);
        nomeDaMateria.setText(materia);
        rv = findViewById(R.id.rc);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RcMateria(this, listBlocos, new RcMateria.OnItemClickListener() {
            @Override
            public void onItemClick(Blocos bloco) {

            }
        });
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        imagem.setImageResource(R.drawable.jotarolendo);
    }
}