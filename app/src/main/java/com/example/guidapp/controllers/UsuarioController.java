package com.example.guidapp.controllers;

import android.content.Context;

import com.example.guidapp.database.EventoUsuarioDatabase;
import com.example.guidapp.database.UsuarioDatabase;
import com.example.guidapp.exceptions.ConfirmacaoDeSenhaInvalidaException;
import com.example.guidapp.exceptions.SenhaInvalidaException;
import com.example.guidapp.exceptions.SenhaTamanhoInvalidoException;
import com.example.guidapp.model.EventoUsuario;
import com.example.guidapp.model.Usuario;

import java.util.ArrayList;

public class UsuarioController {
    public static final int TAMANHO_MINIMO_SENHA = 8;

    private Usuario usuarioLogado;

    private static UsuarioController instance = null;

    private UsuarioController() {
    }

    public static UsuarioController getInstance() {
        if(instance == null)
            instance = new UsuarioController();

        return instance;
    }

    public void carregarUsuarioDoBanco(Context context) {
        UsuarioDatabase usuarioDatabase = new UsuarioDatabase(context);

        ArrayList<Usuario> usuarios = usuarioDatabase.listar();

        if(usuarios.size() > 0)
            usuarioLogado = usuarios.get(0);
    }

    public void login(Context context, Usuario usuario) {
        UsuarioDatabase usuarioDatabase = new UsuarioDatabase(context);
        usuarioDatabase.zerarTabela();

        usuarioDatabase.inserir(usuario);
        carregarUsuarioDoBanco(context);
    }

    public void repopularBanco(Context context) {
        UsuarioDatabase usuarioDatabase = new UsuarioDatabase(context);
        usuarioDatabase.zerarTabela();

        Usuario usuario = new Usuario("Jose", "da Silva", "jose@gmail.com", "123456", false, null);

        usuarioDatabase.inserir(usuario);
    }

    public void atualizarDados(Context context, String nome, String sobrenome, String email) {
        UsuarioDatabase usuarioDatabase = new UsuarioDatabase(context);

        Usuario usuario = new Usuario(nome, sobrenome, email);

        usuarioDatabase.atualizarRegistro(usuarioLogado.getId(), usuario);

        carregarUsuarioDoBanco(context);
    }

    public void atualizarSenha(Context context, String senhaAtual, String novaSenha, String confirmarSenha) throws SenhaInvalidaException, ConfirmacaoDeSenhaInvalidaException, SenhaTamanhoInvalidoException {
        UsuarioDatabase usuarioDatabase = new UsuarioDatabase(context);

        if(! senhaAtual.equals(usuarioLogado.getSenha())) {
            throw new SenhaInvalidaException();
        }

        if(novaSenha.length() < TAMANHO_MINIMO_SENHA) {
            throw new SenhaTamanhoInvalidoException();
        }

        if(! novaSenha.equals(confirmarSenha)) {
            throw new ConfirmacaoDeSenhaInvalidaException();
        }

        if(senhaAtual.equals(usuarioLogado.getSenha()) && novaSenha.equals(confirmarSenha)) {
            usuarioDatabase.atualizarSenha(usuarioLogado.getId(), novaSenha);

            carregarUsuarioDoBanco(context);
        }
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
}
