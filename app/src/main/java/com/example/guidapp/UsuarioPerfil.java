package com.example.guidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.guidapp.api.AtualizarUsuarioRunnable;
import com.example.guidapp.api.LoginRunnable;
import com.example.guidapp.controllers.UsuarioController;
import com.example.guidapp.model.Usuario;

public class UsuarioPerfil extends AppCompatActivity {
    public static final int API_UPDATE_ESPERANDO_API = 0;
    public static final int API_UPDATE_ESPERANDO_USUARIO = 1;
    public static final int API_UPDATE_SUCESSO = 2;
    public static final int API_UPDATE_ERRO = 3;

    private int retornoApi;

    private EditText etNome;
    private EditText etSobrenome;
    private EditText etEmail;
    private Button btSalvar;

    private Usuario usuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_perfil);

        retornoApi = API_UPDATE_ESPERANDO_USUARIO;

        usuarioLogado = UsuarioController.getInstance().getUsuarioLogado();

        etNome = findViewById(R.id.etNome);
        etSobrenome = findViewById(R.id.etSobrenome);
        etEmail = findViewById(R.id.etEmail);
        btSalvar = findViewById(R.id.btSalvar);

        etNome.setText(usuarioLogado.getNome());
        etSobrenome.setText(usuarioLogado.getSobrenome());
        etEmail.setText(usuarioLogado.getEmail());
    }

    public void alterarSenha (View v) {
        Intent intent = new Intent(this, AlteraSenhaUsuario.class);
        startActivity(intent);
    }

    public void roteiro (View v) {
        Intent intent = new Intent(this, Roteiro.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void areaVisitada (View v) {
        Intent intent = new Intent(this, AreaVisitada.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void mapa (View v) {
        Intent intent = new Intent(this, Mapa.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void atualizarUsuario (View v) {
        String nome = etNome.getText().toString();
        String sobrenome = etSobrenome.getText().toString();
        String email = etEmail.getText().toString();

        usuarioLogado.setNome(nome);
        usuarioLogado.setSobrenome(sobrenome);
        usuarioLogado.setEmail(email);

        AsyncTask.execute(new AtualizarUsuarioRunnable(this, usuarioLogado));
        retornoApi = API_UPDATE_ESPERANDO_API;
        esperarRespostaUpdate();
    }

    private void esperarRespostaUpdate() {
        while (retornoApi == API_UPDATE_ESPERANDO_API) {
        }

        switch (retornoApi) {
            case API_UPDATE_SUCESSO:
                UsuarioController.getInstance().atualizarDados(this, usuarioLogado.getNome(), usuarioLogado.getSobrenome(), usuarioLogado.getEmail());
        }

        // RECARREGAR A ACTIVITY ATUAL
        finish();
        startActivity(getIntent());
    }

    public void setRetornoApi(int retornoApi) {
        this.retornoApi = retornoApi;
    }
}
