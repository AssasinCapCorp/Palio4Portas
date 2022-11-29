package com.example.calendariocapivara2.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calendariocapivara2.Objetos.Blocos;
import com.example.calendariocapivara2.Objetos.Calendario;
import com.example.calendariocapivara2.Utilitis.Constants;
import com.example.calendariocapivara2.Utilitis.Muleta;
import com.example.calendariocapivara2.R;
import com.example.calendariocapivara2.Adapter.RcCalendario;
import com.example.calendariocapivara2.Adapter.RcMateria;
import com.example.calendariocapivara2.Activity.TelaAdicionarCoisaParaFazer;
import com.example.calendariocapivara2.Utilitis.PreferenceManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;


public class CalendarioFragment extends Fragment {
    private TextView mes,dom,sen,ter,quar,qui,sex,sab;
    private ImageView mdom,msen,mter,mquar,mquir,msex,msab,mais;
    private RecyclerView rv;
    private RcCalendario adapter;
    public static ArrayList<Calendario> listDeCoisas;
    PreferenceManager preferenceManager;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_calendario, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        preferenceManager = new PreferenceManager(getContext());
        rv = v.findViewById(R.id.rvCalendario);
        dom = v.findViewById(R.id.domingo);
        sen = v.findViewById(R.id.segunda);
        ter = v.findViewById(R.id.terca);
        quar = v.findViewById(R.id.quarta);
        qui = v.findViewById(R.id.quinta);
        sex = v.findViewById(R.id.sexta);
        sab = v.findViewById(R.id.sabado);
        //--------------------------------//
        mdom = v.findViewById(R.id.daySelectorDomingo);
        msen = v.findViewById(R.id.daySelectorSegunda);
        mter = v.findViewById(R.id.daySelectorTerca);
        mquar = v.findViewById(R.id.daySelectorQuarta);
        mquir = v.findViewById(R.id.daySelectorQuinta);
        msex = v.findViewById(R.id.daySelectorSexta);
        msab = v.findViewById(R.id.daySelectorSabado);
        mais = v.findViewById(R.id.mais);
        mais.setOnClickListener(v1 -> {adicionarCoisaParaFazer();});
        int diaDoDomingo = 0;
        mes = v.findViewById(R.id.mes);
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    diaDoDomingo = LocalDateTime.now().getDayOfMonth();
                }
                break;
        }
        ArrayList<Integer> semana =  new ArrayList<>();
        Calendar calendario = Calendar.getInstance();
        calendario.set(LocalDateTime.now().getYear(),LocalDateTime.now().getMonthValue()-2,1);


        for(int i =0; i<7;i++){
            if(diaDoDomingo+i <1){
                semana.add(calendario.getActualMaximum(Calendar.DAY_OF_MONTH)+diaDoDomingo+i);


           // Toast.makeText(getContext(),calendario.getActualMaximum((LocalDateTime.now().getMonthValue()) -1) - diaDoDomingo,Toast.LENGTH_LONG).show();
            }else{
            semana.add(diaDoDomingo+i);}
        }
        int o = 0;
for(int i : semana){
    if(i > getQuantidadeDiasByMes(LocalDateTime.now().getMonthValue())){
        i=i-getQuantidadeDiasByMes(LocalDateTime.now().getMonthValue());
        semana.set(o,i);
    }
    o++;
}
Toast.makeText(getContext(),getQuantidadeDiasByMes(LocalDateTime.now().getMonthValue())+"",Toast.LENGTH_LONG).show();
        dom.setText(semana.get(0)+"");
        sen.setText(semana.get(1) +"");
        ter.setText(semana.get(2)+"");
        quar.setText(semana.get(3)+"");
        qui.setText(semana.get(4)+"");
        sex.setText(semana.get(5)+"");
        sab.setText(semana.get(6)+"");
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
       // Toast.makeText(getContext(),"onStart 1",Toast.LENGTH_LONG).show();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Calendario");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Calendario> coisas = new ArrayList<>();
                for(DataSnapshot d : snapshot.getChildren()){
                    Calendario ca = d.getValue(Calendario.class);

                    if(ca.getNomedDoVagabundo().equals(preferenceManager.getString(Constants.KEY_NAME,null))){
                        coisas.add(ca);
                    }
                }
                CalendarioFragment.listDeCoisas = coisas;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}});

    }

    @Override
    public void onResume() {
        super.onResume();

        //Toast.makeText(getContext(),"onResume 2",Toast.LENGTH_LONG).show();

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<Calendario> listaDeHoje = new ArrayList<>();
        try {


            for (Calendario i : listDeCoisas) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (i.ano == LocalDateTime.now().getYear() && i.diaDoAno == LocalDateTime.now().getDayOfMonth() && stringParaNum(i.mes) == LocalDateTime.now().getMonthValue()) {
                        listaDeHoje.add(i);
                    }
                }
            }
        }catch (Exception e){
        }
        adapter = new RcCalendario(getContext(), listaDeHoje, new RcMateria.OnItemClickListener() {
            @Override
            public void onItemClick(Blocos bloco) {

            }
        });
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
    public void adicionarCoisaParaFazer(){
        Intent i = new Intent(getContext(), TelaAdicionarCoisaParaFazer.class);
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
                Toast.makeText(getContext(),"escreveu errado",Toast.LENGTH_LONG).show();
        }
        return 13;
    }
    public static int getQuantidadeDiasByMes(int mes){
        Calendar datas = new GregorianCalendar();
        datas.set(Calendar.MONTH, mes-1);//2
        int quantidadeDias = datas.getActualMaximum (Calendar.DAY_OF_MONTH);
        return quantidadeDias ;
    }
}