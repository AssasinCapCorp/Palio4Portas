package com.example.calendariocapivara2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TelaPerfil extends AppCompatActivity {
    TextView nomeDoUsuario,blocosFeitos,pratiqueFeitos,diasEstudados;
    Button btFoto;
    ImageView foto;
    Uri mselUri;
    StorageReference storageRef;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_perfil);
        getSupportActionBar().hide();
        nomeDoUsuario = findViewById(R.id.nomeDaQuestao);
        blocosFeitos = findViewById(R.id.qtgBlocosEstudados);
        pratiqueFeitos = findViewById(R.id.praticarFeitos);
        diasEstudados = findViewById(R.id.diasEstudados);
        btFoto = findViewById(R.id.botonFoto);
        foto = findViewById(R.id.foto);
        nomeDoUsuario.setText(Muleta.usuario2.getNome());
        blocosFeitos.setText(Muleta.usuario2.getBlocosEstudados()+"");
        pratiqueFeitos.setText(Muleta.usuario2.getPraticasFeitas()+"");
        diasEstudados.setText(((LocalDateTime.now().getDayOfYear()-Muleta.usuario2.getDiaInicio())+((LocalDateTime.now().getYear()-Muleta.usuario2.getAnoInicio())*365))+"");
        fotoUser();
        storageRef = FirebaseStorage.getInstance().getReference();
        btFoto.setOnClickListener(v -> {
        selectFoto();
        });
    }
    public void selectFoto(){
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            assert data != null;
            mselUri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),mselUri);
                foto.setImageDrawable(new BitmapDrawable(bitmap));
                btFoto.setAlpha(0);
                babi(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void babi(Bitmap bitmap){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
       // Bitmap bitmap = foto.getDrawingCache();
        bitmap.compress(Bitmap.CompressFormat.JPEG,90,bytes);
        byte bb[] = bytes.toByteArray();
        colocaNoFireBase(bb);
    }
    private void colocaNoFireBase(byte[] bb) {
        String nomeDoUser = nomeDoUsuario.getText().toString();
        String caminho = "/fotosUser/"+nomeDoUser+".jpg";
        StorageReference sr = storageRef.child(caminho);
        sr.putBytes(bb).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(TelaPerfil.this,"Acho q deu certo",Toast.LENGTH_LONG).show();
            }}).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TelaPerfil.this,"deu merda KKKK",Toast.LENGTH_LONG).show();
            }});
    }
    public void fotoUser(){
        storageRef = FirebaseStorage.getInstance().getReference().child("/fotosUser/"+nomeDoUsuario.getText().toString()+".jpg");
        try {
            File arq = File.createTempFile("foto","jpg");
            storageRef.getFile(arq).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Bitmap bit = BitmapFactory.decodeFile(arq.getAbsolutePath());
                foto.setImageBitmap(bit);
                btFoto.setAlpha(0);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(TelaPerfil.this,"Imagem não pôde ser carregada pq vc é muito gay",Toast.LENGTH_SHORT).show();
                }});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void home(View v){
        Intent i = new Intent(this,TelaAtividade.class);
        startActivity(i);
    }
    public void materia(View v){
        Intent i = new Intent(this,TelaMaterias.class);
        startActivity(i);
    }

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
}