package com.example.guidapp.controllers;

import android.content.Context;

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
