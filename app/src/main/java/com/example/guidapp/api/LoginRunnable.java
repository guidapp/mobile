package com.example.guidapp.api;

import android.util.JsonReader;
import android.util.Log;

import com.example.guidapp.LoginUsuario;
import com.example.guidapp.controllers.UsuarioController;
import com.example.guidapp.model.Usuario;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginRunnable implements Runnable {
    private String TAG = "LoginRunnable";
    private LoginUsuario context;
    private String email;
    private String senha;

    public LoginRunnable(LoginUsuario context, String email, String senha) {
        this.context = context;
        this.email = email;
        this.senha = senha;
    }

    @Override
    public void run() {
        try {
            URL githubEndpoint = new URL("http://104.154.173.252:8000/api/login");
            HttpURLConnection myConnection = (HttpURLConnection) githubEndpoint.openConnection();
            myConnection.setRequestMethod("POST");
            myConnection.setRequestProperty("Content-type", "application/json");
            myConnection.setDoOutput(true);

            String jsonInputString = "{\"email\": \"" + email + "\", \"senha\": \"" + senha + "\"}";

            try {
                OutputStream os = myConnection.getOutputStream();
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            } catch (IOException e) {
                Log.e(TAG, "Erro ao montar requisição do login. " + e.getMessage());
            }

            myConnection.connect();

            if(myConnection.getResponseCode() == 200) {
                InputStreamReader responseBodyReader = new InputStreamReader(myConnection.getInputStream(), "UTF-8");
                JsonReader jsonReader = new JsonReader(responseBodyReader);

                Usuario usuario = new Usuario(jsonReader);

                jsonReader.close();
                myConnection.disconnect();

                UsuarioController.getInstance().login(context, usuario);

                context.setRetornoApi(LoginUsuario.API_LOGIN_SUCESSO);
            } else if(myConnection.getResponseCode() == 401) {
                context.setRetornoApi(LoginUsuario.API_LOGIN_EMAIL_SENHA_INCORRETOS);
            } else {
                context.setRetornoApi(LoginUsuario.API_ERRO_NO_SERVIDOR);
                Log.e(TAG, "Código do erro: " + myConnection.getResponseCode());
            }
        } catch (IOException e) {
            context.setRetornoApi(LoginUsuario.API_ERRO_NO_SERVIDOR);
            Log.e(TAG, "Erro ao acessar a API. " + e.getMessage());
        }
    }
}
