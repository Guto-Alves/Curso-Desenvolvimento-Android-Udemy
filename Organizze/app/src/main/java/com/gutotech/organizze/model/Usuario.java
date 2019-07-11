package com.gutotech.organizze.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.gutotech.organizze.config.ConfiguracaoFirebase;

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private String senha;
    private double receitaTotal = 0.00;
    private double despesaTotal = 0.00;

    public Usuario() {
        this("", "", "");
    }

    public Usuario(String email, String senha) {
        this("", email, senha);
    }

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public void salvar(){
        DatabaseReference reference = ConfiguracaoFirebase.getFirebaseDatabase();
        reference.child("usuarios")
                .child(id)
                .setValue(this);
    }

    public double getReceitaTotal() {
        return receitaTotal;
    }

    public void setReceitaTotal(double receitaTotal) {
        this.receitaTotal = receitaTotal;
    }

    public double getDespesaTotal() {
        return despesaTotal;
    }

    public void setDespesaTotal(double despesaTotal) {
        this.despesaTotal = despesaTotal;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
