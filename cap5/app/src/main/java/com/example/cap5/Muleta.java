package com.example.cap5;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Muleta {
    static Usuario usuario;
    static ArrayList<Usuario> listaUsuario;
    public Usuario getUsuario(){
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        Muleta.usuario = usuario;
    }

    public static ArrayList<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public static void setListaUsuario(ArrayList<Usuario> listaUsuario) {
        Muleta.listaUsuario = listaUsuario;
    }
    public static void pegarLista(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Usuario");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Usuario> placeHolder = new ArrayList<>();
                for(DataSnapshot d : snapshot.getChildren()){
                    Usuario u = d.getValue(Usuario.class);
                    placeHolder.add(u);
                }
                try {
                    if (placeHolder.isEmpty()) {
                    }
                }catch (NullPointerException po){
                    placeHolder = new ArrayList<>();
                }
                listaUsuario = (placeHolder);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}});
    }
}
