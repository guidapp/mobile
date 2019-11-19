package com.example.guidapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Roteiro extends AppCompatActivity {

    private ConstraintLayout viewRoteiro;
    private FloatingActionButton btAddEvento;
    private ArrayList<ImageView> listaEventos;
    private ArrayList<ImageView> listaTracos;

    private int marginDefault = 40;
    private int tamImagem = 400;

    private Paint paint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roteiro);

        this.viewRoteiro = (ConstraintLayout) findViewById(R.id.viewRoteiro);
        this.btAddEvento = (FloatingActionButton) findViewById(R.id.btAddEvento);

        this.listaEventos = new ArrayList<>();
        this.listaTracos = new ArrayList<>();

        btAddEvento.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addEvento(v);
            }
        });
    }

    public void perfil (View v) {
        Intent intent = new Intent(this, UsuarioPerfil.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void areaVisitada (View v) {
        Intent intent = new Intent(this, AreaVisitada.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void mapa (View v) {
        Intent intent = new Intent(this, Mapa.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void listaEventos (View v) {
        Intent intent = new Intent(this, ListaEventos.class);
        startActivity(intent);
    }

    public void addEvento (View v) {
        this.desenharLinhaAnterior();
        this.addEspacoParaProximaLinha();
        this.addImagem();
    }

    public void removerEvento (View v) {
        ArrayList<ImageView> listaEventosAntiga = (ArrayList) listaEventos.clone();
        listaEventosAntiga.remove(v);

        this.listaEventos = new ArrayList<>();
        this.listaTracos = new ArrayList<>();

        viewRoteiro.removeAllViews();

        for (ImageView imagem : listaEventosAntiga) {
            addEvento(btAddEvento);
        }
    }

    public void opcoesEvento(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setItems(R.array.opcoes_evento_roteiro, new DialogEventoRoteiro(this, v, 1));

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    private void desenharLinhaAnterior() {
        if(listaTracos.size() > 0) {
            ImageView linha_anterior = listaTracos.get(listaTracos.size() - 1);

            if(listaTracos.size()%2 == 1) {
                linha_anterior.setImageResource(R.drawable.linha_diagonal);
            } else {
                linha_anterior.setImageResource(R.drawable.linha_diagonal2);
            }
        }
    }

    private void addEspacoParaProximaLinha() {
        ConstraintLayout constLayout = (ConstraintLayout) viewRoteiro;
        ConstraintSet constSet = new ConstraintSet();
        constSet.clone(constLayout);

        ImageView linha = new ImageView(this);
        linha.setId(-1010000 - listaTracos.size());

        ConstraintLayout.LayoutParams constParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        linha.setLayoutParams(constParams);
        constLayout.addView(linha);

        if(listaTracos.size() == 0) {
            constSet.connect(linha.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, marginDefault+tamImagem/2);
        } else {
            constSet.connect(linha.getId(), ConstraintSet.TOP, listaTracos.get(listaEventos.size()-1).getId(), ConstraintSet.BOTTOM, 0);
        }

        constSet.connect(linha.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, marginDefault+tamImagem/2);
        constSet.connect(linha.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, marginDefault+tamImagem/2);

        constSet.constrainHeight(linha.getId(), tamImagem+marginDefault);
        constSet.applyTo(constLayout);

        listaTracos.add(linha);
    }

    private void addImagem() {
        ConstraintLayout constLayout = (ConstraintLayout) viewRoteiro;
        ConstraintSet constSet = new ConstraintSet();
        constSet.clone(constLayout);

        ImageView imagem = new ImageView(this);
        imagem.setId(-1000000 - listaEventos.size());
        imagem.setImageResource(R.drawable.clone_chopp);
        constLayout.addView(imagem);

        if (listaEventos.size() == 0) {
            constSet.connect(imagem.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, marginDefault);
        } else {
            constSet.connect(imagem.getId(), ConstraintSet.TOP, listaEventos.get(listaEventos.size() - 1).getId(), ConstraintSet.BOTTOM, marginDefault);
        }

        if (listaEventos.size() % 2 == 0) {
            constSet.connect(imagem.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, marginDefault);
        } else {
            constSet.connect(imagem.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, marginDefault);
        }

        constSet.constrainHeight(imagem.getId(), tamImagem);
        constSet.constrainWidth(imagem.getId(), tamImagem);
        constSet.applyTo(constLayout);

        listaEventos.add(imagem);

        imagem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                opcoesEvento(v);
            }
        });

    }
}
