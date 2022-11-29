package com.example.calendariocapivara2.Objetos;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BlocoDoDia {
    int ano,dia;
    String user;
    Blocos blocoDoDia;

    public BlocoDoDia() {
    }

    public BlocoDoDia(int ano, int dia, String user, Blocos blocoDoDia) {
        this.ano = ano;
        this.dia = dia;
        this.user = user;
        this.blocoDoDia = blocoDoDia;
    }

    public Blocos getBlocoDoDia() {
        return blocoDoDia;
    }

    public void setBlocoDoDia(Blocos blocoDoDia) {
        this.blocoDoDia = blocoDoDia;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }
    public void salvar_bd(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("DiaFoda").child(user).setValue(this);
    }
}
