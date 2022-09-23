package com.example.calendariofodacapivaras;

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

public class TelaCadastro extends AppCompatActivity {
    EditText senha, senha2, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);
        getSupportActionBar().hide();
        FirebaseApp.initializeApp(this);
        login = findViewById(R.id.nomeCad);
        senha = findViewById(R.id.senhaCad);
        senha2 = findViewById(R.id.senha2Cad);
    }
    public void botao(View v){
        cadastrar();
    }
    public void print(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
    public void irParaTelaLogin(){
        Intent i = new Intent(this,TelaLogin.class);
        startActivity(i);
    }
    public void cadastrar(){
        String log = login.getText().toString();
        String sen = senha.getText().toString();
        String sen2 = senha2.getText().toString();
        if (sen.equals(sen2)) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Usuario");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean aux = false;
                    for (DataSnapshot d : snapshot.getChildren()) {
                        Usuario u = d.getValue(Usuario.class);
                        if (!log.equals(u.getNome())) {
                            u.salvar_bd();
                            irParaTelaLogin();
                            aux = true;
                        }
                    }
                    if(!aux){
                        print("Esse usuário já existe");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } else {
            Toast.makeText(this, "As senhas devem ser iguais", Toast.LENGTH_SHORT).show();
        }
    }
}