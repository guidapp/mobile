package com.example.guidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.guidapp.controllers.UsuarioController;
import com.example.guidapp.model.Usuario;

public class UsuarioPerfil extends AppCompatActivity {

    private EditText etNome;
    private EditText etSobrenome;
    private EditText etEmail;
    private Button btSalvar;

    private Usuario usuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_perfil);

        usuarioLogado = UsuarioController.getInstance().getUsuarioLogado();

        etNome = findViewById(R.id.etNome);
        etSobrenome = findViewById(R.id.etSobrenome);
        etEmail = findViewById(R.id.etEmail);
        btSalvar = findViewById(R.id.btSalvar);

        etNome.setText(usuarioLogado.getNome());
        etSobrenome.setText(usuarioLogado.getSobrenome());
        etEmail.setText(usuarioLogado.getEmail());
    }

    public void alterarSenha (View v) {
        Intent intent = new Intent(this, AlteraSenhaUsuario.class);
        startActivity(intent);
    }

    public void roteiro (View v) {
        Intent intent = new Intent(this, Roteiro.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void areaVisitada (View v) {
        Intent intent = new Intent(this, AreaVisitada.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void mapa (View v) {
        Intent intent = new Intent(this, Mapa.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void atualizarUsuario (View v) {
        String nome = etNome.getText().toString();
        String sobrenome = etSobrenome.getText().toString();
        String email = etEmail.getText().toString();

        UsuarioController.getInstance().atualizarDados(this, nome, sobrenome, email);

        // RECARREGAR A ACTIVITY ATUAL
        finish();
        startActivity(getIntent());
    }
}
