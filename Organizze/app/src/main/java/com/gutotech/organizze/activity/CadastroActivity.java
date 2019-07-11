package com.gutotech.organizze.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.gutotech.organizze.R;
import com.gutotech.organizze.config.ConfiguracaoFirebase;
import com.gutotech.organizze.helper.Base64Custom;
import com.gutotech.organizze.model.Usuario;

public class CadastroActivity extends AppCompatActivity {
    private EditText campoNome, campoEmail, campoSenha;
    private Button botaoCadastrar;

    private FirebaseAuth auth;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        campoNome = findViewById(R.id.nomeEdit);
        campoEmail = findViewById(R.id.emailEdit);
        campoSenha = findViewById(R.id.senhaEdit);
        botaoCadastrar = findViewById(R.id.cadastrarButton);
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = campoNome.getText().toString();
                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();

                if (!isFieldsEmpty(nome, email, senha)) {
                    usuario = new Usuario(nome, email, senha);

                    cadastrarUsuario();
                }
            }
        });

    }

    private void cadastrarUsuario() {
        auth = ConfiguracaoFirebase.getFirebaseAuth();
        auth.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String idUsuario = Base64Custom.codificar(usuario.getEmail());
                    usuario.setId(idUsuario);
                    usuario.salvar();
                    finish();
                }else {
                    String message;

                    try {
                        throw task.getException();

                    } catch (FirebaseAuthWeakPasswordException e) {
                        message = "Digite uma senha mais forte";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        message = "Por favor, digite um email válido";
                    } catch (FirebaseAuthUserCollisionException e) {
                        message = "Conta já cadastrada";
                    } catch (Exception e) {
                        message = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private boolean isFieldsEmpty(String nome, String email, String senha) {
        if (nome.isEmpty()) {
            campoNome.setError("Este campo é obrigatório");
            return true;
        }

        if (email.isEmpty()) {
            campoEmail.setError("Este campo é obrigatório");
            return true;
        }

        if (senha.isEmpty()) {
            campoSenha.setError("Este campo é obrigatório");
            return true;
        }

        return false;
    }
}
