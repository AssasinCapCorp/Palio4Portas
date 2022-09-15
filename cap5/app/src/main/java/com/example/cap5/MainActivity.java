package com.example.cap5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        String[] topi = {"indo Arábico","xexenia avançada","a lingua do 11 de setembro"};
//        ArrayList<String> topics = new ArrayList<String >(Arrays.asList(topi));
//        Blocos block = new Blocos(topics,"linguagens","basicos da linguagem");
//        block.salvar_bd();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Topicos");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<Blocos> listaBlocos = new ArrayList<>();
              for(DataSnapshot d: snapshot.getChildren()){
                  Blocos bo = d.getValue(Blocos.class);
                  ArrayList<String> listtopics = new ArrayList<>();
                  for(String i: bo.getTopicos()){
                      listtopics.add(i);
                  }
                  Blocos blocos = new Blocos(listtopics,bo.materia,bo.getNome());
                  listaBlocos.add(blocos);
                  //listtopics.clear();
              }
                ListaDeAtividade.listBlocos = listaBlocos;
                ListaDeAtividade.materia = listaBlocos.get(0).materia;
                passarTela();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}});
    }
    public void passarTela(){
        Intent i = new Intent(this,ListaDeAtividade.class);
        startActivity(i);
    }
}