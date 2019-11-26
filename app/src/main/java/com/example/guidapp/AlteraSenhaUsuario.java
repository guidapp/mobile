package com.example.guidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.guidapp.api.LoginRunnable;
import com.example.guidapp.controllers.UsuarioController;
import com.example.guidapp.exceptions.ConfirmacaoDeSenhaInvalidaException;
import com.example.guidapp.exceptions.SenhaInvalidaException;
import com.example.guidapp.exceptions.SenhaTamanhoInvalidoException;

public class AlteraSenhaUsuario extends AppCompatActivity {
    EditText etSenhaAtual;
    EditText etNovaSenha;
    EditText etConfirmarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altera_senha_usuario);

        etSenhaAtual = findViewById(R.id.etSenhaAtual);
        etNovaSenha = findViewById(R.id.etNovaSenha);
        etConfirmarSenha = findViewById(R.id.etConfirmarSenha);
    }

    public void salvar (View v) {
        UsuarioController usuarioController = UsuarioController.getInstance();

        String senhaAtual = etSenhaAtual.getText().toString();
        String novaSenha = etNovaSenha.getText().toString();
        String confirmarSenha = etConfirmarSenha.getText().toString();

        try {
            usuarioController.atualizarSenha(this, senhaAtual, novaSenha, confirmarSenha);

            Toast.makeText(this, "Senha salva com sucesso", Toast.LENGTH_LONG).show();

            finish();
        } catch (SenhaInvalidaException e) {
            Toast.makeText(this, "Senha incorreta.", Toast.LENGTH_LONG).show();
        } catch (ConfirmacaoDeSenhaInvalidaException e) {
            Toast.makeText(this, "A nova senha e a confirmação da senha devem ser iguais.", Toast.LENGTH_LONG).show();
        } catch (SenhaTamanhoInvalidoException e) {
            Toast.makeText(this, "A nova senha deve ter no mínimo 8 carecteres.", Toast.LENGTH_LONG).show();
        }
    }
}
