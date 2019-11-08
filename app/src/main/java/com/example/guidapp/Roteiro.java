package com.example.guidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Roteiro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roteiro);
    }

    public void perfil (View v) {
        Intent intent = new Intent(this, UsuarioPerfil.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void areaVisitada (View v) {
        Intent intent = new Intent(this, AreaVisitada.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void listaEventos (View v) {
        Intent intent = new Intent(this, ListaEventos.class);
        startActivity(intent);
    }
}
