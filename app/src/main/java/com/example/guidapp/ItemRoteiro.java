package com.example.guidapp;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class ItemRoteiro extends ConstraintLayout {
    private Evento evento;
    private ImageView imagem;

    private ConstraintLayout constraintLayout;

    public static final int marginTexto = 16;
    public static final int tamImagem = 350;
    public static final int tamTexto = 100;

    public ItemRoteiro(Context context, Evento evento) {
        super(context);

        this.evento = evento;

        this.constraintLayout = new ConstraintLayout(context);
    }

    public void desenhar(boolean eventoPar) {
        addImagem(eventoPar);
        addTextos(eventoPar);
    }

    private void addTextos(boolean eventoPar) {
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
        tvHoraEvento.setText(evento.getEndereco());
        tvHoraEvento.setTextSize(20);
        tvHoraEvento.setTextColor(getResources().getColor(android.R.color.black));
        tvHoraEvento.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));

        LayoutParams clpHoraEvento = new ConstraintLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        tvHoraEvento.setLayoutParams(clpHoraEvento);

        this.addView(tvHoraEvento);


        // CONSTRAINTS NOME DO EVENTO
        constSet.connect(tvNomeEvento.getId(), ConstraintSet.TOP, imagem.getId(), ConstraintSet.TOP, 120);
        constSet.connect(tvNomeEvento.getId(), ConstraintSet.BOTTOM, tvEnderecoEvento.getId(), ConstraintSet.TOP, 0);
        if(eventoPar)
            constSet.connect(tvNomeEvento.getId(), ConstraintSet.RIGHT, imagem.getId(), ConstraintSet.LEFT, marginTexto);
        else
            constSet.connect(tvNomeEvento.getId(), ConstraintSet.LEFT, imagem.getId(), ConstraintSet.RIGHT, marginTexto);
        constSet.constrainHeight(tvNomeEvento.getId(), tamTexto);


        // CONSTRAINTS ENDERECO DO EVENTO
        constSet.connect(tvEnderecoEvento.getId(), ConstraintSet.TOP, tvNomeEvento.getId(), ConstraintSet.BOTTOM, 0);
        constSet.connect(tvEnderecoEvento.getId(), ConstraintSet.BOTTOM, imagem.getId(), ConstraintSet.BOTTOM, 120);
        if(eventoPar)
            constSet.connect(tvEnderecoEvento.getId(), ConstraintSet.RIGHT, imagem.getId(), ConstraintSet.LEFT, marginTexto);
        else
            constSet.connect(tvEnderecoEvento.getId(), ConstraintSet.LEFT, imagem.getId(), ConstraintSet.RIGHT, marginTexto);
        constSet.constrainHeight(tvEnderecoEvento.getId(), tamTexto);


        // CONSTRAINTS HORA DO EVENTO
        constSet.connect(tvHoraEvento.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
        constSet.connect(tvHoraEvento.getId(), ConstraintSet.RIGHT, imagem.getId(), ConstraintSet.RIGHT, marginTexto);
        constSet.connect(tvHoraEvento.getId(), ConstraintSet.LEFT, imagem.getId(), ConstraintSet.LEFT, marginTexto);
        constSet.constrainHeight(tvHoraEvento.getId(), tamTexto);
//        constSet.constrainPercentWidth(tvHoraEvento.getId(), 50);


        // CONSTRAINT VERTICAL DA IMAGEM
        constSet.connect(imagem.getId(), ConstraintSet.TOP, tvHoraEvento.getId(), ConstraintSet.BOTTOM, 0);


        // APLICAR CONSTRAINTS
        constSet.applyTo(this);
    }

    private void addImagem(boolean eventoPar) {
        ConstraintSet constSet = new ConstraintSet();
        constSet.clone(this);

        imagem = new ImageView(getContext());
        imagem.setId(-1050000 - evento.getId());
        imagem.setImageResource(R.drawable.clone_chopp_quadrado);
        this.addView(imagem);

//        constSet.connect(imagem.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);

        if(eventoPar)
            constSet.connect(imagem.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0);
        else
            constSet.connect(imagem.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);

        constSet.constrainHeight(imagem.getId(), tamImagem);
        constSet.constrainWidth(imagem.getId(), tamImagem);
        constSet.applyTo(this);

        imagem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((Roteiro) getContext()).opcoesEvento(getItemRoteiro());
            }
        });
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public ItemRoteiro getItemRoteiro() {
        return this;
    }

    public static int calcularAltura() {
        return tamImagem + tamTexto;
    }
}
