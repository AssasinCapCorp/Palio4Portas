package com.example.calendariocapivara2.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calendariocapivara2.Adapter.RcMateria;
import com.example.calendariocapivara2.Objetos.BlocoDoDia;
import com.example.calendariocapivara2.Objetos.Blocos;
import com.example.calendariocapivara2.Objetos.BlocosEstudados;
import com.example.calendariocapivara2.Utilitis.CentralTeste;
import com.example.calendariocapivara2.Activity.ListaDeAtividade;
import com.example.calendariocapivara2.Utilitis.Constants;
import com.example.calendariocapivara2.Utilitis.Muleta;
import com.example.calendariocapivara2.R;
import com.example.calendariocapivara2.Utilitis.PreferenceManager;
import com.example.calendariocapivara2.databinding.FragmentHomeBinding;
import com.example.calendariocapivara2.databinding.FragmentMateriasBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class MateriasFragment extends Fragment {

    ImageView imagemDoBloco,mat,hum,cie,ling;
    private TextView nomeDoBloco;
private FragmentHomeBinding binding;
public static String blocoDoDia;
private PreferenceManager preferenceManager;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_materias, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        preferenceManager = new PreferenceManager(getContext());
        mat = v.findViewById(R.id.butMat);
        hum = v.findViewById(R.id.butHum);
        cie = v.findViewById(R.id.butCie);
        ling = v.findViewById(R.id.butLing);
        listener();
        imagemDoBloco = v.findViewById(R.id.imagemDoBloco);
        nomeDoBloco = v.findViewById(R.id.nomeDoBlocoMaterias);
      //  blocoDoDiaMuitoFodaEManeiro();
blocoDoDiaMuitoFodaEManeiro();
        return v;

    }
public void listener(){
        mat.setOnClickListener(v -> {mat();});
        hum.setOnClickListener(v -> {humanas();});
        cie.setOnClickListener(v -> {cie();});
        ling.setOnClickListener(v -> {linguagens();});
}
    @Override
    public void onStart() {
        super.onStart();
    }

    public void mat(){
        listaDeTopicos("Matematica");
    }
    public void cie(){
        listaDeTopicos("Natureza");
    }
    public void linguagens( ){
        listaDeTopicos("linguagens");
    }
    public void humanas(){
        listaDeTopicos("Humanas");
    }

    public void listaDeTopicos(String materia) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Topicos");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Blocos> listaBlocos = new ArrayList<>();
                for (DataSnapshot d : snapshot.getChildren()) {
                    Blocos bloco = d.getValue(Blocos.class);
                    if (bloco.getMateria().equals(materia))
                        listaBlocos.add(bloco);
                }
                ListaDeAtividade.listBlocos = listaBlocos;
                ListaDeAtividade.materia = listaBlocos.get(0).getMateria();
                passarTela();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void print(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
    }
    public void passarTela(){
        Intent i = new Intent(getContext(),ListaDeAtividade.class);
        startActivity(i);
    }
    public void blocoDoDiaMuitoFodaEManeiro(){
        DatabaseReference procurarBlocoDoDiaJaFeito = FirebaseDatabase.getInstance().getReference("DiaFoda");
        procurarBlocoDoDiaJaFeito.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean aux =true;
                BlocoDoDia bo = new BlocoDoDia();
                for(DataSnapshot d: snapshot.getChildren()){
                    BlocoDoDia bode = d.getValue(BlocoDoDia.class);
                    if(bode.getUser().equals(preferenceManager.getString(Constants.KEY_NAME,null)) && bode.getAno() == LocalDateTime.now().getYear() && bode.getDia() == LocalDateTime.now().getDayOfYear()){
                        aux =false;
                        bo = bode;
                    }
                }
                if(aux){
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Topicos");
                    ref.addValueEventListener(new ValueEventListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int materiaDoDia=0;
                            String[] listaDeCoisas = new String[]{"nada", "Matematica", "Natureza", "linguagens", "Humanas"};
                            ArrayList<Blocos> blocos = new ArrayList<>();
                            switch (LocalDateTime.now().getDayOfWeek() + "") {
                                case "MONDAY":
                                    materiaDoDia = Muleta.getCs().getSegunda();
                                    break;
                                case "TUESDAY":
                                    materiaDoDia = Muleta.getCs().getTerca();
                                    break;
                                case "WEDNESDAY":
                                    materiaDoDia = Muleta.getCs().getQuarta();
                                    break;
                                case "THURSDAY":
                                    materiaDoDia = Muleta.getCs().getQuinta();
                                    break;
                                case "FRIDAY":
                                    materiaDoDia = Muleta.getCs().getSexta();
                                    break;
                                case "SATURDAY":
                                    materiaDoDia = Muleta.getCs().getSabado();
                                    break;
                                case "SUNDAY":
                                    materiaDoDia = Muleta.getCs().getDomingo();
                                    break;
                            }
                            for(DataSnapshot d: snapshot.getChildren()){
                                Blocos b = d.getValue(Blocos.class);
                                if(b.getMateria().equals(listaDeCoisas[materiaDoDia])){
                                    blocos.add(b);
                                }
                            }
                            Random r = new Random();
                            Blocos blocoSelecionado = blocos.get(r.nextInt(blocos.size()));
                            BlocoDoDia bdd = new BlocoDoDia(LocalDateTime.now().getYear(), LocalDateTime.now().getDayOfYear(),Muleta.usuario2.getNome(), blocoSelecionado);
                            bdd.salvar_bd();
                            setarImagem(bdd);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}});
                }else{
                    setarImagem(bo);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}});
    }

    public void setarImagem(BlocoDoDia bo){
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("/bloco/"+bo.getBlocoDoDia().getNome()+".jpg");
        try {
            File arq = File.createTempFile("foto","jpg");
            storageRef.getFile(arq).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bit = BitmapFactory.decodeFile(arq.getAbsolutePath());
                    imagemDoBloco.setImageBitmap(bit);
                    nomeDoBloco.setText(bo.getBlocoDoDia().getNome());


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(getContext(),"Imagem não pôde ser carregada",Toast.LENGTH_SHORT).show();

                }});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        CentralTeste.ativi = false;
    }
}