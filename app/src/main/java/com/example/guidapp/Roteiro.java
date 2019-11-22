package com.example.guidapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.guidapp.controllers.EventoController;
import com.example.guidapp.model.Evento;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Roteiro extends AppCompatActivity {

    public static final int SEM_ACAO = 0;
    public static final int ADD_EVENTO_AO_ROTEIRO = 1;

    private ConstraintLayout viewRoteiro;
    private FloatingActionButton btAddEvento;
    private ArrayList<ImageView> listaTracos;
    private ArrayList<ItemRoteiro> listaItens;
    private EventoController eventoController;

    private int marginDefault = 40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roteiro);

        this.viewRoteiro = (ConstraintLayout) findViewById(R.id.viewRoteiro);
        this.btAddEvento = (FloatingActionButton) findViewById(R.id.btAddEvento);

        this.listaTracos = new ArrayList<>();
        this.listaItens = new ArrayList<>();

        this.eventoController = EventoController.getInstance();

        btAddEvento.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listaEventos(v);
            }
        });

        for (Evento evento : eventoController.eventosRoteiro) {
            addEvento(evento);
        }
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

    public void addEvento (Evento evento) {
        this.desenharLinhaAnterior();
        this.addEspacoParaProximaLinha();
        this.addItemRoteiro(evento);
    }

    public void removerEvento (int idEvento) {
        eventoController.removerEventoRoteiro(idEvento);

        this.listaItens = new ArrayList<>();
        this.listaTracos = new ArrayList<>();

        viewRoteiro.removeAllViews();

        for (Evento evento : eventoController.eventosRoteiro) {
            addEvento(evento);
        }
    }

    public void opcoesEvento(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setItems(R.array.opcoes_evento_roteiro, new DialogEventoRoteiro(this, ((ItemRoteiro) v).getEvento().getId()));

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
            constSet.connect(linha.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 2*marginDefault+ItemRoteiro.calcularAltura()/2);
        } else {
            constSet.connect(linha.getId(), ConstraintSet.TOP, listaTracos.get(listaItens.size()-1).getId(), ConstraintSet.BOTTOM, 0);
        }

        constSet.connect(linha.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
        constSet.connect(linha.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0);

        constSet.constrainHeight(linha.getId(), ItemRoteiro.calcularAltura()+marginDefault);
        constSet.applyTo(constLayout);

        listaTracos.add(linha);
    }

    private void addItemRoteiro(Evento evento) {
        ConstraintLayout constLayout = (ConstraintLayout) viewRoteiro;
        ConstraintSet constSet = new ConstraintSet();
        constSet.clone(constLayout);

        ItemRoteiro item = new ItemRoteiro(this, evento);
        item.setId(-1000000 - listaItens.size());
        constLayout.addView(item);

        if (listaItens.size() == 0) {
            constSet.connect(item.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, marginDefault);
        } else {
            constSet.connect(item.getId(), ConstraintSet.TOP, listaItens.get(listaItens.size() - 1).getId(), ConstraintSet.BOTTOM, marginDefault);
        }

        if (listaItens.size() % 2 == 0) {
            constSet.connect(item.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, marginDefault);
        } else {
            constSet.connect(item.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, marginDefault);
        }

        constSet.constrainHeight(item.getId(), ItemRoteiro.calcularAltura());
        constSet.applyTo(constLayout);

        item.desenhar(listaItens.size()%2 == 1);

        listaItens.add(item);
    }
}
