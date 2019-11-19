package com.example.guidapp;

import java.time.LocalDate;
import java.time.LocalTime;

public class Evento {
    private int id;
    private String nome;
    private String descricao;
    private float avaliacao;
    private int visitas;
    private float latitude;
    private float longitude;
    private String endereco;
    private LocalDate data;
    private LocalTime hora;
    private String nomeImagem;

    public Evento(int id, String nome, String descricao, float avaliacao, int visitas, float latitude, float longitude, String endereco, LocalDate data, LocalTime hora) {
        this.nome = nome;
        this.descricao = descricao;
        this.avaliacao = avaliacao;
        this.visitas = visitas;
        this.latitude = latitude;
        this.longitude = longitude;
        this.endereco = endereco;
        this.data = data;
        this.hora = hora;
    }

    public Evento(int id, String nome, String endereco) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(float avaliacao) {
        this.avaliacao = avaliacao;
    }

    public int getVisitas() {
        return visitas;
    }

    public void setVisitas(int visitas) {
        this.visitas = visitas;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
}
