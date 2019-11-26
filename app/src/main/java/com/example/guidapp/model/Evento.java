package com.example.guidapp.model;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public Evento(int id, String nome, float avaliacao, double latitude, double longitude, String endereco, Calendar dataHora) {
        this(id, nome, "descrição....", avaliacao, 0, latitude, longitude, endereco, dataHora);
    }

    public Evento(int id, String nome) {
        this(id, nome, "descrição....", 3, 0, 2.222, 4.444, "Rua 7", Calendar.getInstance());
    }

    public Evento(JsonReader jsonReader) throws IOException {
        String hora = "";
        String data = "";

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            String key = jsonReader.nextName();

            switch (key) {
                case "id":
                    this.id = jsonReader.nextInt();
                    break;
                case "nome":
                    this.nome = jsonReader.nextString();
                    break;
                case "descricao":
                    this.descricao = jsonReader.nextString();
                    break;
                case "hora":
                    hora = jsonReader.nextString();
                    break;
                case "evento_unico":
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        String key2 = jsonReader.nextName();

                        switch (key2) {
                            case "latitude":
                                this.latitude = Double.parseDouble(jsonReader.nextString());
                                break;
                            case "longitude":
                                this.longitude = Double.parseDouble((jsonReader.nextString()));
                                break;
                            case "data":
                                data = jsonReader.nextString();
                                break;
                            case "evento":
                                jsonReader.beginObject();
                                while (jsonReader.hasNext()) {
                                    String key3 = jsonReader.nextName();

                                    switch (key3) {
                                        case "avaliacao":
                                            this.avaliacao = Integer.parseInt(jsonReader.nextString());
                                            break;
                                        case "visitas":
                                            this.visitas = jsonReader.nextInt();
                                            break;
                                        default:
                                            jsonReader.skipValue();
                                            break;
                                    }
                                }
                                jsonReader.endObject();
                                break;
                            default:
                                jsonReader.skipValue();
                                break;
                        }
                    }
                    jsonReader.endObject();
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }

        jsonReader.endObject();

        String dataHora = data + " " + hora;
        if(!data.equals("") && !hora.equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.dataHora = Calendar.getInstance();
            try {
                this.dataHora.setTime(sdf.parse(dataHora));
            } catch (ParseException e) {
                Log.e("EVENTO", "Erro na criação do evento. " + e.getMessage());
            }
        }
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
