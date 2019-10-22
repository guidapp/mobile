package com.example.guidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginUsuario extends AppCompatActivity {

    private EditText loginText, senhaText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuario);
        this.loginText = (EditText)findViewById(R.id.etNomeLogin);
        this.senhaText = (EditText)findViewById(R.id.etSenhaLogin);
    }

    public void login(View v) {
        Intent it = new Intent(this, UsuarioPerfil.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        it.putExtra("nome", loginText.getText().toString());
        it.putExtra("senha", senhaText.getText().toString());
        startActivity(it);
    }
}
