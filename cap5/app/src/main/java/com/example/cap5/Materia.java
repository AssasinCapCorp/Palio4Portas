package com.example.cap5;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Materia {
    String nomeDaMateria;
    ArrayList<Blocos> listaBlocos;

    public Materia(String nomeDaMateria, ArrayList<Blocos> listaBlocos) {
        this.nomeDaMateria = nomeDaMateria;
        this.listaBlocos = listaBlocos;
    }

    public String getNomeDaMateria() {
        return nomeDaMateria;
    }

    public void setNomeDaMateria(String nomeDaMateria) {
        this.nomeDaMateria = nomeDaMateria;
    }

    public ArrayList<Blocos> getListaBlocos() {
        return listaBlocos;
    }

    public void setListaBlocos(ArrayList<Blocos> listaBlocos) {
        this.listaBlocos = listaBlocos;
    }
}
