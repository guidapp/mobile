package com.example.guidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.guidapp.controllers.EventoController;
import com.example.guidapp.model.Evento;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

    public void roteiro (View v) {
        Intent intent = new Intent(this, Roteiro.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng garanhuns = new LatLng(-8.8940296,-36.4896322);

        EventoController eventoController = EventoController.getInstance();
        for (Evento evento : eventoController.listaEventos) {
            if(! eventoController.eventoPassado(evento))
                mMap.addMarker(new MarkerOptions().position(new LatLng(evento.getLatitude(),evento.getLongitude())).title(evento.getNome()));
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(garanhuns, 14));
    }
}
