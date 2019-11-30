package com.example.guidapp.api;

import android.util.JsonReader;
import android.util.Log;

import com.example.guidapp.CadastroUsuario;
import com.example.guidapp.LoginUsuario;
import com.example.guidapp.controllers.UsuarioController;
import com.example.guidapp.model.Usuario;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CadastroRunnable implements Runnable {
    private String TAG = "CadastroRunnable";

    private CadastroUsuario context;
    private Usuario usuario;

    public CadastroRunnable(CadastroUsuario context, Usuario usuario) {
        this.context = context;
        this.usuario = usuario;
    }

    @Override
    public void run() {
        try {
            URL githubEndpoint = new URL("http://104.154.173.252:8000/api/usuarios");
            HttpURLConnection myConnection = (HttpURLConnection) githubEndpoint.openConnection();
            myConnection.setRequestMethod("POST");
            myConnection.setRequestProperty("Content-type", "application/json");
            myConnection.setDoOutput(true);

            String jsonInputString = usuario.toJson();

            try {
                OutputStream os = myConnection.getOutputStream();
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            } catch (IOException e) {
                Log.e(TAG, "Erro ao montar requisição do cadastro. " + e.getMessage());
            }

            myConnection.connect();

            if(myConnection.getResponseCode() == 201) {
                InputStreamReader responseBodyReader = new InputStreamReader(myConnection.getInputStream(), "UTF-8");
                JsonReader jsonReader = new JsonReader(responseBodyReader);

                Usuario usuario = new Usuario(jsonReader);

                jsonReader.close();
                myConnection.disconnect();

                context.setRetornoApi(CadastroUsuario.API_CADASTRO_SUCESSO);
            } else {
                context.setRetornoApi(CadastroUsuario.API_CADASTRO_ERRO);
                Log.e(TAG, "Código do erro: " + myConnection.getResponseCode());
            }
        } catch (IOException e) {
            context.setRetornoApi(CadastroUsuario.API_CADASTRO_ERRO);
            Log.e(TAG, "Erro ao acessar a API. " + e.getMessage());
        }
    }
}
