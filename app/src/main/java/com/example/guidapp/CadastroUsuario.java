package com.example.guidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
    private TextView tvAviso;

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
        tvAviso = findViewById(R.id.tvAviso);
    }

    public void cadastrar (View v) {
        String nome, sobrenome, email, senha, confSenha;

        nome = etNomeCadastro.getText().toString();
        sobrenome = etSobrenome.getText().toString();
        email = etEmailCadastro.getText().toString();
        senha = etSenhaCadastro.getText().toString();
        confSenha = etConfSenhaCadastro.getText().toString();

        if(nome.equals("") || sobrenome.equals("") || email.equals("") || senha.equals("") || confSenha.equals("")) {
            tvAviso.setText("Todos os campos são obrigatórios.");
            return;
        }

        if(senha.length() < 8) {
            tvAviso.setText("O tamanho mínimo da senha são 8 caracteres.");
            return;
        }

        if(! senha.equals(confSenha)) {
            tvAviso.setText("As senhas devem ser iguais.");
            return;
        }

        Usuario usuario = new Usuario(0, nome, sobrenome, email, senha, false, null);
        AsyncTask.execute(new CadastroRunnable(this, usuario));
        retornoApi = API_CADASTRO_ESPERANDO_API;
        esperarRespostaLogin();
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
