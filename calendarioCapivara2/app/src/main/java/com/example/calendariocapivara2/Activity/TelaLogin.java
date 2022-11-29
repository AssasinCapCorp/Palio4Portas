package com.example.calendariocapivara2.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.calendariocapivara2.Utilitis.CentralTeste;
import com.example.calendariocapivara2.Utilitis.Constants;
import com.example.calendariocapivara2.Utilitis.Muleta;
import com.example.calendariocapivara2.Objetos.CalendarioSemanal;
import com.example.calendariocapivara2.Objetos.Usuario2;
import com.example.calendariocapivara2.R;
import com.example.calendariocapivara2.Utilitis.PreferenceManager;
import com.example.calendariocapivara2.fragments.HomeFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class TelaLogin extends AppCompatActivity {
private PreferenceManager preferenceManager;
    EditText login,senha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);
        FirebaseApp.initializeApp(this);
        getSupportActionBar().hide();
        login = findViewById(R.id.loginLogin);
        senha = findViewById(R.id.loginSenha);
        preferenceManager = new PreferenceManager(this);
    }
    public void irParaCadastro(View v){
        Intent i = new Intent(this,TelaLogin.class);
        startActivity(i);
    }
    public void botao(View v){
        entrar();
    }
    public void entrar(){
        String log = login.getText().toString().trim();
        String sen = senha.getText().toString().trim();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Usuario");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean aux = true;
                for(DataSnapshot d : snapshot.getChildren()){
                    Usuario2 u = d.getValue(Usuario2.class);
                    if(u.getNome().equals(log) && u.getSenha().equals(sen)){
                       // Muleta.usuario2 = u;
                        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                     //   preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                        preferenceManager.putString(Constants.KEY_NAME,login.getText().toString());
                        preferenceManager.putString(Constants.KEY_PASSWORD,senha.getText().toString());
                        preferenceManager.putString(Constants.KEY_DIA_DE_INICIO,u.getDiaInicio()+"");
                        preferenceManager.putString(Constants.KEY_ANO_DE_INICIO,u.getAnoInicio()+"");
                        aux = false;
                       // telaCalendarioSemana();
                        DatabaseReference po = FirebaseDatabase.getInstance().getReference("CalendarioSemanal");
                        po.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for(DataSnapshot d: snapshot.getChildren()){
                                 CalendarioSemanal ca = d.getValue(CalendarioSemanal.class);
                                 if(ca.getNomedoUser().equals(u.getNome())){
                                     preferenceManager.putString(Constants.KEY_CALENDARIO_SEMANAL,ca.getNomedoUser()+","+ca.getSegunda()+","+ca.getTerca()+","+ca.getQuarta()+","+ca.getQuinta()+","+ca.getSexta()+","+ca.getSabado()+","+ca.getDomingo());
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
    public void entrarFireStoneBoladoMuitoFodaEManeiro(){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_NAME,login.getText().toString())
                .whereEqualTo(Constants.KEY_PASSWORD,senha.getText().toString()).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() >0){
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                        preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                        preferenceManager.putString(Constants.KEY_NAME,login.getText().toString());
                        preferenceManager.putString(Constants.KEY_PASSWORD,senha.getText().toString());
                        Intent i = new Intent(this, HomeFragment.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }else{print("EXPLOSÃO");}
                });

        }
    public void telaMaterias(){
        Intent i = new Intent(this, CentralTeste.class);
        startActivity(i);
    }
    public void telaCalendarioSemana(){Intent i = new Intent(this,TelaProgramarSemana.class);startActivity(i);}
    public void print(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

}

