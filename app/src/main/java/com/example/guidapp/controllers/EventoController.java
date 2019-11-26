package com.example.guidapp.controllers;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.example.guidapp.database.EventoDatabase;
import com.example.guidapp.model.Evento;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

public class EventoController {
    public ArrayList<Evento> listaEventos;
    public ArrayList<Evento> eventosRoteiro;
    public ArrayList<Integer> eventosVisitados;

    private static EventoController instance = null;

    private EventoController() {
        listaEventos = new ArrayList<>();
        eventosRoteiro = new ArrayList<>();
        eventosVisitados = new ArrayList<>();
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

        eventosVisitados.add(1);
        eventosVisitados.add(2);

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

    public boolean eventoVisitado(int idEvento) {
        return eventosVisitados.contains(idEvento);
    }

    public int porcentagemLocaisVisitados() {
        ArrayList<LatLng> locais = new ArrayList<>();
        ArrayList<LatLng> locaisVisitados = new ArrayList<>();

        // ALGORITMO PARA LISTAR LOCAIS DE EVENTOS (SEM REPETICAO)
        for(Evento evento : listaEventos) {
            LatLng coordEvento = new LatLng(evento.getLatitude(), evento.getLongitude());

            boolean localAdicionado = false;
            for (LatLng local : locais) {
                if(coordenadasProximas(local, coordEvento)){
                    localAdicionado = true;
                    break;
                }
            }

            if(! localAdicionado) {
                locais.add(coordEvento);
            }
        }

        // ALGORITMO PARA LISTAR LOCAIS VISITADOS
        for(int idEvento : eventosVisitados) {
            Evento eventoVisitado = getEventoById(idEvento);
            LatLng coordEvento = new LatLng(eventoVisitado.getLatitude(), eventoVisitado.getLongitude());

            boolean localVisitado = false;
            for (LatLng local : locaisVisitados) {
                if(coordenadasProximas(local, coordEvento)){
                    localVisitado = true;
                    break;
                }
            }

            if(! localVisitado) {
                locaisVisitados.add(coordEvento);
            }
        }

        int total = locais.size();
        int visitados = locaisVisitados.size();

        return 100 * visitados / total;
    }

    public boolean localVisitado(double latitude, double longitude) {
        LatLng coord = new LatLng(latitude, longitude);

        for (int idEventoVisitado : eventosVisitados) {
            Evento eventoVisitado = getEventoById(idEventoVisitado);
            LatLng coordEvento = new LatLng(eventoVisitado.getLatitude(), eventoVisitado.getLongitude());

            if(coordenadasProximas(coordEvento, coord)) {
                return true;
            }
        }

        return false;
    }

    public boolean eventoPassado(Evento evento) {
        return evento.getDataHora().before(Calendar.getInstance());
    }

    private boolean coordenadasProximas (LatLng coord1, LatLng coord2) {
        return coord1.latitude == coord2.latitude && coord1.longitude == coord2.longitude;
    }
}
