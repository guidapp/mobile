package com.example.guidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.guidapp.controllers.EventoController;
import com.example.guidapp.model.Evento;

import java.util.ArrayList;

public class ListaEventos extends AppCompatActivity {
    ConstraintLayout listaEventoView;

    private ArrayList<ItemListaEventos> listaItensEvento;

    private int marginDefault = 40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_eventos);

        this.listaEventoView = (ConstraintLayout) findViewById(R.id.listaEventosView);
        this.listaItensEvento = new ArrayList<>();

        construirLista();
    }

    public void detalhesEvento (View v) {
        Intent intent = new Intent(this, DescricaoEvento.class);
        startActivity(intent);
    }

    public void voltar (View v) {
        finish();
    }

    private void construirLista() {
        EventoController eventoController = EventoController.getInstance();

        for (Evento evento : eventoController.listaEventos){
            if(eventoController.eventoNoRoteiro(evento.getId()) || eventoController.eventoVisitado(evento.getId())) {
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
