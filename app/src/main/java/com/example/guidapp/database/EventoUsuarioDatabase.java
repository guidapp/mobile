package com.example.guidapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.guidapp.model.Evento;
import com.example.guidapp.model.EventoUsuario;

import java.util.ArrayList;
import java.util.Calendar;

import static android.os.Build.ID;

public class EventoUsuarioDatabase extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "banco.db";
    private static final String TABELA = "eventos_usuarios";
    private static final String ID_EVENTO = "_idevento";
    private static final String ID_USUARIO = "_idusuario";
    private static final String ROTEIRO = "roteiro";
    private static final String VISITOU = "visitou";
    private static final String AVALIACAO = "avaliacao";
    private static final int VERSAO = 1;

    private SQLiteDatabase db;

    public EventoUsuarioDatabase(@Nullable Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TABELA+"("
                + ID_EVENTO + " integer primary key, "
                + ID_USUARIO + " integer primary key, "
                + ROTEIRO + " integer, "
                + VISITOU + " integer, "
                + AVALIACAO + " integer, "
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

    public ArrayList<EventoUsuario> listar(int idUsuario){
        ArrayList<EventoUsuario> listaRelacoes = new ArrayList<>();
        Cursor cursor;
        String[] campos =  {ID_EVENTO, ID_USUARIO, ROTEIRO, VISITOU, AVALIACAO};
        db = this.getReadableDatabase();

        cursor = db.query(TABELA, campos, null, null, null, null, null, null);

        if(cursor!=null){
            while(cursor.moveToNext()) {
                EventoUsuario eventoUsuario = new EventoUsuario(
                        cursor.getInt(0), // ID_EVENTO
                        cursor.getInt(1), // ID_USUARIO
                        cursor.getInt(2) == 1, // ROTEIRO
                        cursor.getInt(3) == 1, // VISITOU
                        cursor.getFloat(4) // AVALIACAO
                );

                listaRelacoes.add(eventoUsuario);
            }
        }
        db.close();

        return listaRelacoes;
    }

    public boolean inserir(EventoUsuario eventoUsuario) {
        ContentValues valores;
        long resultado;

        db = this.getWritableDatabase();
        valores = new ContentValues();
        valores.put(ID_EVENTO, eventoUsuario.getIdevento());
        valores.put(ID_USUARIO, eventoUsuario.getIdusuario());
        valores.put(ROTEIRO, eventoUsuario.isRoteiro());
        valores.put(VISITOU, eventoUsuario.isVisitou());
        valores.put(AVALIACAO, eventoUsuario.getAvaliacao());

        resultado = db.insert(TABELA, null, valores);
        db.close();

        return resultado != -1;
    }
}
