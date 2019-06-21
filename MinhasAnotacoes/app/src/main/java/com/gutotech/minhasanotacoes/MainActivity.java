package com.gutotech.minhasanotacoes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText editAnotacao;
    private AnotacaoPref pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editAnotacao = findViewById(R.id.editAnotacao);

        pref = new AnotacaoPref(getApplicationContext());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String anotacao = editAnotacao.getText().toString();
                if (anotacao.isEmpty())
                    Snackbar.make(view, "Preencha a anotação!", Snackbar.LENGTH_LONG).show();
                else{
                    pref.salvarAnotacao(anotacao);
                    Snackbar.make(view, "Anotação salva com sucesso!", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        String anotacao = pref.getAnotacao();
        if (!anotacao.equals("")){
            editAnotacao.setText(anotacao);
        }
    }
}
