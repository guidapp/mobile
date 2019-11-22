package com.example.guidapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.guidapp.model.Usuario;

import java.util.ArrayList;

public class UsuarioDatabase extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "banco.db";
    private static final String TABELA = "usuarios";
    private static final String ID = "_id";
    private static final String NOME = "nome";
    private static final String SOBRENOME = "sobrenome";
    private static final String EMAIL = "email";
    private static final String SENHA = "senha";
    private static final String IS_ADMINISTRADOR = "isadminitrador";
    private static final String CPF = "cpf";
    private static final int VERSAO = 1;

    private SQLiteDatabase db;

    Context context;

    public UsuarioDatabase(@Nullable Context context) {
        super(context, NOME_BANCO, null, VERSAO);

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TABELA+"("
                + ID + " integer primary key autoincrement, "
                + NOME + " text, "
                + SOBRENOME + " text, "
                + EMAIL + " text, "
                + SENHA + " text, "
                + IS_ADMINISTRADOR + " integer, "
                + CPF + " text "
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

    public ArrayList<Usuario> listar(){
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        Cursor cursor;

        String[] campos =  {ID, NOME, SOBRENOME, EMAIL, SENHA, IS_ADMINISTRADOR, CPF};

        db = this.getReadableDatabase();

        cursor = db.query(TABELA, campos, null, null, null, null, null, null);

        if(cursor!=null){
            while(cursor.moveToNext()) {
                Usuario usuario = new Usuario(
                        cursor.getInt(0), // ID
                        cursor.getString(1), // NOME
                        cursor.getString(2), // SOBRENOME
                        cursor.getString(3), // EMAIL
                        cursor.getString(4), // SENHA
                        cursor.getInt(5) == 1, // IS_ADMINISTRADOR
                        cursor.getString(6) // CPF
                );

                listaUsuarios.add(usuario);
            }
        }
        db.close();

        return listaUsuarios;
    }

    public boolean inserir(Usuario usuario) {
        ContentValues valores;
        long resultado;

        db = this.getWritableDatabase();
        valores = new ContentValues();
        valores.put(NOME, usuario.getNome());
        valores.put(SOBRENOME, usuario.getSobrenome());
        valores.put(EMAIL, usuario.getEmail());
        valores.put(SENHA, usuario.getSenha());
        valores.put(IS_ADMINISTRADOR, usuario.isAdministrador());
        valores.put(CPF, usuario.getCpf());

        resultado = db.insert(TABELA, null, valores);
        db.close();

        return resultado != -1;
    }

    public void atualizarRegistro(int id, Usuario usuario){
        ContentValues valores;
        String where;

        db = this.getWritableDatabase();

        where = UsuarioDatabase.ID + "=" + id;

        valores = new ContentValues();
        valores.put(UsuarioDatabase.NOME, usuario.getNome());
        valores.put(UsuarioDatabase.SOBRENOME, usuario.getSobrenome());
        valores.put(UsuarioDatabase.EMAIL, usuario.getEmail());
        valores.put(UsuarioDatabase.CPF, usuario.getCpf());

        db.update(UsuarioDatabase.TABELA,valores,where,null);
        db.close();
    }

    public void atualizarSenha(int idUsuario, String novaSenha) {
        ContentValues valores;
        String where;

        db = this.getWritableDatabase();

        where = UsuarioDatabase.ID + "=" + idUsuario;

        valores = new ContentValues();
        valores.put(UsuarioDatabase.SENHA, novaSenha);

        db.update(UsuarioDatabase.TABELA,valores,where,null);
        db.close();
    }
}
