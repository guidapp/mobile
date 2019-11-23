package com.example.guidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.guidapp.controllers.EventoController;
import com.example.guidapp.model.Evento;

import java.util.ArrayList;

public class AreaVisitada extends AppCompatActivity {

    TextView tvPorcentagem;
    ConstraintLayout listaEventoView;

    EventoController eventoController;
    private ArrayList<ItemListaEventos> listaItensEvento;

    private int marginDefault = 40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_visitada);

        eventoController = EventoController.getInstance();
        listaItensEvento = new ArrayList<>();

        listaEventoView = findViewById(R.id.listaEventosView);
        tvPorcentagem = findViewById(R.id.tvPorcentagem);

        tvPorcentagem.setText(eventoController.porcentagemLocaisVisitados() + " %");

        construirLista();
    }

    public void detalhesEvento (View v) {
        Intent intent = new Intent(this, DescricaoEvento.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void roteiro (View v) {
        Intent intent = new Intent(this, Roteiro.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void perfil (View v) {
        Intent intent = new Intent(this, UsuarioPerfil.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void mapa (View v) {
        Intent intent = new Intent(this, Mapa.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void construirLista() {
        for (Evento evento : eventoController.listaEventos){
            if(eventoController.eventoNoRoteiro(evento.getId()) || eventoController.localVisitado(evento.getLatitude(), evento.getLongitude()) || eventoController.eventoPassado(evento)) {
                continue;
            }

            ConstraintSet constSet = new ConstraintSet();
            constSet.clone(listaEventoView);

            ItemListaEventos item = new ItemListaEventos(this, evento);
            item.setId(-1000000 - evento.getId());
            item.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            listaEventoView.addView(item);

            if (listaItensEvento.size() == 0) {
                constSet.connect(item.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, marginDefault);
            } else {
                constSet.connect(item.getId(), ConstraintSet.TOP, listaItensEvento.get(listaItensEvento.size() - 1).getId(), ConstraintSet.BOTTOM, marginDefault);
            }

            constSet.connect(item.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, marginDefault);
            constSet.connect(item.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, marginDefault);

            constSet.constrainHeight(item.getId(), ItemListaEventos.calcularAltura());
            constSet.applyTo(listaEventoView);

            item.desenhar();

            listaItensEvento.add(item);
        }
    }
}
