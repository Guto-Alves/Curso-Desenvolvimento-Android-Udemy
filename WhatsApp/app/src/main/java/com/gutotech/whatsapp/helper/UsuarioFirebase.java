package com.gutotech.whatsapp.helper;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.gutotech.whatsapp.config.ConfigFirebase;
import com.gutotech.whatsapp.model.Usuario;

public class UsuarioFirebase {

    public static String getIdentificador() {
        FirebaseAuth auth = ConfigFirebase.getAuth();
        String email = auth.getCurrentUser().getEmail();
        String identificadorUsuario = Base64Custom.codificar(email);

        return identificadorUsuario;
    }

    public static FirebaseUser getUsuarioAtual() {
        FirebaseAuth auth = ConfigFirebase.getAuth();
        return auth.getCurrentUser();
    }

    public static boolean atualizarFotoUsuario(Uri uri) {
        try {
            FirebaseUser user = getUsuarioAtual();
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(uri)
                    .build();

            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!task.isSuccessful())
                        Log.d("Perfil", "Erro ao atualizar foto de perfil.");
                }
            });

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean atualizarNomeUsuario(String nome) {
        try {
            FirebaseUser user = getUsuarioAtual();
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(nome)
                    .build();

            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!task.isSuccessful())
                        Log.d("Perfil", "Erro ao atualizar nome de perfil.");
                }
            });

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Usuario getDadosUsuarioLogado() {
        FirebaseUser firebaseUser = getUsuarioAtual();

        Usuario usuario = new Usuario();
        usuario.setEmail(firebaseUser.getEmail());
        usuario.setNome(firebaseUser.getDisplayName());

        if (firebaseUser.getPhotoUrl() == null)
            usuario.setFoto("");
        else
            usuario.setFoto(firebaseUser.getPhotoUrl().toString());

        return usuario;
    }

}
