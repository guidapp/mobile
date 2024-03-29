package com.example.guidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guidapp.api.EventosRunnable;
import com.example.guidapp.api.LoginRunnable;
import com.example.guidapp.controllers.UsuarioController;
import com.example.guidapp.model.Usuario;

public class LoginUsuario extends AppCompatActivity {
    public static final int API_LOGIN_ESPERANDO_API = 0;
    public static final int API_LOGIN_ESPERANDO_USUARIO = 1;
    public static final int API_LOGIN_SUCESSO = 2;
    public static final int API_LOGIN_EMAIL_SENHA_INCORRETOS = 3;
    public static final int API_ERRO_NO_SERVIDOR = 4;
    public static final int API_EVENTOS_ESPERANDO_API = 5;
    public static final int API_EVENTOS_LISTAGEM_SUCESSO = 6;

    private EditText loginText, senhaText;
    private TextView tvAviso;
//    private Button btLogin = (Button) findViewById(R.id.btLogin);

    private int retornoApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuario);

        retornoApi = API_LOGIN_ESPERANDO_USUARIO;

        this.loginText = (EditText)findViewById(R.id.etEmailLogin);
        this.senhaText = (EditText)findViewById(R.id.etSenhaLogin);
        this.tvAviso = findViewById(R.id.tvAviso);

        loginText.setText("admin@gmail.com");
        senhaText.setText("123456");

        /*EventoController.getInstance().repopularBanco(this);
        EventoController.getInstance().carregarDadosDoBanco(this);

        UsuarioController.getInstance().repopularBanco(this);
        UsuarioController.getInstance().carregarUsuarioDoBanco(this);*/
    }

    public void login(View v) {
         AsyncTask.execute(new LoginRunnable(this, loginText.getText().toString(), senhaText.getText().toString()));
         retornoApi = API_LOGIN_ESPERANDO_API;
         esperarRespostaLogin();
    }

    public void cadastro (View v) {
        Intent intent = new Intent(this, CadastroUsuario.class);
        startActivity(intent);
    }

    public void esperarRespostaLogin() {
        while (retornoApi == API_LOGIN_ESPERANDO_API) {
        }

        switch (retornoApi) {
            case API_LOGIN_EMAIL_SENHA_INCORRETOS:
                tvAviso.setText("E-mail ou senha incorretos.");
                break;
            case API_ERRO_NO_SERVIDOR:
                tvAviso.setText("Erro no servidor.");
                break;
            case API_LOGIN_SUCESSO:
                AsyncTask.execute(new EventosRunnable(this));
                retornoApi = API_EVENTOS_ESPERANDO_API;
                esperarListagemEventos();
                break;
        }
    }

    public void esperarListagemEventos() {
        while (retornoApi == API_EVENTOS_ESPERANDO_API) {
        }

        switch (retornoApi) {
            case API_EVENTOS_LISTAGEM_SUCESSO:
                Intent intent = new Intent(this, Mapa.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
        }
    }

    public int getRetornoApi() {
        return retornoApi;
    }

    public void setRetornoApi(int retornoApi) {
        this.retornoApi = retornoApi;
    }
}
