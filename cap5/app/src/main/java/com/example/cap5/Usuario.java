package com.example.cap5;

    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;

    public class Usuario {
        String nome;
        String senha;
        int blocosEstudados;
        int praticasFeitas;

        public Usuario() {
        }

        public Usuario(String nome, String senha) {
            this.nome = nome;
            this.senha = senha;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }

        public int getBlocosEstudados() {
            return blocosEstudados;
        }

        public void setBlocosEstudados(int blocosEstudados) {
            this.blocosEstudados = blocosEstudados;
        }

        public int getPraticasFeitas() {
            return praticasFeitas;
        }

        public void setPraticasFeitas(int praticasFeitas) {
            this.praticasFeitas = praticasFeitas;
        }

        public void salvar_bd() {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            ref.child("Usuario").child(nome).setValue(this);
        }
    }

