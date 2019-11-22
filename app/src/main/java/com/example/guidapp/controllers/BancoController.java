package com.example.guidapp.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.guidapp.database.CriaBanco;
import com.example.guidapp.model.Evento;

public class BancoController {
    private SQLiteDatabase db;
    private CriaBanco banco;

    public BancoController(Context context){
        banco = new CriaBanco(context);
    }

    public String insereDado(Evento evento){
        ContentValues valores;
        long resultado;



        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put("nome", evento.getNome());
        valores.put("avaliacao", evento.getAvaliacao());
        valores.put("latitude", evento.getLatitude());
        valores.put("longitude", evento.getLongitude());
        valores.put("endereco", evento.getEndereco());
        valores.put("datahora", evento.getDataHora().getTimeInMillis());

        resultado = db.insert("eventos", null, valores);
        db.close();

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";
    }

    public Cursor carregaDados(){
        Cursor cursor;
        String[] campos =  {"_id", "nome"};
        db = banco.getReadableDatabase();
        cursor = db.query("eventos", campos, null, null, null, null, null, null);

        if(cursor!=null){
//            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
}
