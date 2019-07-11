
package com.gutotech.organizze.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.organizze.R;
import com.gutotech.organizze.config.ConfiguracaoFirebase;
import com.gutotech.organizze.helper.Base64Custom;
import com.gutotech.organizze.helper.DateCustom;
import com.gutotech.organizze.model.Movimentacao;
import com.gutotech.organizze.model.Usuario;

public class DespesasActivity extends AppCompatActivity {
    private EditText campoValor;
    private TextInputEditText campoData, campoCategoria, campoDescricao;
    private Movimentacao movimentacao;

    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAuth();

    private double despesaTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        campoValor = findViewById(R.id.valorEdit);
        campoData = findViewById(R.id.dataEdit);
        campoCategoria = findViewById(R.id.categoriaEdit);
        campoDescricao = findViewById(R.id.descricaoEdit);

        campoData.setText(DateCustom.dataAtual());

        recuperarDespesaTotal();
    }

    public void salvarDespesa(View view) {
        String textoValor = campoValor.getText().toString();
        String textoData = campoData.getText().toString();
        String textoCategoria = campoCategoria.getText().toString();
        String textoDescricao = campoDescricao.getText().toString();

        if (validarCamposDespesa(textoValor, textoData, textoCategoria, textoDescricao)) {
            double despesaGerada = Double.parseDouble(textoValor);
            atualizarDespesa(despesaTotal + despesaGerada);

            movimentacao = new Movimentacao();
            movimentacao.setValor(despesaGerada);
            movimentacao.setData(textoData);
            movimentacao.setCategoria(textoCategoria);
            movimentacao.setDescricao(textoDescricao);
            movimentacao.setTipo("d");


            movimentacao.salvar();

            finish();
        }
    }

    public boolean validarCamposDespesa(String valor, String data, String categoria, String descricao) {
        if (valor.isEmpty()) {
            campoValor.setError("Este campo é obrigatório");
            return false;
        }

        if (data.isEmpty()) {
            campoData.setError("Este campo é obrigatório");
            return false;
        }

        if (categoria.isEmpty()) {
            campoCategoria.setError("Este campo é obrigatório");
            return false;
        }

        if (descricao.isEmpty()) {
            campoDescricao.setError("Este campo é obrigatório");
            return false;
        }

        return true;
    }

    public void recuperarDespesaTotal() {
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificar(emailUsuario);

        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                despesaTotal = usuario.getDespesaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void atualizarDespesa(double despesa) {
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificar(emailUsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.child("despesaTotal").setValue(despesa);
    }
}
