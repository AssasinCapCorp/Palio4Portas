package com.example.calendariocapivara2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class TelaProgramarSemana extends AppCompatActivity {
    private  int s, t, qa, qi, sx, sb, d;
    private ImageView sab,dom;
     private ImageButton seg,ter,qua,qui,sex;
     static Usuario2 usuario;
      TextView st,tt,qat,qit,sxt,sbt,dt;
    Button boton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_programar_semana);
        st = findViewById(R.id.segt);
        tt = findViewById(R.id.tert);
        qat = findViewById(R.id.quat);
        qit = findViewById(R.id.quit);
        sxt = findViewById(R.id.sext);
        sbt=findViewById(R.id.sabt);
        dt = findViewById(R.id.domt);
        boton = findViewById(R.id.botaoSemanal);
        boton.setOnClickListener(view ->{
            registrar();
        });
        //--------------------------------------------------------\\
        st.setText("fazer nada");
        tt.setText("fazer nada");
        qat.setText("fazer nada");
        qit.setText("fazer nada");
        sxt.setText("fazer nada");
        sbt.setText("fazer nada");
        dt.setText("fazer nada");
        //---------------------------------\\
        sb =0;
        sab = findViewById(R.id.sabadoCal);
        sab.setImageResource(R.drawable.numseicarai);
        sab.setOnClickListener(view ->{
            sabTrocarImagem();
        });
        d = 0;
        dom = findViewById(R.id.domingoCal);
        dom.setImageResource(R.drawable.numseicarai);
        dom.setOnClickListener(view ->{
            domTrocarImagem();
        });
        s = 0;
        seg =findViewById(R.id.segundoCal);
        seg.setImageResource(R.drawable.numseicarai);
        seg.setOnClickListener(view ->{
            segTrocarImagem();
        });
        t = 0;
        ter=findViewById(R.id.tercaCal);
        ter.setImageResource(R.drawable.numseicarai);
        ter.setOnClickListener(view ->{
            terTrocarImagem();
        });
        qa = 0;
        qua=findViewById(R.id.quartaCal);
        qua.setImageResource(R.drawable.numseicarai);
        qua.setOnClickListener(view ->{
            quartaTrocarImagem();
        });
        qi = 0;
        qui=findViewById(R.id.quintaCal);
        qui.setImageResource(R.drawable.numseicarai);
        qui.setOnClickListener(view ->{
            quintaTrocarImagem();
        });
        sx = 0;
        sex=findViewById(R.id.sextaCal);
        sex.setImageResource(R.drawable.numseicarai);
        sex.setOnClickListener(view ->{
            sexTrocarImagem();
        });

    }

    public void segTrocarImagem() {
        s++;
        switch (s) {
            case 5:
                seg.setImageResource(R.drawable.numseicarai);
                st.setText("fazer nada");
                s = 0;
                break;
            case 1:
                seg.setImageResource(R.drawable.matematica);
                st.setText("Matemática");
                break;
            case 2:
                seg.setImageResource(R.drawable.alquimiacaralho);
                st.setText("C.Nat");
                break;
            case 3:
                seg.setImageResource(R.drawable.jotarolendo);
                st.setText("Linguagens");
                break;
            case 4:
                seg.setImageResource(R.drawable.humanas_rum_igual_a_death_little_corno);
                st.setText("C.Hum");

        }
    }

    public void terTrocarImagem() {
        t++;
        switch (t) {
            case 5:
                ter.setImageResource(R.drawable.numseicarai);
                t = 0;
                tt.setText("fazer nada");
                break;
            case 1:
                ter.setImageResource(R.drawable.matematica);
                tt.setText("Matemática");
                break;
            case 2:
                ter.setImageResource(R.drawable.alquimiacaralho);
                tt.setText("C.Nat");
                break;
            case 3:
                ter.setImageResource(R.drawable.jotarolendo);
                tt.setText("Linguagens");
                break;
            case 4:
                ter.setImageResource(R.drawable.humanas_rum_igual_a_death_little_corno);
                tt.setText("C.Hum");
        }
    }

    public void quartaTrocarImagem() {
        qa++;
        switch (qa) {
            case 5:
                qua.setImageResource(R.drawable.numseicarai);
                qa = 0;
                qat.setText("fazer nada");
                break;
            case 1:
                qua.setImageResource(R.drawable.matematica);
                qat.setText("Matemática");
                break;
            case 2:
                qua.setImageResource(R.drawable.alquimiacaralho);
                qat.setText("C.Nat");
                break;
            case 3:
                qua.setImageResource(R.drawable.jotarolendo);
                qat.setText("Linguagens");
                break;
            case 4:
                qua.setImageResource(R.drawable.humanas_rum_igual_a_death_little_corno);
                qat.setText("C.Hum");
        }
    }

    public void quintaTrocarImagem() {
        qi++;
        switch (qi) {
            case 5:
                qui.setImageResource(R.drawable.numseicarai);
                qi = 0;
                qit.setText("fazer nada");
                break;
            case 1:
                qui.setImageResource(R.drawable.matematica);
                qit.setText("Matemática");
                break;
            case 2:
                qui.setImageResource(R.drawable.alquimiacaralho);
                qit.setText("C.Nat");
                break;
            case 3:
                qui.setImageResource(R.drawable.jotarolendo);
                qit.setText("Linguagens");
                break;
            case 4:
                qui.setImageResource(R.drawable.humanas_rum_igual_a_death_little_corno);
                qit.setText("C.Hum");
        }
    }
