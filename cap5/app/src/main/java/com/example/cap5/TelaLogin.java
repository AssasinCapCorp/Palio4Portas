package com.example.cap5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TelaLogin extends AppCompatActivity {
    EditText login,senha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);
        FirebaseApp.initializeApp(this);
        getSupportActionBar().hide();
        login = findViewById(R.id.loginLogin);
        senha = findViewById(R.id.loginSenha);
    }
    public void irParaCadastro(View v){
        Intent i = new Intent(this,TelaLogin.class);
        startActivity(i);
    }
    public void botao(View v){
        entrar();
    }
    public void entrar(){
        String log = login.getText().toString();
        String sen = senha.getText().toString();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Usuario");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean aux = true;
                for(DataSnapshot d : snapshot.getChildren()){
                    Usuario u = d.getValue(Usuario.class);
                    if(u.getNome().equals(log) && u.getSenha().equals(sen)){
                        Muleta.usuario = u;
                        aux = false;
                        //Intent i = new Intent(this,) fazer para a primeira tela
                    }
                }
                if(aux){
                    TelaCadastro t = new TelaCadastro();
                    t.print("Usuario não encontrado");
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}