package com.example.guidapp.model;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class Usuario {
    private int id;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private boolean administrador;
    private String cpf;

    public Usuario(int id, String nome, String sobrenome, String email, String senha, boolean administrador, String cpf) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senha = senha;
        this.administrador = administrador;
        this.cpf = cpf;
    }

    public Usuario(int id, String nome, String sobrenome, String email, boolean administrador, String cpf) {
        this(id, nome, sobrenome, email, null, administrador, cpf);
    }

    public Usuario(String nome, String sobrenome, String email, String senha, boolean administrador, String cpf) {
        this(0, nome, sobrenome, email, senha, administrador, cpf);
    }

    public Usuario(String nome, String sobrenome, String email) {
        this(0, nome, sobrenome, email, null, false, null);
    }

    public Usuario(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            String key = jsonReader.nextName();

            switch (key) {
                case "id":
                    this.id = jsonReader.nextInt();
                    break;
                case "name":
                    this.nome = jsonReader.nextString();
                    break;
                case "email":
                    this.email = jsonReader.nextString();
                    break;
                case "cpf":
                    this.cpf = jsonReader.nextString();
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }

            this.administrador = false;
        }

        jsonReader.endObject();
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

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
