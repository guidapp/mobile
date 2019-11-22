package com.example.guidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.guidapp.controllers.EventoController;
import com.example.guidapp.model.Evento;

public class DescricaoEvento extends AppCompatActivity {

    private TextView tvNome;
    private TextView tvAvaliacao;
    private TextView tvLocal;
    private TextView tvDataHora;
    private TextView tvDescricao;
    private Button btActionRoteiro;

    private Evento evento;
    private EventoController eventoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descricao_evento);

        tvNome = findViewById(R.id.tvNome);
        tvAvaliacao = findViewById(R.id.tvAvaliacao);
        tvLocal = findViewById(R.id.tvLocal);
        tvDataHora = findViewById(R.id.tvDataHora);
        tvDescricao = findViewById(R.id.tvDescricao);
        btActionRoteiro = findViewById(R.id.btActionRoteiro);

        eventoController = EventoController.getInstance();
        evento = eventoController.getEventoById(getIntent().getIntExtra("idEvento", 0));

        tvNome.setText(evento.getNome());
        tvAvaliacao.setText(evento.getAvaliacao() + "/5");
        tvLocal.setText("Local: " + evento.getEndereco());
        tvDataHora.setText(evento.getDataHoraFormatada());
        tvDescricao.setText(evento.getDescricao());

        if(eventoController.eventoNoRoteiro(evento.getId())) {
            btActionRoteiro.setText("Remover evento do roteiro");
        } else {
            btActionRoteiro.setText("Adicionar evento ao roteiro");
        }
    }

    public void actionEventoRoteiro(View v) {
        if(eventoController.eventoNoRoteiro(evento.getId())) {
            eventoController.removerEventoRoteiro(evento.getId());
        } else {
            eventoController.addEventoAoRoteiro(evento.getId());
        }

        Intent intent = new Intent(this, Roteiro.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void voltar (View v) {
        finish();
    }
}
