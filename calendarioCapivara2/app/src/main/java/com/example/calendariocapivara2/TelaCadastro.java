package com.example.calendariocapivara2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

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

    public void botao(View v) {
        cadastrar();
    }

    public void print(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void irParaTelaLogin() {
        Intent i = new Intent(this, TelaLogin.class);
        startActivity(i);
    }

    public void cadastrar() {
        String log = login.getText().toString();
        String sen = senha.getText().toString();
        String sen2 = senha2.getText().toString();
        if (sen.equals(sen2)) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Usuario");
            ref.addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean aux = false;
                    for (DataSnapshot d : snapshot.getChildren()) {
                       Usuario2 foda = d.getValue(Usuario2.class);
                        if (!log.equals(foda.getNome())) {
                            aux = true;
                        }
                    }
                    if (!aux) {
                        print("Esse usuário já existe");

                    } else if(aux) {
                        Calendario primeiraCoisa = new Calendario(log,LocalDateTime.now().getDayOfYear(), LocalDateTime.now().getYear(), LocalDateTime.now().getHour()+"",intParaStrinMES(LocalDateTime.now().getMonthValue())+"","Começar a estudar");
                        primeiraCoisa.salvar_bd();

                        Usuario2 po = new Usuario2(log, sen,LocalDateTime.now().getDayOfYear(),LocalDateTime.now().getYear());
                        TelaProgramarSemana.usuario = po;
                        telaCalendarioSemana();
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
    public  String intParaStrinMES(int mes){
        switch (mes){
            case 1:
                return "janeiro";
            case 2:
                return  "fevereiro";
            case 3:
                return "março";
            case 4:
                return "abril";
            case 5:
                return "maio";
            case 6:
                return "junho";
            case 7:
                return "julho";
            case 8:
                return "agosto";
            case 9:
                return "setembro";
            case 10:
                return "outubro";
            case 11:
                return "novembro";
            case 12:
                return "dezembro";
        }
        return "janeiro";
    }
    public void telaCalendarioSemana(){Intent i = new Intent(this,TelaProgramarSemana.class);startActivity(i);}



}