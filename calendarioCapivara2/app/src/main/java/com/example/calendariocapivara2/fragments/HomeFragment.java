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

import com.example.calendariocapivara2.Objetos.BlocosEstudados;
import com.example.calendariocapivara2.Objetos.CalendarioSemanal;
import com.example.calendariocapivara2.Utilitis.*;
import com.example.calendariocapivara2.Objetos.BlocoDoDia;
import com.example.calendariocapivara2.Objetos.Blocos;
import com.example.calendariocapivara2.Utilitis.Muleta;
import com.example.calendariocapivara2.Objetos.Questao;
import com.example.calendariocapivara2.R;
import com.example.calendariocapivara2.Activity.TelaPratica;
import com.example.calendariocapivara2.databinding.FragmentHomeBinding;
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


public class HomeFragment extends Fragment {
    private ImageView foto,imagemBlocoDoDia,praticar;
    TextView nomeDoBloco;
private FragmentHomeBinding binding;
 PreferenceManager preferenceManager;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        binding.getRoot().setSystemUiVisibility(uiOptions);
        preferenceManager = new PreferenceManager(getContext());
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        foto = binding.getRoot().findViewById(R.id.fotoUser);
        praticar = binding.getRoot().findViewById(R.id.botaoPraticar);
        fotoUser();
        imagemBlocoDoDia = binding.getRoot().findViewById(R.id.fotoDoBloco);
        nomeDoBloco = binding.getRoot().findViewById(R.id.nomeDoBloco);
        blocoDoDiaMuitoFodaEManeiro();
        praticar.setOnClickListener(v1 -> {
            questaoFoda();
        });


//        BlocosEstudados bl = new BlocosEstudados(new ArrayList<>(),"a");
//        bl.salvar_bd();


        return binding.getRoot();
    }
    public void fotoUser(){
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("/fotosUser/" + preferenceManager.getString(Constants.KEY_NAME,null)+ ".jpg");
        try {
            File arq = File.createTempFile("foto","jpg");
            storageRef.getFile(arq).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bit = BitmapFactory.decodeFile(arq.getAbsolutePath());
                    binding.fotoUser.setImageBitmap(bit);

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
    public void questaoFoda(){
        ArrayList<Questao> listaDeQuestao = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Questao");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d:snapshot.getChildren()){
                    Questao q = d.getValue(Questao.class);
                    listaDeQuestao.add(q);
                }
                Random r = new Random();
                TelaPratica.questao = listaDeQuestao.get(r.nextInt(listaDeQuestao.size()));
                passarParaTelaPratique();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}});

    }
    public void passarParaTelaPratique(){ Intent i = new Intent(getContext(),TelaPratica.class);
        startActivity(i);}
    public void print(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
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
                            String[] t = preferenceManager.getString(Constants.KEY_CALENDARIO_SEMANAL,null).split(",",10);
                            CalendarioSemanal cs = new CalendarioSemanal(t[0],Integer.parseInt(t[1]),Integer.parseInt(t[2]),Integer.parseInt(t[3]),Integer.parseInt(t[4]),Integer.parseInt(t[5]),Integer.parseInt(t[6]),Integer.parseInt(t[7]));
                            String[] listaDeCoisas = new String[]{"nada", "Matematica", "Natureza", "linguagens", "Humanas"};
                            ArrayList<Blocos> blocos = new ArrayList<>();
                            switch (LocalDateTime.now().getDayOfWeek() + "") {
                                case "MONDAY":
                                    materiaDoDia = cs.getSegunda();
                                    break;
                                case "TUESDAY":
                                    materiaDoDia = cs.getTerca();
                                    break;
                                case "WEDNESDAY":
                                    materiaDoDia = cs.getQuarta();
                                    break;
                                case "THURSDAY":
                                    materiaDoDia = cs.getQuinta();
                                    break;
                                case "FRIDAY":
                                    materiaDoDia = cs.getSexta();
                                    break;
                                case "SATURDAY":
                                    materiaDoDia = cs.getSabado();
                                    break;
                                case "SUNDAY":
                                    materiaDoDia = cs.getDomingo();
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
                            BlocoDoDia bdd = new BlocoDoDia(LocalDateTime.now().getYear(), LocalDateTime.now().getDayOfYear(),preferenceManager.getString(Constants.KEY_NAME,null), blocoSelecionado);
                            bdd.salvar_bd();
                            setarImagem(bdd);
                            MateriasFragment.blocoDoDia = bdd.getBlocoDoDia().getNome();
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
                    imagemBlocoDoDia.setImageBitmap(bit);
                    nomeDoBloco.setText(bo.getBlocoDoDia().getNome());


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Toast.makeText(TelaAtividade.this,"/bloco/"+bo.blocoDoDia+".jpg",Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(),"Imagem não pôde ser carregada",Toast.LENGTH_SHORT).show();
                    nomeDoBloco.setText("era para ser :"+bo.getBlocoDoDia().getNome());
                }});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}