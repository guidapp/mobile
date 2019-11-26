package com.example.guidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.guidapp.api.CadastroRunnable;
import com.example.guidapp.api.LoginRunnable;
import com.example.guidapp.model.Usuario;

public class CadastroUsuario extends AppCompatActivity {
    public static final int API_CADASTRO_ESPERANDO_API = 0;
    public static final int API_CADASTRO_ESPERANDO_USUARIO = 1;
    public static final int API_CADASTRO_SUCESSO = 2;
    public static final int API_CADASTRO_ERRO = 3;

    private int retornoApi;

    private EditText etNomeCadastro,etSobrenome, etEmailCadastro, etSenhaCadastro, etConfSenhaCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        retornoApi = API_CADASTRO_ESPERANDO_USUARIO;

        etNomeCadastro = findViewById(R.id.etNomeCadastro);
        etSobrenome = findViewById(R.id.etSobrenome);
        etEmailCadastro = findViewById(R.id.etEmailCadastro);
        etSenhaCadastro = findViewById(R.id.etSenhaCadastro);
        etConfSenhaCadastro = findViewById(R.id.etConfSenhaCadastro);
    }

    public void cadastrar (View v) {
        String nome, sobrenome, email, senha, confSenha;

        nome = etNomeCadastro.getText().toString();
        sobrenome = etSobrenome.getText().toString();
        email = etEmailCadastro.getText().toString();
        senha = etSenhaCadastro.getText().toString();
        confSenha = etConfSenhaCadastro.getText().toString();

        if(senha.equals(confSenha)) {
            Usuario usuario = new Usuario(0, nome, sobrenome, email, senha, false, null);
            AsyncTask.execute(new CadastroRunnable(this, usuario));
            retornoApi = API_CADASTRO_ESPERANDO_API;
            esperarRespostaLogin();
        }
    }

    private void esperarRespostaLogin() {
        while (retornoApi == API_CADASTRO_ESPERANDO_API) {
        }

        switch (retornoApi) {
            case API_CADASTRO_SUCESSO:
                finish();
                break;
        }
    }

    public void setRetornoApi(int retornoApi) {
        this.retornoApi = retornoApi;
    }
}
