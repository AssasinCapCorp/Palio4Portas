package com.example.calendariocapivara2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class TelaAtividade extends AppCompatActivity {
    private ImageView foto,imagemBlocoDoDia;
    TextView nomeDoBloco;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_atividade);
        getSupportActionBar().hide();
        foto = findViewById(R.id.fotoUser);
        fotoUser();
        imagemBlocoDoDia = findViewById(R.id.fotoDoBloco);
        nomeDoBloco = findViewById(R.id.nomeDoBloco);
        blocoDoDiaMuitoFodaEManeiro();
    }
    public void fotoUser(){
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("/fotosUser/" +Muleta.usuario2.getNome()+ ".jpg");
        try {
            File arq = File.createTempFile("foto","jpg");
            storageRef.getFile(arq).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bit = BitmapFactory.decodeFile(arq.getAbsolutePath());
                    foto.setImageBitmap(bit);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(TelaAtividade.this,"Imagem não pôde ser carregada",Toast.LENGTH_SHORT).show();
                }});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void perfil(View v){
        Intent i = new Intent(this,TelaPerfil.class);
        startActivity(i);
    }
    public void materia(View v){
        Intent i = new Intent(this,TelaMaterias.class);
        startActivity(i);
    }
    public void questaoFoda(View c){
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
    public void passarParaTelaPratique(){ Intent i = new Intent(this,TelaPratica.class);
        startActivity(i);}
    public void passarCalendario(View v){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Calendario");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Calendario> coisas = new ArrayList<>();
                for(DataSnapshot d : snapshot.getChildren()){
                    Calendario ca = d.getValue(Calendario.class);

                    if(ca.nomedDoVagabundo.equals(Muleta.usuario2.getNome())){
                        coisas.add(ca);
                    }
                }
                TelaCalendario.listDeCoisas = coisas;
                passarCalendario2();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}});

}
public void passarCalendario2(){Intent i = new Intent(this,TelaCalendario.class);
    startActivity(i);}
public void print(String msg){
    Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
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
                    if(bode.user.equals(Muleta.usuario2.getNome()) && bode.ano == LocalDateTime.now().getYear() && bode.dia == LocalDateTime.now().getDayOfYear()){
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
                                materiaDoDia = Muleta.cs.segunda;
                                break;
                            case "TUESDAY":
                                materiaDoDia = Muleta.getCs().terca;
                                break;
                            case "WEDNESDAY":
                                materiaDoDia = Muleta.getCs().quarta;
                                break;
                            case "THURSDAY":
                                materiaDoDia = Muleta.getCs().getQuinta();
                                break;
                            case "FRIDAY":
                                materiaDoDia = Muleta.cs.getSexta();
                                break;
                            case "SATURDAY":
                                materiaDoDia = Muleta.cs.sabado;
                                break;
                            case "SUNDAY":
                                materiaDoDia = Muleta.cs.domingo;
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
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("/bloco/"+bo.blocoDoDia.getNome()+".jpg");
        try {
            File arq = File.createTempFile("foto","jpg");
            storageRef.getFile(arq).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bit = BitmapFactory.decodeFile(arq.getAbsolutePath());
                    imagemBlocoDoDia.setImageBitmap(bit);
                    nomeDoBloco.setText(bo.blocoDoDia.getNome());


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                   // Toast.makeText(TelaAtividade.this,"/bloco/"+bo.blocoDoDia+".jpg",Toast.LENGTH_LONG).show();
                    Toast.makeText(TelaAtividade.this,"Imagem não pôde ser carregada",Toast.LENGTH_SHORT).show();
                    nomeDoBloco.setText("era para ser :"+bo.blocoDoDia.getNome());
                }});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
