package com.example.guidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginUsuario extends AppCompatActivity {

    private EditText loginText, senhaText;
//    private Button btLogin = (Button) findViewById(R.id.btLogin);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuario);
        this.loginText = (EditText)findViewById(R.id.etEmailLogin);
        this.senhaText = (EditText)findViewById(R.id.etSenhaLogin);
    }

    public void login(View v) {
        Intent it = new Intent(this, Roteiro.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        it.putExtra("nome", loginText.getText().toString());
        it.putExtra("senha", senhaText.getText().toString());
        startActivity(it);
    }

    public void cadastro (View v) {
        Intent intent = new Intent(this, CadastroUsuario.class);
        startActivity(intent);
    }
}
