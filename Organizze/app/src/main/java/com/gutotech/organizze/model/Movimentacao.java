package com.gutotech.organizze.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.gutotech.organizze.config.ConfiguracaoFirebase;
import com.gutotech.organizze.helper.Base64Custom;
import com.gutotech.organizze.helper.DateCustom;

public class Movimentacao {
    private String data;
    private String categoria;
    private String descricao;
    private String tipo;
    private double valor;

    public Movimentacao() {
    }

    public  void salvar(){
        FirebaseAuth auth = ConfiguracaoFirebase.getFirebaseAuth();
        String idUsuario = Base64Custom.codificar(auth.getCurrentUser().getEmail());

        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebaseDatabase();
        databaseReference.child("movimentacao")
                .child(idUsuario)
                .child(DateCustom.mesAno(getData()))
                .push()
                .setValue(this);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
