package com.gutotech.whatsapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gutotech.whatsapp.R;
import com.gutotech.whatsapp.helper.UsuarioFirebase;
import com.gutotech.whatsapp.model.Mensagem;
import com.gutotech.whatsapp.model.Usuario;

import java.util.List;

public class MensagensAdapter extends RecyclerView.Adapter<MensagensAdapter.MyViewHolder> {
    private List<Mensagem> list;
    private Context context;

    private static final int TIPO_REMETENTE = 0;
    private static final int TIPO_DESTINATARIO = 1;

    public MensagensAdapter(List<Mensagem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View item = null;

        if (viewType == TIPO_REMETENTE)
            item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_mensagem_remetente, viewGroup, false);
        else if (viewType == TIPO_DESTINATARIO)
            item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_mensagem_destinatario, viewGroup, false);

        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Mensagem mensagem = list.get(i);
        String msg = mensagem.getMensagem();
        String imagem = mensagem.getImagem();

        if (imagem != null) {
            Glide.with(context).load(Uri.parse(imagem)).into(myViewHolder.imagem);
            myViewHolder.mensagem.setVisibility(View.GONE);
        } else {
            myViewHolder.mensagem.setText(msg);
            myViewHolder.imagem.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        Mensagem mensagem = list.get(position);

        String idUsuario = UsuarioFirebase.getIdentificador();

        if (idUsuario.equals(mensagem.getIdUsuario()))
            return TIPO_REMETENTE;

        return TIPO_DESTINATARIO;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mensagem;
        private ImageView imagem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mensagem = itemView.findViewById(R.id.textMensagemTexto);
            imagem = itemView.findViewById(R.id.imageMensagemFoto);
        }
    }

}
