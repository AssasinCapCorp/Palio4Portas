package com.example.cap5;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Blocos {
    ArrayList<String> topicos;
    String materia,nome;

    public Blocos() {
    }

    public Blocos(ArrayList<String> topicos, String materia, String nome) {
        this.topicos = topicos;
        this.materia = materia;
        this.nome = nome;
    }

    public ArrayList<String> getTopicos() {
        return topicos;
    }

    public void setTopicos(ArrayList<String> topicos) {
        this.topicos = topicos;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void salvar_bd(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Topicos").child(nome).setValue(this);
    }
}
