package com.example.calendariofodacapivaras;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Usuario {
    String nome;
    String senha;
    int id;

    public Usuario(String nome, String senha, int id) {
        this.nome = nome;
        this.senha = senha;
        this.id = id;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getSenha(){
        return senha;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
    public void salvar_bd(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Usuario").child(nome).setValue(this);
    }
}
