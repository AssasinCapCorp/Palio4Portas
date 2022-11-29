package com.example.calendariocapivara2.Objetos;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BlocosEstudados {
    ArrayList<Blocos> blocosEstudados;
    String user;

    public BlocosEstudados() {
    }

    public BlocosEstudados(ArrayList<Blocos> blocosEstudados, String user) {
        this.blocosEstudados = blocosEstudados;
        this.user = user;
    }

    public ArrayList<Blocos> getBlocosEstudados() {
        return blocosEstudados;
    }

    public void setBlocosEstudados(ArrayList<Blocos> blocosEstudados) {
        this.blocosEstudados = blocosEstudados;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    public void salvar_bd(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("BlocosEstudados").child(user).setValue(this);
    }
}
