package com.example.guidapp.controllers;

import android.content.Context;
import android.util.Log;

import com.example.guidapp.database.EventoDatabase;
import com.example.guidapp.database.EventoUsuarioDatabase;
import com.example.guidapp.database.UsuarioDatabase;
import com.example.guidapp.model.Evento;
import com.example.guidapp.model.EventoUsuario;
import com.example.guidapp.model.Usuario;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class EventoUsuarioController {
    public ArrayList<EventoUsuario> eventosUsuarios;

    private static EventoUsuarioController instance = null;

    private EventoUsuarioController() {
        eventosUsuarios = new ArrayList<>();
    }

    public static EventoUsuarioController getInstance() {
        if(instance == null)
            instance = new EventoUsuarioController();
        return instance;
    }

    public void carregarDadosDoBanco(Context context) {
        EventoUsuarioDatabase eventoUsuarioDatabase = new EventoUsuarioDatabase(context);
        eventosUsuarios = eventoUsuarioDatabase.listar(UsuarioController.getInstance().getUsuarioLogado().getId());
    }

    public boolean eventoVisitado(int idEvento) {
        for (EventoUsuario eventoUsuario : eventosUsuarios) {
            if(eventoUsuario.getIdevento() == idEvento) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<Integer> getIdEventosRoteiro(Context context) {
        carregarDadosDoBanco(context);

        ArrayList<Integer> eventos = new ArrayList<>();

        for (EventoUsuario eventoUsuario : eventosUsuarios) {
            if(eventoUsuario.isRoteiro()) {
                eventos.add(eventoUsuario.getIdevento());
            }
        }

        return eventos;
    }

    public EventoUsuario getEventoUsuario(int idEvento, int idUsuario) {
        for (EventoUsuario eventoUsuario : eventosUsuarios) {
            if (eventoUsuario.getIdusuario() == idUsuario && eventoUsuario.getIdevento() == idEvento)
                return eventoUsuario;
        }

        return null;
    }

    public void addEventoRoteiro(Context context, int idEvento, int idUsuario) {
        EventoUsuarioDatabase eventoUsuarioDatabase = new EventoUsuarioDatabase(context);

        EventoUsuario eventoUsuario = getEventoUsuario(idEvento, idUsuario);

        if(eventoUsuario == null) {
            eventoUsuarioDatabase.inserir(new EventoUsuario(idEvento, idUsuario, true, false, 0));
        } else {
            eventoUsuario.setRoteiro(true);
            eventoUsuarioDatabase.atualizarRegistro(idEvento, idUsuario, eventoUsuario);
        }
    }

    public void removerEventoRoteiro(Context context, int idEvento, int idUsuario) {
        EventoUsuarioDatabase eventoUsuarioDatabase = new EventoUsuarioDatabase(context);

        EventoUsuario eventoUsuario = getEventoUsuario(idEvento, idUsuario);

        if(eventoUsuario != null) {
            eventoUsuario.setRoteiro(false);
            eventoUsuarioDatabase.atualizarRegistro(idEvento, idUsuario, eventoUsuario);
        }
    }

    public void avaliarEvento(Context context, int idEvento, int idUsuario, float avaliacao) {
        EventoUsuarioDatabase eventoUsuarioDatabase = new EventoUsuarioDatabase(context);

        EventoUsuario eventoUsuario = getEventoUsuario(idEvento, idUsuario);

        if(eventoUsuario == null) {
            eventoUsuarioDatabase.inserir(new EventoUsuario(idEvento, idUsuario, false, true, avaliacao));
        } else {
            eventoUsuario.setAvaliacao(avaliacao);
            if(eventoUsuario.getAvaliacao() > 0)
                eventoUsuario.setVisitou(true);
            else
                eventoUsuario.setVisitou(false);

            eventoUsuarioDatabase.atualizarRegistro(idEvento, idUsuario, eventoUsuario);
        }

        carregarDadosDoBanco(context);
    }

    public float getAvaliacaoEvento(Context context, int idEvento, int idUsuario) {
        EventoUsuarioDatabase eventoUsuarioDatabase = new EventoUsuarioDatabase(context);

        EventoUsuario eventoUsuario = getEventoUsuario(idEvento, idUsuario);

        if(eventoUsuario == null) {
            return 0;
        } else {
            return eventoUsuario.getAvaliacao();
        }
    }

    public boolean localVisitado(double latitude, double longitude) {
        LatLng coord = new LatLng(latitude, longitude);

        for (EventoUsuario eventoUsuario : eventosUsuarios) {
            if(eventoUsuario.isVisitou()) {
                Evento eventoVisitado = EventoController.getInstance().getEventoById(eventoUsuario.getIdevento());
                LatLng coordEvento = new LatLng(eventoVisitado.getLatitude(), eventoVisitado.getLongitude());

                if(coordenadasProximas(coordEvento, coord)) {
                    return true;
                }
            }
        }

        return false;
    }

    public int porcentagemLocaisVisitados() {
        ArrayList<LatLng> locais = new ArrayList<>();
        ArrayList<LatLng> locaisVisitados = new ArrayList<>();

        ArrayList<Evento> listaEventos = EventoController.getInstance().listaEventos;

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
        for (EventoUsuario eventoUsuario : eventosUsuarios) {
            if(eventoUsuario.isVisitou()) {
                Evento eventoVisitado = EventoController.getInstance().getEventoById(eventoUsuario.getIdevento());
                LatLng coordEvento = new LatLng(eventoVisitado.getLatitude(), eventoVisitado.getLongitude());

                boolean localVisitado = false;
                for (LatLng local : locaisVisitados) {
                    if (coordenadasProximas(local, coordEvento)) {
                        localVisitado = true;
                        break;
                    }
                }

                if (!localVisitado) {
                    locaisVisitados.add(coordEvento);
                }
            }
        }

        int total = locais.size();
        int visitados = locaisVisitados.size();

        return 100 * visitados / total;
    }

    private boolean coordenadasProximas (LatLng coord1, LatLng coord2) {
        return coord1.latitude == coord2.latitude && coord1.longitude == coord2.longitude;
    }

    public ArrayList<EventoUsuario> getEventosUsuarios() {
        return eventosUsuarios;
    }

    public void setEventosUsuarios(ArrayList<EventoUsuario> eventosUsuarios) {
        this.eventosUsuarios = eventosUsuarios;
    }
}
