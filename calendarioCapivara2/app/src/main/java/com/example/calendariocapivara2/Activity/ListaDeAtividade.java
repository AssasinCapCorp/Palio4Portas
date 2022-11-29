package com.example.calendariocapivara2.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.calendariocapivara2.Adapter.RcMateria;
import com.example.calendariocapivara2.Objetos.BlocosEstudados;
import com.example.calendariocapivara2.Utilitis.Constants;
import com.example.calendariocapivara2.Utilitis.Muleta;
import com.example.calendariocapivara2.Objetos.Blocos;
import com.example.calendariocapivara2.R;
import com.example.calendariocapivara2.Utilitis.PreferenceManager;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;

public class ListaDeAtividade extends AppCompatActivity {

    ImageView imagem;
    TextView nomeDaMateria;
    public static ArrayList<Blocos> listBlocos;
    RecyclerView rv;
    RcMateria adapter;
    public static String materia;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_lista_de_atividade);
        imagem = findViewById(R.id.fundinho);
        nomeDaMateria = findViewById(R.id.nomeMateria);
        nomeDaMateria.setText(materia);
        preferenceManager = new PreferenceManager(this);

        switch (materia) {
            case "linguagens":
                imagem.setImageResource(R.drawable.jotarolendo);
                break;
            case "Natureza":
                imagem.setImageResource(R.drawable.alquimiacaralho);
                break;
            case "Matematica":
                imagem.setImageResource(R.drawable.matematica);
                break;
            case "Humanas":
                imagem.setImageResource(R.drawable.humanas_rum_igual_a_death_little_corno);
                break;
        }
        rv = findViewById(R.id.rc);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        try {
            String[] coisas = preferenceManager.getString(Constants.KEY_TOPICOS_ESTUDADOS,null).split(";",99);
            ArrayList<String> co = new ArrayList<>(Arrays.asList(coisas));
            adapter = new RcMateria(this, listBlocos,co, new RcMateria.OnItemClickListener() {
                @Override
                public void onItemClick(Blocos bloco) {

                }
            });
        }catch (NullPointerException e){
            adapter = new RcMateria(this, listBlocos,new ArrayList<>(), new RcMateria.OnItemClickListener() {
                @Override
                public void onItemClick(Blocos bloco) {

                }
            });
        }
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onStop () {
        super.onStop();
    }
}