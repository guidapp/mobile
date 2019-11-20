package com.example.guidapp.controllers;

import com.example.guidapp.Evento;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

public class EventoController {
    public ArrayList<Evento> listaEventos;
    public ArrayList<Evento> eventosRoteiro;

    private static EventoController instance = null;

    private EventoController() {
        listaEventos = new ArrayList<>();
        eventosRoteiro = new ArrayList<>();

        int ano = 2019;
        int mes = 11;
        int dia = 19;

        listaEventos.add(new Evento(1, "Clone de chopp",                    1,  -8.8928373, -36.4953706, "Rua 3", new GregorianCalendar(ano, mes, dia, 13, 30, 00)));
        listaEventos.add(new Evento(2, "MARATONA DE CRUZES",                2,  -8.8843082, -36.4756346, "Rua 5", new GregorianCalendar(ano, mes, dia, 12, 20, 00)));
        listaEventos.add(new Evento(3, "Simpósio de Engenharia Mecânica",   3,  -8.8799955, -36.4881046, "Rua 6", new GregorianCalendar(ano, mes, dia, 10, 10, 00)));
        listaEventos.add(new Evento(4, "MARCELO FALCÃO NA VIBE",            2,  -8.8805019, -36.4857083, "Rua 1", new GregorianCalendar(ano, mes, dia, 9,  50, 00)));
        listaEventos.add(new Evento(5, "PEGADO - HOUSE CLUB",               4,  -8.8916634, -36.473478, "Rua 2", new GregorianCalendar(ano, mes, dia, 15, 10, 00)));
        listaEventos.add(new Evento(6, "emPride",                           5,  -8.8929524, -36.4857135, "Rua 9", new GregorianCalendar(ano, mes, dia, 6,  30, 00)));
        listaEventos.add(new Evento(7, "Simpósio FBJ 2019",                 1,  -8.8837221, -36.4740897, "Rua 4", new GregorianCalendar(ano, mes, dia, 20, 20, 00)));
        listaEventos.add(new Evento(8, "10 anos da Matemática UFPE/CAA",    3,  -8.882537, -36.4867451, "Rua 7", new GregorianCalendar(ano, mes, dia, 21, 40, 00)));
        listaEventos.add(new Evento(9, "4• Encontro de Tatuadores BJ-PE",   0,  -8.8798445, -36.4693109, "Rua 8", new GregorianCalendar(ano, mes, dia, 14, 10, 00)));
        listaEventos.add(new Evento(10, "Feira Afrocriativa 2019",          2,  -8.883594, -36.4906688, "Rua a", new GregorianCalendar(ano, mes, dia, 11, 00, 00)));
        listaEventos.add(new Evento(11, "Carreiras Policias MDM",           1,  -8.8880472, -36.4914402, "Rua b", new GregorianCalendar(ano, mes, dia, 18, 10, 00)));
        listaEventos.add(new Evento(12, "3a Corrida Vamos Abraçar o Sol",   2,  -8.8814444, -36.4935899, "Rua c", new GregorianCalendar(ano, mes, dia, 17, 20, 00)));
        listaEventos.add(new Evento(13, "34ª Corrida da Consciência Negra", 4,  -8.8900444, -36.4759209, "Rua d", new GregorianCalendar(ano, mes, dia, 8,  30, 00)));
    }

    public static EventoController getInstance() {
        if(instance == null)
            instance = new EventoController();

        return instance;
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
}