//
    public void sexTrocarImagem() {
        sx++;
        switch (sx) {
            case 5:
                sex.setImageResource(R.drawable.numseicarai);
                sx = 0;
                sxt.setText("fazer nada");
                break;
            case 1:
                sex.setImageResource(R.drawable.matematica);
                sxt.setText("Matemática");
                break;
            case 2:
                sex.setImageResource(R.drawable.alquimiacaralho);
                sxt.setText("C.Nat");
                break;
            case 3:
                sex.setImageResource(R.drawable.jotarolendo);
                sxt.setText("Linguagens");
                break;
            case 4:
                sex.setImageResource(R.drawable.humanas_rum_igual_a_death_little_corno);
                sxt.setText("C.Hum");
        }
    }

    public void sabTrocarImagem() {
        sb++;
        switch (sb) {
            case 5:
                sab.setImageResource(R.drawable.numseicarai);
                sb = 0;
                sbt.setText("fazer nada");
                break;
            case 1:
                sab.setImageResource(R.drawable.matematica);
                sbt.setText("Matemática");
                break;
            case 2:
                sab.setImageResource(R.drawable.alquimiacaralho);
                sbt.setText("C.Nat");
                break;
            case 3:
                sab.setImageResource(R.drawable.jotarolendo);
                sbt.setText("Linguagens");
                break;
            case 4:
                sab.setImageResource(R.drawable.humanas_rum_igual_a_death_little_corno);
                sbt.setText("C.Hum");
        }
    }

    public void domTrocarImagem() {
        d++;
        switch (d) {
            case 5:
                dom.setImageResource(R.drawable.numseicarai);
                d = 0;
                dt.setText("fazer nada");
                break;
            case 1:
                dom.setImageResource(R.drawable.matematica);
                dt.setText("Matemática");
                break;
            case 2:
                dom.setImageResource(R.drawable.alquimiacaralho);
                dt.setText("C.Nat");
                break;
            case 3:
                dom.setImageResource(R.drawable.jotarolendo);
                dt.setText("Linguagens");
                break;
            case 4:
                dom.setImageResource(R.drawable.humanas_rum_igual_a_death_little_corno);
                dt.setText("C.Hum");
        }
    }
    public void registrar(){
//      String nomedoUser, int segunda, int terca, int quarta, int quinta, int sexta, int sabado, int domingo
        CalendarioSemanal ca = new CalendarioSemanal(usuario.getNome(), s,t,qa,qi,sx,sb,d);
        Muleta.cs=ca;
        Muleta.usuario2 = usuario;
        ca.salvar_bd();
        usuario.salvar_bd();
        Intent i = new Intent(this,TelaMaterias.class);
        startActivity(i);
    }
}
