package com.example.calendariocapivara2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

public class TelaCalendario extends AppCompatActivity {
    private TextView mes,dom,sen,ter,quar,qui,sex,sab;
    private ImageView mdom,msen,mter,mquar,mquir,msex,msab;
    private RecyclerView rv;
    private RcCalendario adapter;
    static ArrayList<Calendario> listDeCoisas;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_calendario);
        getSupportActionBar().hide();
        dom = findViewById(R.id.domingo);
        sen = findViewById(R.id.segunda);
        ter = findViewById(R.id.terca);
        quar = findViewById(R.id.quarta);
        qui = findViewById(R.id.quinta);
        sex = findViewById(R.id.sexta);
        sab = findViewById(R.id.sabado);
        //--------------------------------//
        mdom = findViewById(R.id.daySelectorDomingo);
        msen = findViewById(R.id.daySelectorSegunda);
        mter = findViewById(R.id.daySelectorTerca);
        mquar = findViewById(R.id.daySelectorQuarta);
        mquir = findViewById(R.id.daySelectorQuinta);
        msex = findViewById(R.id.daySelectorSexta);
        msab = findViewById(R.id.daySelectorSabado);
        int diaDoDomingo = 0;
        mes = findViewById(R.id.mes);
        mes.setText(LocalDateTime.now().getMonth()+"");
        switch (LocalDateTime.now().getDayOfWeek()+""){
            case "MONDAY":
                msen.setVisibility(View.VISIBLE);
                diaDoDomingo = LocalDateTime.now().getDayOfMonth()-1;
                break;
            case "TUESDAY":
                mter.setVisibility(View.VISIBLE);
                diaDoDomingo = LocalDateTime.now().getDayOfMonth()-2;
                break;
            case "WEDNESDAY":
                mquar.setVisibility(View.VISIBLE);
                diaDoDomingo = LocalDateTime.now().getDayOfMonth()-3;
                break;
            case "THURSDAY":
                mquir.setVisibility(View.VISIBLE);
                diaDoDomingo = LocalDateTime.now().getDayOfMonth()-4;
                break;
            case "FRIDAY":
                msex.setVisibility(View.VISIBLE);
                diaDoDomingo = LocalDateTime.now().getDayOfMonth()-5;
                break;
            case "SATURDAY":
                msab.setVisibility(View.VISIBLE);
                diaDoDomingo = LocalDateTime.now().getDayOfMonth()-6;
                break;
            case "SUNDAY":
                mdom.setVisibility(View.VISIBLE);
                diaDoDomingo = LocalDateTime.now().getDayOfMonth();
                break;
        }
        ArrayList<Integer> semana =  new ArrayList<>();
        for(int i =0; i<7;i++){
            semana.add(diaDoDomingo+i);
        }
        dom.setText(diaDoDomingo+"");
        sen.setText(semana.get(1) +"");
        ter.setText(semana.get(2)+"");
        quar.setText(semana.get(3)+"");
        qui.setText(semana.get(4)+"");
        sex.setText(semana.get(5)+"");
        sab.setText(semana.get(6)+"");
        rv = findViewById(R.id.rvCalendario);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Calendario> listaDeHoje = new ArrayList<>();
        for(Calendario i: listDeCoisas){
            if(i.ano == LocalDateTime.now().getYear() && i.diaDoAno == LocalDateTime.now().getDayOfMonth() && stringParaNum(i.mes) == LocalDateTime.now().getMonthValue()){
                listaDeHoje.add(i);
            }
        }
        adapter = new RcCalendario(this, listaDeHoje, new RcMateria.OnItemClickListener() {
            @Override
            public void onItemClick(Blocos bloco) {

            }
        });
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public void AdicionarCoisaParaFazer(View v){
        Intent i = new Intent(this,TelaAdicionarCoisaParaFazer.class);
        startActivity(i);
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
}