package com.example.guidapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.guidapp.model.Evento;

import java.util.ArrayList;
import java.util.Calendar;

public class EventoDatabase extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "banco.db";
    private static final String TABELA = "eventos";
    private static final String ID = "_id";
    private static final String NOME = "nome";
    private static final String AVALIACAO = "avaliacao";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String ENDERECO = "endereco";
    private static final String DATAHORA = "datahora";
    private static final int VERSAO = 1;

    private SQLiteDatabase db;

    public EventoDatabase(@Nullable Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TABELA+"("
                + ID + " integer primary key autoincrement, "
                + NOME + " text, "
                + AVALIACAO + " integer, "
                + LATITUDE + " real, "
                + LONGITUDE + " real, "
                + ENDERECO + " text, "
                + DATAHORA + " integer "
                +")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);
    }

    public void zerarTabela() {
        onUpgrade(this.getWritableDatabase(), 1, 1);
    }

    public ArrayList<Evento> listar(){
        ArrayList<Evento> listaEventos = new ArrayList<>();
        Cursor cursor;
        String[] campos =  {ID, NOME, AVALIACAO, LATITUDE, LONGITUDE, ENDERECO, DATAHORA};
        db = this.getReadableDatabase();

        cursor = db.query(TABELA, campos, null, null, null, null, null, null);

        if(cursor!=null){
            while(cursor.moveToNext()) {
                Calendar dataHora = Calendar.getInstance();
                dataHora.setTimeInMillis(cursor.getLong(6));

                Evento evento = new Evento(
                        cursor.getInt(0), // ID
                        cursor.getString(1), // NOME
                        cursor.getInt(2), // AVALIACAO
                        cursor.getDouble(3), // LATITUDE
                        cursor.getDouble(4), // LONGITUDE
                        cursor.getString(5), // ENDERECO
                        dataHora // DATAHORA
                );

                listaEventos.add(evento);
            }
        }
        db.close();

        return listaEventos;
    }

    public boolean inserir(Evento evento) {
        ContentValues valores;
        long resultado;

        db = this.getWritableDatabase();
        valores = new ContentValues();
        valores.put(NOME, evento.getNome());
        valores.put(AVALIACAO, evento.getAvaliacao());
        valores.put(LATITUDE, evento.getLatitude());
        valores.put(LONGITUDE, evento.getLongitude());
        valores.put(ENDERECO, evento.getEndereco());
        valores.put(DATAHORA, evento.getDataHora().getTimeInMillis());

        resultado = db.insert(TABELA, null, valores);
        db.close();

        return resultado != -1;
    }
}
