package com.gutotech.whatsapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gutotech.whatsapp.R;
import com.gutotech.whatsapp.adapter.MensagensAdapter;
import com.gutotech.whatsapp.config.ConfigFirebase;
import com.gutotech.whatsapp.helper.Base64Custom;
import com.gutotech.whatsapp.helper.UsuarioFirebase;
import com.gutotech.whatsapp.model.Conversa;
import com.gutotech.whatsapp.model.Mensagem;
import com.gutotech.whatsapp.model.Usuario;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    private TextView textViewNome;
    private CircleImageView circleImageViewFoto;

    private EditText editMensagem;

    private ImageView imageCamera;
    private static final int SELECAO_CAMERA = 100;

    private RecyclerView recyclerMensagens;
    private MensagensAdapter adapter;
    private List<Mensagem> mensagens = new ArrayList<>();

    private Usuario usuarioDestinatario;

    private String idUsuarioRemetente;
    private String idUsuarioDestinatario;

    private DatabaseReference database;
    private DatabaseReference mensagensRef;
    private ChildEventListener childEventListenerMensagens;

    private StorageReference storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewNome = findViewById(R.id.textViewNomeChat);
        circleImageViewFoto = findViewById(R.id.circleImageFotoChat);
        editMensagem = findViewById(R.id.editMensagem);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            usuarioDestinatario = (Usuario) bundle.getSerializable("chatContato");

            textViewNome.setText(usuarioDestinatario.getNome());

            String foto = usuarioDestinatario.getFoto();
            if (foto != null) {
                Uri uri = Uri.parse(foto);
                Glide.with(ChatActivity.this).load(uri).into(circleImageViewFoto);
            } else
                circleImageViewFoto.setImageResource(R.drawable.padrao);
        }

        idUsuarioRemetente = UsuarioFirebase.getIdentificador();
        idUsuarioDestinatario = Base64Custom.codificar(usuarioDestinatario.getEmail());

        recyclerMensagens = findViewById(R.id.recyclerMensagens);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerMensagens.setLayoutManager(layoutManager);
        recyclerMensagens.setHasFixedSize(true);
        adapter = new MensagensAdapter(mensagens, getApplicationContext());
        recyclerMensagens.setAdapter(adapter);

        database = ConfigFirebase.getDatabase();
        mensagensRef = database.child("mensagens")
                .child(idUsuarioRemetente)
                .child(idUsuarioDestinatario);

        imageCamera = findViewById(R.id.imageCamera);
        imageCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null)
                    startActivityForResult(intent, SELECAO_CAMERA);
            }
        });

        storage = ConfigFirebase.getStorage();

        FloatingActionButton floatingActionButton = findViewById(R.id.fabEnviarMensagem);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarMensagem();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bitmap imagem = null;

            try {
                if (requestCode == SELECAO_CAMERA)
                    imagem = (Bitmap) data.getExtras().get("data");

                if (imagem != null) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    byte[] dadosImagem = baos.toByteArray();

                    String nomeImagem = UUID.randomUUID().toString();

                    final StorageReference imagemRef = storage.child("imagens")
                            .child("fotos")
                            .child(idUsuarioRemetente)
                            .child(nomeImagem);

                    UploadTask uploadTask = imagemRef.putBytes(dadosImagem);
                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            // Continua a tarefa para pegar a URL.
                            return imagemRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                String downloadUrl = downloadUri.toString();

                                Mensagem mensagem = new Mensagem();
                                mensagem.setIdUsuario(idUsuarioRemetente);
                                mensagem.setMensagem("imagem.jpg");
                                mensagem.setImagem(downloadUrl);

                                salvarMensagem(idUsuarioRemetente, idUsuarioDestinatario, mensagem);
                                salvarMensagem(idUsuarioDestinatario, idUsuarioRemetente, mensagem);

                                Toast.makeText(ChatActivity.this, "Sucesso ao enviar imagem", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ChatActivity.this, "Erro ao enviar imagem: " + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void enviarMensagem() {
        String textoMensagem = editMensagem.getText().toString();

        if (!textoMensagem.isEmpty()) {
            Mensagem mensagem = new Mensagem();
            mensagem.setIdUsuario(idUsuarioRemetente);
            mensagem.setMensagem(textoMensagem);

            salvarMensagem(idUsuarioRemetente, idUsuarioDestinatario, mensagem);
            salvarMensagem(idUsuarioDestinatario, idUsuarioRemetente, mensagem);

            editMensagem.setText("");

            salvarConversa(mensagem);

        } else
            Toast.makeText(ChatActivity.this, "Digite uma mensagem para enviar!", Toast.LENGTH_SHORT).show();
    }

    private void salvarConversa(Mensagem mensagem) {
        Conversa conversaRemetente = new Conversa();
        conversaRemetente.setIdRemetente(idUsuarioRemetente);
        conversaRemetente.setIdDestinatario(idUsuarioDestinatario);
        conversaRemetente.setUltimaMensagem(mensagem.getMensagem());
        conversaRemetente.setUsuarioExibicao(usuarioDestinatario);
        conversaRemetente.salvar();
    }

    private void salvarMensagem(String idRemetente, String idDestinatario, Mensagem mensagem) {
        DatabaseReference databaseReference = ConfigFirebase.getDatabase();
        DatabaseReference mensagemRef = databaseReference.child("mensagens");

        mensagemRef.child(idRemetente)
                .child(idDestinatario)
                .push()
                .setValue(mensagem);
    }

    private void recuperarMensagem() {
        childEventListenerMensagens = mensagensRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Mensagem mensagem = dataSnapshot.getValue(Mensagem.class);
                mensagens.add(mensagem);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarMensagem();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mensagensRef.removeEventListener(childEventListenerMensagens);
        mensagens.clear();
    }
}
