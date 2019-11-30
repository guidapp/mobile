package com.example.guidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.guidapp.api.AtualizarSenhaRunnable;
import com.example.guidapp.controllers.UsuarioController;

public class AlteraSenhaUsuario extends AppCompatActivity {
    public static final int API_UPDATE_SENHA_ESPERANDO_API = 0;
    public static final int API_UPDATE_SENHA_ESPERANDO_USUARIO = 1;
    public static final int API_UPDATE_SENHA_SUCESSO = 2;
    public static final int API_UPDATE_SENHA_ERRO = 3;
    public static final int API_UPDATE_SENHA_NAO_AUTORIZADO = 4;

    private EditText etSenhaAtual;
    private EditText etNovaSenha;
    private EditText etConfirmarSenha;
    private TextView tvAviso;

    private int retornoApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altera_senha_usuario);

        retornoApi = API_UPDATE_SENHA_ESPERANDO_USUARIO;

        etSenhaAtual = findViewById(R.id.etSenhaAtual);
        etNovaSenha = findViewById(R.id.etNovaSenha);
        etConfirmarSenha = findViewById(R.id.etConfirmarSenha);
        tvAviso = findViewById(R.id.tvAviso);
    }

    public void salvar (View v) {
        UsuarioController usuarioController = UsuarioController.getInstance();

        String senhaAtual = etSenhaAtual.getText().toString();
        String novaSenha = etNovaSenha.getText().toString();
        String confirmarSenha = etConfirmarSenha.getText().toString();

        if(! novaSenha.equals(confirmarSenha)) {
            tvAviso.setText("A nova senha e a confirmação da senha devem ser iguais.");
        } else if(novaSenha.length() < 8) {
            tvAviso.setText("O tamanho minimo da nova senha e 8.");
        } else {
            AsyncTask.execute(new AtualizarSenhaRunnable(this, senhaAtual, novaSenha));
            retornoApi = API_UPDATE_SENHA_ESPERANDO_API;
            esperarRespostaUpdate();
        }
    }

    private void esperarRespostaUpdate() {
        while (retornoApi == API_UPDATE_SENHA_ESPERANDO_API) {
        }

        switch (retornoApi) {
            case API_UPDATE_SENHA_NAO_AUTORIZADO:
                tvAviso.setText("Senha incorreta.");
                break;
            case API_UPDATE_SENHA_ERRO:
                tvAviso.setText("Erro no servidor.");
                break;
            case API_UPDATE_SENHA_SUCESSO:
                UsuarioController.getInstance().carregarUsuarioDoBanco(this);
                finish();
                break;
        }
    }

    public void setRetornoApi(int retornoApi) {
        this.retornoApi = retornoApi;
    }
}
