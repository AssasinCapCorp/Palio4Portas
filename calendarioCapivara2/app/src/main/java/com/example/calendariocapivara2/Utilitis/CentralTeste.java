package com.example.calendariocapivara2.Utilitis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.calendariocapivara2.R;
import com.example.calendariocapivara2.fragments.CalendarioFragment;
import com.example.calendariocapivara2.fragments.HomeFragment;
import com.example.calendariocapivara2.fragments.MateriasFragment;
import com.example.calendariocapivara2.fragments.PerfilFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;

public class CentralTeste extends AppCompatActivity {
    public static boolean ativi;
BottomNavigationView bottonNavigation;
HomeFragment homeFragment = new HomeFragment();
MateriasFragment materiasFragment = new MateriasFragment();
PerfilFragment perfilFragment = new PerfilFragment();
CalendarioFragment calendarioFragment = new CalendarioFragment();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this,MateriasFragment.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_central_teste);
        bottonNavigation = findViewById(R.id.bottonnavigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
        bottonNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                        return true;
                    case R.id.perfil:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,perfilFragment).commit();
                        return true;
                    case R.id.materias:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,materiasFragment ).commit();
                        return true;
                    case R.id.calendario:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,calendarioFragment).commit();
                        return true;

                }
                return false;
            }
        });

        }}
