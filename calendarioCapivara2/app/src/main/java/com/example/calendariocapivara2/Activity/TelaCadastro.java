package com.example.calendariocapivara2.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.calendariocapivara2.Objetos.BlocosEstudados;
import com.example.calendariocapivara2.Objetos.Calendario;
import com.example.calendariocapivara2.Objetos.Usuario2;
import com.example.calendariocapivara2.R;
import com.example.calendariocapivara2.Utilitis.Constants;
import com.example.calendariocapivara2.Utilitis.PreferenceManager;
import com.example.calendariocapivara2.databinding.ActivityTelaCadastroBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class TelaCadastro extends AppCompatActivity {
    private PreferenceManager preferenceManager;
     ActivityTelaCadastroBinding binding;
    EditText senha, senha2, login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        FirebaseApp.initializeApp(this);
        login = findViewById(R.id.nomeCad);
        senha = findViewById(R.id.senhaCad);
        senha2 = findViewById(R.id.senha2Cad);
        preferenceManager = new PreferenceManager(this);
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
                        BlocosEstudados b = new BlocosEstudados(new ArrayList<>(),log);
                        b.salvar_bd();
                        Usuario2 po = new Usuario2(log, sen,LocalDateTime.now().getDayOfYear(),LocalDateTime.now().getYear());
                        po.salvar_bd();
                        preferenceManager.putString(Constants.KEY_NAME,log);
                        preferenceManager.putString(Constants.KEY_ANO_DE_INICIO,po.getAnoInicio()+"");
                        preferenceManager.putString(Constants.KEY_DIA_DE_INICIO,po.getDiaInicio()+"");
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
    public void telaCalendarioSemana(){Intent i = new Intent(this,TelaProgramarSemana.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);}

public void cadastrarPeloFirestone(){

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String,Object> data = new HashMap<>();
        data.put(Constants.KEY_NAME,login.getText().toString());
        data.put(Constants.KEY_PASSWORD,senha.getText().toString());
        database.collection(Constants.KEY_COLLECTION_USERS).add(data).addOnSuccessListener(documentReference -> {
            preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
            preferenceManager.putString(Constants.KEY_USER_ID, documentReference.getId());
            preferenceManager.putString(Constants.KEY_NAME,login.getText().toString());
            Intent i = new Intent(this,TelaProgramarSemana.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }).addOnFailureListener(Exception ->{
print("deu ruim");
        });

}

}