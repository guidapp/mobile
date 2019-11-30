package com.example.guidapp.model;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EventoUsuario {
    private int idevento;
    private int idusuario;
    private boolean roteiro;
    private boolean visitou;
    private float avaliacao;

    public EventoUsuario(int idevento, int idusuario, boolean roteiro, boolean visitou, float avaliacao) {
        this.idevento = idevento;
        this.idusuario = idusuario;
        this.roteiro = roteiro;
        this.visitou = visitou;
        this.avaliacao = avaliacao;
    }

    public int getIdevento() {
        return idevento;
    }

    public void setIdevento(int idevento) {
        this.idevento = idevento;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public boolean isRoteiro() {
        return roteiro;
    }

    public void setRoteiro(boolean roteiro) {
        this.roteiro = roteiro;
    }

    public boolean isVisitou() {
        return visitou;
    }

    public void setVisitou(boolean visitou) {
        this.visitou = visitou;
    }

    public float getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(float avaliacao) {
        this.visitou = true;
        this.avaliacao = avaliacao;
    }
}
