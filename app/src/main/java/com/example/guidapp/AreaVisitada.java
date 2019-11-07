package com.example.guidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AreaVisitada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_visitada);
    }

    public void detalhesEvento (View v) {
        Intent intent = new Intent(this, DescricaoEvento.class);
        startActivity(intent);
    }
}
