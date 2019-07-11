package com.gutotech.organizze.activity;

import android.content.Intent;
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
import com.gutotech.organizze.model.Usuario;

public class LoginActivity extends AppCompatActivity {
    private EditText campoEmail, campoSenha;
    private Button botaoEntrar;

    private Usuario usuario;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        campoEmail = findViewById(R.id.emailEditText);
        campoSenha = findViewById(R.id.senhaEditText);
        botaoEntrar = findViewById(R.id.entrarButton);
        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();

                if (!isFieldsEmpty(email, senha)) {
                    usuario = new Usuario(email, senha);
                    validarLogin();
                }
            }
        });
    }

    private void validarLogin() {
        auth = ConfiguracaoFirebase.getFirebaseAuth();
        auth.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    abrirTelaPrincipal();
                } else {
                    String message;

                    try {
                        throw task.getException();

                    } catch (FirebaseAuthInvalidUserException e) {
                        message = "Usuário não está cadastrado";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        message = "Email e senha não correspondem a um usuário cadastrado";
                    } catch (Exception e) {
                        message = "Erro ao fazer login: " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private boolean isFieldsEmpty(String email, String senha) {
        if (email.isEmpty()) {
            campoEmail.setError("Campo é obrigatório");
            return true;
        }

        if (senha.isEmpty()) {
            campoSenha.setError("Campo é obrigatório");
            return true;
        }

        return false;
    }

    public void abrirTelaPrincipal() {
        startActivity(new Intent(this, PrincipalActivity.class));
        finish();
    }
}
