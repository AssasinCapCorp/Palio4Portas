package com.example.calendariocapivara2.Objetos;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CalendarioSemanal {
    String nomedoUser;
    int segunda,terca,quarta,quinta,sexta,sabado,domingo;

    public CalendarioSemanal() {
    }

    public CalendarioSemanal(String nomedoUser, int segunda, int terca, int quarta, int quinta, int sexta, int sabado, int domingo) {
        this.nomedoUser = nomedoUser;
        this.segunda = segunda;
        this.terca = terca;
        this.quarta = quarta;
        this.quinta = quinta;
        this.sexta = sexta;
        this.sabado = sabado;
        this.domingo = domingo;
    }

    public String getNomedoUser() {
        return nomedoUser;
    }

    public void setNomedoUser(String nomedoUser) {
        this.nomedoUser = nomedoUser;
    }

    public int getSegunda() {
        return segunda;
    }

    public void setSegunda(int segunda) {
        this.segunda = segunda;
    }

    public int getTerca() {
        return terca;
    }

    public void setTerca(int terca) {
        this.terca = terca;
    }

    public int getQuarta() {
        return quarta;
    }

    public void setQuarta(int quarta) {
        this.quarta = quarta;
    }

    public int getQuinta() {
        return quinta;
    }

    public void setQuinta(int quinta) {
        this.quinta = quinta;
    }

    public int getSexta() {
        return sexta;
    }

    public void setSexta(int sexta) {
        this.sexta = sexta;
    }

    public int getSabado() {
        return sabado;
    }

    public void setSabado(int sabado) {
        this.sabado = sabado;
    }

    public int getDomingo() {
        return domingo;
    }

    public void setDomingo(int domingo) {
        this.domingo = domingo;
    }
    public void salvar_bd(){DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    ref.child("CalendarioSemanal").child(nomedoUser).setValue(this);
    }


}
