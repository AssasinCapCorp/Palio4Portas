package com.example.calendariocapivara2;

import static com.example.calendariocapivara2.R.color.VERMEIO;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ablanco.zoomy.Zoomy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TelaPratica extends AppCompatActivity {
    ImageView q1,q2,q3,q4,q5,bq1,bq2,bq3,bq4,bq5,foto,explicacao,imagemFelicidade;
    TextView titulo,alt1,alt2,alt3,alt4,alt5,resposta;
    static Questao questao;
    boolean respondeu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_pratica);
        getSupportActionBar().hide();
        respondeu = false;
        q1 = findViewById(R.id.q1);
        q2 = findViewById(R.id.q2);
        q3 = findViewById(R.id.q3);
        q4 = findViewById(R.id.q4);
        q5 = findViewById(R.id.q5);
        bq1 = findViewById(R.id.bolinha1);
        bq2 = findViewById(R.id.bolinha2);
        bq3 = findViewById(R.id.bolinha3);
        bq4 = findViewById(R.id.bolinha4);
        bq5 = findViewById(R.id.bolinha5);
        bq1.setVisibility(View.INVISIBLE);
        bq2.setVisibility(View.INVISIBLE);
        bq3.setVisibility(View.INVISIBLE);
        bq4.setVisibility(View.INVISIBLE);
        bq5.setVisibility(View.INVISIBLE);
        alt1 = findViewById(R.id.alt1);
        alt2 = findViewById(R.id.alt2);
        alt3 = findViewById(R.id.alt3);
        alt4 = findViewById(R.id.alt4);
        alt5 = findViewById(R.id.alt5);
        foto = findViewById(R.id.fotoDaQuestao);
        titulo = findViewById(R.id.nomeDaQuestao);
        resposta = findViewById(R.id.explicacaotxt);
        explicacao = findViewById(R.id.explicacao);
        imagemFelicidade = findViewById(R.id.capivaraMuitoFoda);
        escolhoVC();



        Zoomy.Builder builder = new Zoomy.Builder(this).target(foto);
        builder.register();


    }
    public void escolhoVC(){
        //objeto questão
//        String[] alt= new String[]{"Cartilaginoso", "Sanguíneo", "Muscular", "Nervoso", "Ósseo"};
//        ArrayList<String> alternativas = new ArrayList<>(Arrays.asList(alt));
//        Questao q = new Questao("(ENEM - 2019)7456356",alternativas,"E","Mano o bagulho vai no calcio no corpo. E ossos é a única das aternativa que tem calcio");
//        questao = q;
        //foto e alternativas
        fotoDaQuestao(questao);
        alt1.setText(questao.alternativa.get(0));
        alt2.setText(questao.alternativa.get(1));
        alt3.setText(questao.alternativa.get(2));
        alt4.setText(questao.alternativa.get(3));
        alt5.setText(questao.alternativa.get(4));
        //titulo
        String nomeDoVestibularDaQuestao ="";
        String cod="";
        boolean aux = false;
        for(String i:questao.idQ.split("")){
        if(!aux){
            nomeDoVestibularDaQuestao += i;
            }
        else{
            cod+=i;
        }
        if(i.equals(")")){aux = true;}
        }
        titulo.setText(nomeDoVestibularDaQuestao+"   cod:"+cod);
    }
    public void fotoDaQuestao(Questao q){
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("/questao/"+q.idQ+".png");
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

                   // Toast.makeText(TelaPratica.this,"Imagem não pôde ser carregada pq vc é muito gay",Toast.LENGTH_SHORT).show();
                    Toast.makeText(TelaPratica.this,"/questao/"+q.idQ+".png",Toast.LENGTH_SHORT).show();
                }});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void resp1(View v){if(!respondeu){respondeu = true;correcao("A");bq1.setVisibility(View.VISIBLE);}}
    public void resp2(View v){if(!respondeu){respondeu = true;correcao("B");bq2.setVisibility(View.VISIBLE);}}
    public void resp3(View v){if(!respondeu){respondeu = true;correcao("C");bq3.setVisibility(View.VISIBLE);}}
    public void resp4(View v){if(!respondeu){respondeu = true;correcao("D");bq4.setVisibility(View.VISIBLE);}}
    public void resp5(View v){if(!respondeu){respondeu = true;correcao("E");bq5.setVisibility(View.VISIBLE);}}
    @SuppressLint("ResourceAsColor")
    public void correcao(String alternativa){
        boolean acertou =false;
        if(alternativa.equals(questao.alternativaCerta)){
            acertou = true;
            imagemFelicidade.setImageResource(R.drawable.capivarafelizoooooooooo);
        }
        switch (questao.alternativaCerta+""){
            case "A":
                alt1.setTextColor(getResources().getColor(R.color.VERDE));
                break;
            case "B":
                alt2.setTextColor(getResources().getColor(R.color.VERDE));
                break;
            case "C":
                alt3.setTextColor(getResources().getColor(R.color.VERDE));
                break;
            case "D":
                alt4.setTextColor(getResources().getColor(R.color.VERDE));
                break;
            case "E":
                alt5.setTextColor(getResources().getColor(R.color.VERDE));
                break;
        }
        if(!acertou){
            switch (alternativa){
                case "A":
                    alt1.setTextColor(getResources().getColor(R.color.VERMEIO));
                    break;
                case "B":
                    alt2.setTextColor(getResources().getColor(R.color.VERMEIO));
                    break;
                case "C":
                    alt3.setTextColor(getResources().getColor(R.color.VERMEIO));
                    break;
                case "D":
                    alt4.setTextColor(getResources().getColor(R.color.VERMEIO));
                    break;
                case "E":
                    alt5.setTextColor(getResources().getColor(R.color.VERMEIO));
                    break;
            }
            Muleta.usuario2.setPraticasFeitas(Muleta.usuario2.getPraticasFeitas()+1);
            explicacao.setVisibility(View.VISIBLE);
            resposta.setText(questao.resposta);
            resposta.setVisibility(View.VISIBLE);
        }
    }
}