package com.example.calendariocapivara2.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calendariocapivara2.Activity.MainActivity;
import com.example.calendariocapivara2.Utilitis.Constants;
import com.example.calendariocapivara2.R;
import com.example.calendariocapivara2.Utilitis.PreferenceManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;


public class PerfilFragment extends Fragment {
    TextView nomeDoUsuario,blocosFeitos,pratiqueFeitos,diasEstudados;
    Button btFoto;
    ImageView foto,sair;
    Uri mselUri;
    StorageReference storageRef;
    PreferenceManager preferenceManager;
    @SuppressLint("MissingInflatedId")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.fragment_perfil, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        preferenceManager = new PreferenceManager(getContext());
        sair = v.findViewById(R.id.sairConta);
        nomeDoUsuario = v.findViewById(R.id.nomeDaQuestao);
        blocosFeitos = v.findViewById(R.id.qtgBlocosEstudados);
        pratiqueFeitos = v.findViewById(R.id.praticarFeitos);
        diasEstudados = v.findViewById(R.id.diasEstudados);
        btFoto = v.findViewById(R.id.botonFoto);
        foto = v.findViewById(R.id.foto);
        //----
        sair.setOnClickListener(v1 -> {
            preferenceManager.clear();
            preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,false);
            Intent i = new Intent(getContext(),MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        });
        nomeDoUsuario.setText(preferenceManager.getString(Constants.KEY_NAME,null));
        try{pratiqueFeitos.setText(preferenceManager.getInt(Constants.KEY_QUANTIDADE_DE_PRATICAS_FEITAS)+"");}
        catch (NullPointerException e){ preferenceManager.putInt(Constants.KEY_QUANTIDADE_DE_PRATICAS_FEITAS,0);
        blocosFeitos.setText("0");
        }
try {
    String[] c = preferenceManager.getString(Constants.KEY_TOPICOS_ESTUDADOS,null).split(",",99);
    blocosFeitos.setText(c.length+"");
}catch (NullPointerException e){preferenceManager.putString(Constants.KEY_TOPICOS_ESTUDADOS,"");blocosFeitos.setText("0");}

        diasEstudados.setText(((LocalDateTime.now().getDayOfYear()-Integer.parseInt(preferenceManager.getString(Constants.KEY_DIA_DE_INICIO,null)))+((LocalDateTime.now().getYear()-Integer.parseInt(preferenceManager.getString(Constants.KEY_ANO_DE_INICIO,null)))*365))+"");
        fotoUser();
        storageRef = FirebaseStorage.getInstance().getReference();
        btFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFoto();
            }
        });

        return v;
    }
    public void selectFoto(){
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i,0);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( data != null){
        if(requestCode == 0) {
            mselUri = data.getData();
            Bitmap bitmap = null;
            //------
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), mselUri);
                foto.setImageDrawable(new BitmapDrawable(bitmap));
                btFoto.setAlpha(0);
                babi(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                Toast.makeText(getContext(),"Acho q deu certo",Toast.LENGTH_LONG).show();
            }}).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"deu merda KKKK",Toast.LENGTH_LONG).show();
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

                    Toast.makeText(getContext(),"Imagem não pôde ser carregada",Toast.LENGTH_SHORT).show();
                }});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}