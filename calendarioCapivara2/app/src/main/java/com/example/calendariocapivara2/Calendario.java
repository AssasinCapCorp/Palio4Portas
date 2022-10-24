package com.example.calendariocapivara2;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Calendario {
    String nomedDoVagabundo;
    int diaDoAno,ano;
    String horario,mes;
    String coisaParaFazer;


    public Calendario() {
    }

    public Calendario(String nomedDoVagabundo, int diaDoAno, int ano, String horario, String mes, String coisaParaFazer) {
        this.nomedDoVagabundo = nomedDoVagabundo;
        this.diaDoAno = diaDoAno;
        this.ano = ano;
        this.horario = horario;
        this.mes = mes;
        this.coisaParaFazer = coisaParaFazer;
    }

    public String getNomedDoVagabundo() {
        return nomedDoVagabundo;
    }

    public void setNomedDoVagabundo(String nomedDoVagabundo) {
        this.nomedDoVagabundo = nomedDoVagabundo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getDiaDoAno() {
        return diaDoAno;
    }

    public void setDiaDoAno(int diaDoAno) {
        this.diaDoAno = diaDoAno;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getCoisaParaFazer() {
        return coisaParaFazer;
    }

    public void setCoisaParaFazer(String coisaParaFazer) {
        this.coisaParaFazer = coisaParaFazer;
    }
    public void salvar_bd(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Calendario").child(coisaParaFazer+","+nomedDoVagabundo).setValue(this);
    }
}
