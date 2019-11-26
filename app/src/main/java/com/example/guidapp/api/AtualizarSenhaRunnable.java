package com.example.guidapp.api;

import android.util.JsonReader;
import android.util.Log;

import com.example.guidapp.AlteraSenhaUsuario;
import com.example.guidapp.UsuarioPerfil;
import com.example.guidapp.controllers.UsuarioController;
import com.example.guidapp.model.Usuario;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AtualizarSenhaRunnable implements Runnable {
    private static String TAG = "AtualizarSenhaRunnable";

    AlteraSenhaUsuario context;
    String senhaAtual, novaSenha;

    public AtualizarSenhaRunnable(AlteraSenhaUsuario context, String senhaAtual, String novaSenha) {
        this.context = context;
        this.senhaAtual = senhaAtual;
        this.novaSenha = novaSenha;
    }

    @Override
    public void run() {
        Usuario usuario = UsuarioController.getInstance().getUsuarioLogado();

        try {
            URL githubEndpoint = new URL("http://104.154.173.252:8000/api/usuarios/alterarsenha");
            HttpURLConnection myConnection = (HttpURLConnection) githubEndpoint.openConnection();
            myConnection.setRequestMethod("POST");
            myConnection.setRequestProperty("Content-type", "application/json");
            myConnection.setDoOutput(true);

            String jsonInputString = "{\"id\":"+usuario.getId()+", \"senhaAtual\":\""+senhaAtual+"\", \"novaSenha\":\""+novaSenha+"\"}";

            Log.e(TAG, jsonInputString);

            try {
                OutputStream os = myConnection.getOutputStream();
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            } catch (IOException e) {
                Log.e(TAG, "Erro ao montar requisição de atualização de senha. " + e.getMessage());
            }

            myConnection.connect();

            if(myConnection.getResponseCode() == 200) {
                InputStreamReader responseBodyReader = new InputStreamReader(myConnection.getInputStream(), "UTF-8");
                JsonReader jsonReader = new JsonReader(responseBodyReader);

                jsonReader.close();
                myConnection.disconnect();

                context.setRetornoApi(AlteraSenhaUsuario.API_UPDATE_SENHA_SUCESSO);
            } else if (myConnection.getResponseCode() == 401) {
                context.setRetornoApi(AlteraSenhaUsuario.API_UPDATE_SENHA_NAO_AUTORIZADO);
                Log.e(TAG, "Código do erro: " + myConnection.getResponseCode());
            } else {
                context.setRetornoApi(AlteraSenhaUsuario.API_UPDATE_SENHA_ERRO);
                Log.e(TAG, "Código do erro: " + myConnection.getResponseCode());
            }
        } catch (IOException e) {
            context.setRetornoApi(AlteraSenhaUsuario.API_UPDATE_SENHA_ERRO);
            Log.e(TAG, "Erro ao acessar a API. " + e.getMessage());
        }
    }
}
