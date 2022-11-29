package com.example.calendariocapivara2.Objetos;

import android.graphics.Bitmap;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Struct;
import java.util.ArrayList;

public class Questao {
    public String idQ;
    public ArrayList<String> alternativa;
    public String alternativaCerta;
    String resposta;


    public Questao(String idQ, ArrayList<String> alternativa, String alternativaCerta, String resposta) {
        this.idQ = idQ;
        this.alternativa = alternativa;
        this.alternativaCerta = alternativaCerta;
        this.resposta = resposta;
    }

    public Questao() {
    }

    public String getAlternativaCerta() {
        return alternativaCerta;
    }

    public void setAlternativaCerta(String alternativaCerta) {
        this.alternativaCerta = alternativaCerta;
    }

    public String getIdQ() {
        return idQ;
    }

    public void setIdQ(String idQ) {
        this.idQ = idQ;
    }

    public ArrayList<String> getAlternativa() {
        return alternativa;
    }

    public void setAlternativa(ArrayList<String> alternativa) {
        this.alternativa = alternativa;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }
    public void salvarBd(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Questao").child(idQ+"").setValue(this);
    }
}
