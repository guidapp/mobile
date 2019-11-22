package com.example.guidapp.model;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Evento implements Comparable<Evento> {
    private int id;
    private String nome;
    private String descricao;
    private float avaliacao;
    private int visitas;
    private double latitude;
    private double longitude;
    private String endereco;
    private Calendar dataHora;
    private String nomeImagem;

    public Evento(int id, String nome, String descricao, float avaliacao, int visitas, double latitude, double longitude, String endereco, Calendar dataHora) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.avaliacao = avaliacao;
        this.visitas = visitas;
        this.latitude = latitude;
        this.longitude = longitude;
        this.endereco = endereco;
        this.dataHora = dataHora;
    }

    public Evento(int id, String nome, int avaliacao, double latitude, double longitude, String endereco, Calendar dataHora) {
        this.id = id;
        this.nome = nome;
        this.avaliacao = avaliacao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.endereco = endereco;
        this.dataHora = dataHora;
    }

    public Evento(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.avaliacao = 3;
        this.latitude = 2.222;
        this.longitude = 4.444;
        this.endereco = "Rua 7";
        this.dataHora = Calendar.getInstance();
    }

    public String getDataHoraFormatada() {
        return getDataHora().get(Calendar.DATE) + "/" +
                getDataHora().get(Calendar.MONTH) + "/" +
                getDataHora().get(Calendar.YEAR) + " " +
                getDataHora().get(Calendar.HOUR_OF_DAY) + ":" +
                getDataHora().get(Calendar.MINUTE);
    }

    public boolean mesmoLocal(Evento evento) {
        return this.longitude == evento.getLongitude() && this.latitude == evento.getLatitude();
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
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

    public Calendar getDataHora() {
        return dataHora;
    }

    public void setDataHora(Calendar dataHora) {
        this.dataHora = dataHora;
    }

    public String getNomeImagem() {
        return nomeImagem;
    }

    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }

    @Override
    public int compareTo(Evento evento) {
        return dataHora.compareTo(evento.getDataHora());
    }
}
