package com.example.calendariocapivara2.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.calendariocapivara2.R;
import com.example.calendariocapivara2.Utilitis.CentralTeste;
import com.example.calendariocapivara2.Utilitis.Constants;
import com.example.calendariocapivara2.Utilitis.PreferenceManager;
import com.example.calendariocapivara2.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {
    private PreferenceManager preferenceManager;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        preferenceManager = new PreferenceManager(this);
        if(preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)){
            startActivity(new Intent(this, CentralTeste.class));
        }

        //        int diaDoDomingo = LocalDateTime.now().getDayOfMonth()-4;
//        Calendar calendario = Calendar.getInstance();
//        calendario.set(LocalDateTime.now().getYear(),LocalDateTime.now().getMonthValue()-2,1);
//        Toast.makeText(this,(calendario.getActualMaximum(Calendar.DAY_OF_MONTH))+"",Toast.LENGTH_LONG).show();

//        String[] alt= new String[]{"suas densidades são maiores.", "o número de ramificações é maior.", "sua solubilidade no petróleo é maior.", "as forças intermoleculares são mais intensas.", "a cadeia carbônica é mais difícil de ser quebrada"};
//        ArrayList<String> alternativas = new ArrayList<>(Arrays.asList(alt));
//        Questao q = new Questao("(ENEM - 2019)7456264",alternativas,"D","Na torre de separação do petróleo quanto mais fundo mais quente é, devido a quantidade de carbono imensa se ligando");
//        q.salvarBd();
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
    public void print(String s){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
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