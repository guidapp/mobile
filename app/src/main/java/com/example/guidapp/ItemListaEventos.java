package com.example.guidapp;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.guidapp.controllers.EventoController;

import java.util.Calendar;

public class ItemListaEventos extends ConstraintLayout {
    private Evento evento;
    private ImageView imagem;

    public static final int marginTexto = 16;
    public static final int tamImagem = 400;
    public static final int tamTexto = 80;

    public ItemListaEventos(Context context, Evento evento) {
        super(context);

        this.evento = evento;

        this.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DescricaoEvento.class);
                intent.putExtra("idEvento", getEvento().getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });
    }

    public void desenhar() {
        addImagem();
        addTextos();
    }

    public void addImagem() {
        ConstraintSet constSet = new ConstraintSet();
        constSet.clone(this);

        imagem = new ImageView(getContext());
        imagem.setId(-1050000 - evento.getId());
        imagem.setImageResource(R.drawable.clone_chopp_quadrado);
        this.addView(imagem);

        constSet.connect(imagem.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
        constSet.connect(imagem.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);

        constSet.constrainHeight(imagem.getId(), tamImagem);
        constSet.constrainWidth(imagem.getId(), tamImagem);
        constSet.applyTo(this);
    }

    public void addTextos() {
        ConstraintSet constSet = new ConstraintSet();
        constSet.clone(this);


        // NOME DO EVENTO
        TextView tvNomeEvento = new TextView(getContext());
        tvNomeEvento.setId(-10600000 - evento.getId());
        tvNomeEvento.setText(evento.getNome());
        tvNomeEvento.setTextSize(20);
        tvNomeEvento.setTextColor(getResources().getColor(android.R.color.black));
        this.addView(tvNomeEvento);


        // ENDERECO DO EVENTO
        TextView tvEnderecoEvento = new TextView(getContext());
        tvEnderecoEvento.setId(-10700000 - evento.getId());
        tvEnderecoEvento.setText(evento.getEndereco());
        tvEnderecoEvento.setTextSize(20);
        tvEnderecoEvento.setTextColor(getResources().getColor(android.R.color.black));
        this.addView(tvEnderecoEvento);


        // HORA DO EVENTO
        TextView tvHoraEvento = new TextView(getContext());
        tvHoraEvento.setId(-10800000 - evento.getId());
        tvHoraEvento.setText(evento.getDataHoraFormatada());
        tvHoraEvento.setTextSize(20);
        tvHoraEvento.setTextColor(getResources().getColor(android.R.color.black));
        this.addView(tvHoraEvento);


        // AVALIACAO
        TextView tvAvaliacao = new TextView(getContext());
        tvAvaliacao.setId(-10900000 - evento.getId());
        tvAvaliacao.setText(evento.getAvaliacao() + "/5");
        tvAvaliacao.setTextSize(20);
        tvAvaliacao.setTextColor(getResources().getColor(android.R.color.black));
        this.addView(tvAvaliacao);


        // ESTRELA DE AVALIACAO
        ImageView estrela = new ImageView(getContext());
        estrela.setId(-1001000 - evento.getId());
        estrela.setImageResource(R.drawable.star);
        this.addView(estrela);


        // CONSTRAINTS NOME DO EVENTO
        constSet.connect(tvNomeEvento.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, marginTexto);
        constSet.connect(tvNomeEvento.getId(), ConstraintSet.LEFT, imagem.getId(), ConstraintSet.RIGHT, marginTexto);
        constSet.constrainHeight(tvNomeEvento.getId(), tamTexto);


        // CONSTRAINTS ENDERECO DO EVENTO
        constSet.connect(tvEnderecoEvento.getId(), ConstraintSet.TOP, tvNomeEvento.getId(), ConstraintSet.BOTTOM, marginTexto);
        constSet.connect(tvEnderecoEvento.getId(), ConstraintSet.LEFT, imagem.getId(), ConstraintSet.RIGHT, marginTexto);
        constSet.constrainHeight(tvEnderecoEvento.getId(), tamTexto);


        // CONSTRAINTS HORA DO EVENTO
        constSet.connect(tvHoraEvento.getId(), ConstraintSet.TOP, tvEnderecoEvento.getId(), ConstraintSet.BOTTOM, marginTexto);
        constSet.connect(tvHoraEvento.getId(), ConstraintSet.LEFT, imagem.getId(), ConstraintSet.RIGHT, marginTexto);
        constSet.constrainHeight(tvHoraEvento.getId(), tamTexto);


        // CONSTRAINTS AVALIACAO
        constSet.connect(tvAvaliacao.getId(), ConstraintSet.TOP, tvHoraEvento.getId(), ConstraintSet.BOTTOM, marginTexto);
        constSet.connect(tvAvaliacao.getId(), ConstraintSet.LEFT, imagem.getId(), ConstraintSet.RIGHT, marginTexto);
        constSet.constrainHeight(tvAvaliacao.getId(), tamTexto);


        // CONSTRAINTS ESTRELA AVALIACAO
        constSet.connect(estrela.getId(), ConstraintSet.LEFT, tvAvaliacao.getId(), ConstraintSet.RIGHT, 0);
        constSet.connect(estrela.getId(), ConstraintSet.TOP, tvAvaliacao.getId(), ConstraintSet.TOP, 0);
        constSet.connect(estrela.getId(), ConstraintSet.BOTTOM, tvAvaliacao.getId(), ConstraintSet.BOTTOM, 0);
        constSet.constrainHeight(estrela.getId(), 80);
        constSet.constrainWidth(estrela.getId(), 120);


        // APLICAR CONSTRAINTS
        constSet.applyTo(this);
    }

    public Evento getEvento() {
        return evento;
    }

    public static int calcularAltura() {
        return tamImagem;
    }
}
