package com.example.calendariocapivara2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        String[] alt= new String[]{"suas densidades são maiores.", "o número de ramificações é maior.", "sua solubilidade no petróleo é maior.", "as forças intermoleculares são mais intensas.", "a cadeia carbônica é mais difícil de ser quebrada"};
        ArrayList<String> alternativas = new ArrayList<>(Arrays.asList(alt));
        Questao q = new Questao("(ENEM - 2019)7456264",alternativas,"D","Na torre de separação do petróleo quanto mais fundo mais quente é, devido a quantidade de carbono imensa se ligando");
        q.salvarBd();
//
//        Intent i = new Intent(this, TelaPratica.class);
//        startActivity(i);

        //String nome, String senha, int diaInicio, int anoInicio, ArrayList<Calendario> coisasParaFazer
//
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Usuario");
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot d: snapshot.getChildren()){
//                    Usuario u = d.getValue(Usuario.class);
//
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


//        Intent i = new Intent(this,TelaCalendario.class);
//            startActivity(i);
        //Toast.makeText(this, LocalDateTime.now().getDayOfWeek()+"",Toast.LENGTH_LONG).show();
    }
    public void telaLogin(View v){
        Intent i = new Intent(this,TelaLogin.class);
        startActivity(i);
    }
    public void telaCadastro(View v){

        Intent i = new Intent(this,TelaCadastro.class);
        startActivity(i);
    }


}