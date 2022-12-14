package com.example.calendariocapivara2.Objetos;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Usuario2 {
    String nome;
    String senha;
    ArrayList<Blocos> blocosEstudados;
    int praticasFeitas;
    int diaInicio;
    int anoInicio;
    public Usuario2(){}
    public Usuario2(String nome, String senha, int diaInicio, int anoInicio) {
        this.nome = nome;
        this.senha = senha;
        this.diaInicio = diaInicio;
        this.anoInicio = anoInicio;
    }

    public ArrayList<Blocos> getBlocosEstudados() {
        return blocosEstudados;
    }

    public void setBlocosEstudados(ArrayList<Blocos> blocosEstudados) {
        this.blocosEstudados = blocosEstudados;
    }

    public int getDiaInicio() {
        return diaInicio;
    }

    public void setDiaInicio(int diaInicio) {
        this.diaInicio = diaInicio;
    }

    public int getAnoInicio() {
        return anoInicio;
    }

    public void setAnoInicio(int anoInicio) {
        this.anoInicio = anoInicio;
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




    public int getPraticasFeitas() {
        return praticasFeitas;
    }

    public void setPraticasFeitas(int praticasFeitas) {
        this.praticasFeitas = praticasFeitas;
    }


    public void salvar_bd(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Usuario").child(nome).setValue(this);
    }
}
