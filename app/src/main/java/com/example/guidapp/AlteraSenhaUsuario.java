package com.example.guidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AlteraSenhaUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altera_senha_usuario);
    }

    public void salvar (View v) {
//        Intent intent = new Intent(this, UsuarioPerfil.class);
//        startActivity(intent);
        finish();
    }
}
