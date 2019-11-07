package com.example.guidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginUsuario extends AppCompatActivity {

//    private EditText etEmailLogin = (EditText) findViewById(R.id.etEmailLogin);
//    private EditText etSenhaLogin = (EditText) findViewById(R.id.etSenhaLogin);
//    private Button btLogin = (Button) findViewById(R.id.btLogin);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuario);
    }

    public void login (View v) {
        Intent intent = new Intent(this, Roteiro.class);
        startActivity(intent);
    }

    public void cadastro (View v) {
        Intent intent = new Intent(this, CadastroUsuario.class);
        startActivity(intent);
    }
}
