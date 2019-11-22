package com.example.guidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.guidapp.controllers.EventoController;
import com.example.guidapp.controllers.UsuarioController;

public class LoginUsuario extends AppCompatActivity {

    private EditText loginText, senhaText;
//    private Button btLogin = (Button) findViewById(R.id.btLogin);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuario);
        this.loginText = (EditText)findViewById(R.id.etEmailLogin);
        this.senhaText = (EditText)findViewById(R.id.etSenhaLogin);

        EventoController.getInstance().repopularBanco(this);
        EventoController.getInstance().carregarDadosDoBanco(this);

        UsuarioController.getInstance().repopularBanco(this);
        UsuarioController.getInstance().carregarUsuarioDoBanco(this);
    }

    public void login(View v) {
        Intent intent = new Intent(this, Mapa.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//        intent.putExtra("nome", loginText.getText().toString());
//        intent.putExtra("senha", senhaText.getText().toString());
        startActivity(intent);
    }

    public void cadastro (View v) {
        Intent intent = new Intent(this, CadastroUsuario.class);
        startActivity(intent);
    }
}
