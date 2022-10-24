package com.example.calendariocapivara2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class TelaAdicionarCoisaParaFazer extends AppCompatActivity {
    EditText tituloc,diac,mesc,anoc,horas;
    Button botones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_adicionar_coisa_para_fazer);
        getSupportActionBar().hide();
        tituloc = findViewById(R.id.descricaoPika);
        diac = findViewById(R.id.diaDoLoco);
        mesc = findViewById(R.id.mesDoLoco);
        anoc = findViewById(R.id.anoDoLoco);
        horas = findViewById(R.id.horaDoLoco);
        botones = findViewById(R.id.buttonoooo);
        botones.setOnClickListener(view ->{
            if(tituloc.getText().toString().equals("") && diac.getText().toString().equals("") && mesc.getText().toString().equals("") && anoc.getText().toString().equals("") && horas.getText().toString().equals("")){
                Toast.makeText(this,"coloca tudo mano",Toast.LENGTH_LONG).show();
            }else{criarOBaguere(pegarOMes());}
        });
    }
    public String numParaStrinMES(int mes){
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
    public int stringParaNum(String mes){
        switch (mes.toLowerCase(Locale.ROOT)){
            case "janeiro":
                return 1;
            case "fevereiro":
                return 2;
            case "março":
                return 3;
            case "abril":
                return 4;
            case "maio":
                return 5;
            case "junho":
                return 6;
            case "julho":
                return 7;
            case "agosto":
                return 8;
            case "setembro":
                return 9;
            case "outubro":
                return 10;
            case "novembro":
                return 11;
            case "dezembro":
                return 12;
            default:
                Toast.makeText(this,"escreveu errado",Toast.LENGTH_LONG).show();
        }
        return 13;
    }


    public int pegarOMes(){
        String mes = mesc.getText().toString();
        try {
            int nmes = Integer.parseInt(mes);
            return nmes;
        }catch (Exception e){
            return stringParaNum(mes.toLowerCase(Locale.ROOT));
        }
    }
    public void criarOBaguere(int nmes) {
        if (!(nmes == 13)) {
            int ano = Integer.parseInt(anoc.getText().toString());
            int dia = Integer.parseInt(diac.getText().toString());
            Calendar calendario = Calendar.getInstance();
            if (calendario.getMinimum(nmes) <= dia && dia <= calendario.getMaximum(nmes)) {

                Calendario ca = new Calendario(Muleta.usuario2.nome, dia, ano, horas.getText().toString(), numParaStrinMES(nmes), tituloc.getText().toString());
                ca.salvar_bd();
                Toast.makeText(this, "coisa para fazer criada", Toast.LENGTH_LONG).show();
                passarCalendario();
            }

        }
    }
        public void passarCalendario () {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Calendario");
            ref.addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<Calendario> coisas = new ArrayList<>();
                    for (DataSnapshot d : snapshot.getChildren()) {
                        Calendario ca = d.getValue(Calendario.class);

                        if (ca.nomedDoVagabundo.equals(Muleta.usuario2.getNome()) && stringParaNum(ca.getMes()) == LocalDate.now().getMonthValue()) {
                            coisas.add(ca);
                        }
                    }
                    TelaCalendario.listDeCoisas = coisas;
                    passarCalendario2();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        }
        public void passarCalendario2(){
            Intent i = new Intent(this, TelaCalendario.class);
            startActivity(i);
        }
    }