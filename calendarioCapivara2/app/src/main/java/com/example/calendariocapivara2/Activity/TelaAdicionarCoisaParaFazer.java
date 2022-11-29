package com.example.calendariocapivara2.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.calendariocapivara2.Utilitis.CentralTeste;
import com.example.calendariocapivara2.Utilitis.Constants;
import com.example.calendariocapivara2.Utilitis.Muleta;
import com.example.calendariocapivara2.Objetos.Calendario;
import com.example.calendariocapivara2.R;
import com.example.calendariocapivara2.Utilitis.PreferenceManager;
import com.example.calendariocapivara2.fragments.CalendarioFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class TelaAdicionarCoisaParaFazer extends AppCompatActivity {
    EditText tituloc,diac,mesc,anoc,horas,minu;
    Button botones;
    PreferenceManager preferenceManager;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(getApplicationContext());
        setContentView(R.layout.activity_tela_adicionar_coisa_para_fazer);
        getSupportActionBar().hide();
        tituloc = findViewById(R.id.descricaoPika);
        diac = findViewById(R.id.diaDoLoco);
        mesc = findViewById(R.id.mesDoLoco);
        anoc = findViewById(R.id.anoDoLoco);
        horas = findViewById(R.id.horaDoLoco);
        botones = findViewById(R.id.buttonoooo);
        minu = findViewById(R.id.minutoDoLoco);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            diac.setText(LocalDateTime.now().getDayOfMonth()+"");
            mesc.setText(LocalDateTime.now().getMonthValue()+"");
            anoc.setText(LocalDateTime.now().getYear()+"");
        }
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
            int nmes = Integer.parseInt(mesc.getText().toString());
         //  Toast.makeText(this,mes+" 1",Toast.LENGTH_LONG).show();
            return nmes;
        }catch (Exception e){
         //   Toast.makeText(this,mes+" 2",Toast.LENGTH_LONG).show();
            return stringParaNum(mes.toLowerCase(Locale.ROOT));

        }
    }
    public void criarOBaguere(int nmes) {
      //  Toast.makeText(this,"",Toast.LENGTH_LONG).show();
        if (nmes < 13) {
            int ano = Integer.parseInt(anoc.getText().toString());
            int dia = Integer.parseInt(diac.getText().toString());
            if (1 <= dia && dia <= getQuantidadeDiasByMes(nmes) ){
                Calendario ca = new Calendario(preferenceManager.getString(Constants.KEY_NAME,null), dia, ano, horas.getText().toString()+":"+minu.getText().toString(), numParaStrinMES(nmes), tituloc.getText().toString());
                ca.salvar_bd();
                Toast.makeText(this, "coisa para fazer criada", Toast.LENGTH_LONG).show();
                passarCalendario();
            }

        }
       // Toast.makeText(this,"boooooom "+nmes,Toast.LENGTH_LONG).show();
    }
        public void passarCalendario () {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Calendario");
            ref.addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<Calendario> coisas = new ArrayList<>();
                   // Toast.makeText(this,"PASSAR CALENDARIO",Toast.LENGTH_LONG).show();
                    for (DataSnapshot d : snapshot.getChildren()) {
                        Calendario ca = d.getValue(Calendario.class);
                    if(tituloc.getText().equals("")){
                        tituloc.setText("alguma coisa ai");
                    }if(horas.equals("")){
                        horas.setText(00+"");
                        }
                    if(minu.getText().equals("")){
                        minu.setText(00+"");
                        }
                        if (ca.getNomedDoVagabundo().equals(preferenceManager.getString(Constants.KEY_NAME,null)) && stringParaNum(ca.getMes()) == LocalDate.now().getMonthValue()) {
                            coisas.add(ca);
                        }
                    }
                    CalendarioFragment.listDeCoisas = coisas;
                    passarCalendario2();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        }
        public void passarCalendario2(){
            Intent i = new Intent(this, CentralTeste.class);
            startActivity(i);
        }
    public static int getQuantidadeDiasByMes(int mes){
        Calendar datas = new GregorianCalendar();
        datas.set(Calendar.MONTH, mes-1);//2
        int quantidadeDias = datas.getActualMaximum (Calendar.DAY_OF_MONTH);
        return quantidadeDias ;
    }
    }