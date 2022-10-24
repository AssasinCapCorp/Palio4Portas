package com.example.calendariocapivara2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
                    Usuario2 u = d.getValue(Usuario2.class);
                    if(u.getNome().equals(log) && u.getSenha().equals(sen)){
                        Muleta.usuario2 = u;
                        aux = false;
                       // telaCalendarioSemana();
                        DatabaseReference po = FirebaseDatabase.getInstance().getReference("CalendarioSemanal");
                        po.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot d: snapshot.getChildren()){
                                 CalendarioSemanal ca = d.getValue(CalendarioSemanal.class);
                                 if(ca.nomedoUser.equals(u.getNome())){
                                     Muleta.cs =ca;
                                     telaMaterias();
                                 }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}});
                    }
                }
                if(aux){
                    print("Usuario não encontrado");
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void telaMaterias(){ Intent i = new Intent(this,TelaAtividade.class);// fazer para a primeira tela
        startActivity(i);}
    public void telaCalendarioSemana(){Intent i = new Intent(this,TelaProgramarSemana.class);startActivity(i);}
    public void print(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

}

