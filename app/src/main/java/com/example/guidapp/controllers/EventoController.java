package com.example.guidapp.controllers;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.example.guidapp.database.EventoDatabase;
import com.example.guidapp.database.EventoUsuarioDatabase;
import com.example.guidapp.model.Evento;
import com.example.guidapp.model.EventoUsuario;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

public class EventoController {
    public ArrayList<Evento> listaEventos;
    public ArrayList<Evento> eventosRoteiro;
    // public ArrayList<Integer> eventosVisitados;

    private static EventoController instance = null;

    private EventoController() {
        listaEventos = new ArrayList<>();
        eventosRoteiro = new ArrayList<>();
        // eventosVisitados = new ArrayList<>();
    }

    public static EventoController getInstance() {
        if(instance == null)
            instance = new EventoController();

        return instance;
    }

    public void carregarDadosDoBanco(Context context) {
        EventoDatabase eventoDatabase = new EventoDatabase(context);
        listaEventos = eventoDatabase.listar();
    }

    public void atualizarBancoLocal(Context context, ArrayList<Evento> eventos) {
        EventoDatabase eventoDatabase = new EventoDatabase(context);
        eventoDatabase.zerarTabela();

        for (Evento evento : eventos) {
            eventoDatabase.inserir(evento);
        }

        // eventosVisitados.add(1);
        // eventosVisitados.add(2);

        carregarDadosDoBanco(context);
    }

    public Evento getEventoById(int id) {
        for (Evento evento : listaEventos) {
            if(evento.getId() == id)
                return evento;
        }

        return null;
    }

    public void addEventoAoRoteiro(int idEvento) {
        eventosRoteiro.add(getEventoById(idEvento));
        Collections.sort(eventosRoteiro);
    }

    public void removerEventoRoteiro(int idEvento) {
        eventosRoteiro.remove(getEventoById(idEvento));
        Collections.sort(eventosRoteiro);
    }

    public boolean eventoNoRoteiro(int idEvento) {
        Evento evento = getEventoById(idEvento);

        return eventosRoteiro.contains(evento);
    }

    public boolean eventoPassado(Evento evento) {
        return evento.getDataHora().before(Calendar.getInstance());
    }
}
