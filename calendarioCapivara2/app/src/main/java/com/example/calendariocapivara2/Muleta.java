package com.example.calendariocapivara2;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class Muleta{
    static Usuario2 usuario2;
    static ArrayList<Usuario2> listaUsuario2;
    static CalendarioSemanal cs;
    public Usuario2 getUsuario(){
        return usuario2;
    }

    public static CalendarioSemanal getCs() {
        return cs;
    }

    public static void setCs(CalendarioSemanal cs) {
        Muleta.cs = cs;
    }

    public static void setUsuario(Usuario2 usuario2) {
        Muleta.usuario2 = usuario2;
    }

    public static ArrayList<Usuario2> getListaUsuario() {
        return listaUsuario2;
    }

    public static void setListaUsuario(ArrayList<Usuario2> listaUsuario2) {
        Muleta.listaUsuario2 = listaUsuario2;
    }
    public static void pegarLista(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Usuario");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Usuario2> placeHolder = new ArrayList<>();
             for(DataSnapshot d : snapshot.getChildren()){
                 Usuario2 u = d.getValue(Usuario2.class);
                 placeHolder.add(u);
             }
             try {
                 if (placeHolder.isEmpty()) {
                 }
             }catch (NullPointerException po){
                 placeHolder = new ArrayList<>();
             }
             listaUsuario2 = (placeHolder);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}});
    }

}