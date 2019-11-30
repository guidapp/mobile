package com.example.guidapp.api;

import android.util.JsonReader;
import android.util.Log;

import com.example.guidapp.LoginUsuario;
import com.example.guidapp.controllers.EventoController;
import com.example.guidapp.model.Evento;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class EventosRunnable implements Runnable {
    private String TAG = "EventosRunnable";

    LoginUsuario context;

    public EventosRunnable(LoginUsuario context) {
        this.context = context;
    }

    @Override
    public void run() {
        try {
            URL githubEndpoint = new URL("http://104.154.173.252:8000/api/eventos");
            HttpURLConnection myConnection = (HttpURLConnection) githubEndpoint.openConnection();

            myConnection.connect();

            if(myConnection.getResponseCode() == 200) {
                InputStreamReader responseBodyReader = new InputStreamReader(myConnection.getInputStream(), "UTF-8");
                JsonReader jsonReader = new JsonReader(responseBodyReader);

                ArrayList<Evento> eventos = new ArrayList<>();

                jsonReader.beginArray();
                while(jsonReader.hasNext()) {
                    eventos.add(new Evento(jsonReader));
                }
                jsonReader.endArray();

                jsonReader.close();
                myConnection.disconnect();

                EventoController.getInstance().atualizarBancoLocal(context, eventos);

                context.setRetornoApi(LoginUsuario.API_EVENTOS_LISTAGEM_SUCESSO);
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
