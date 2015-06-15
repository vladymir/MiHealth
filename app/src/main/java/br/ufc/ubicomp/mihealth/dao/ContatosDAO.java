package br.ufc.ubicomp.mihealth.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.drive.Contents;

import java.util.ArrayList;
import java.util.List;

import br.ufc.ubicomp.mihealth.model.Contatos;

public class ContatosDAO {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public ContatosDAO (Context context){
        databaseHelper = new DatabaseHelper(context);
    }
    private SQLiteDatabase getDatabase(){
        if (database == null){
            database = databaseHelper.getWritableDatabase();
        }
        return (database);
    }

    private Contatos criarContato(Cursor cursor){
        Contatos model = new Contatos(
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Contatos._ID)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Contatos.NOME)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Contatos.TELEFONE))
        );
        return model;
    }

    public List<Contatos> listarContatos(){
        Cursor cursor = getDatabase().query(DatabaseHelper.Contatos.TABELA,
                DatabaseHelper.Contatos.COLUNAS, null, null, null, null, null);

        List<Contatos> contatos = new ArrayList<Contatos>();
        while (cursor.moveToNext()){
            Contatos model = criarContato(cursor);
            contatos.add(model);
        }
        cursor.close();
        return contatos;
    }

    public long salvarContatos(Contatos contatos){
        ContentValues valores = new ContentValues();
        valores.put(DatabaseHelper.Contatos.NOME, contatos.getNome());
        valores.put(DatabaseHelper.Contatos.TELEFONE, contatos.getTelefone());

        if(contatos.get_id() != null){
            return getDatabase().update(DatabaseHelper.Contatos.TABELA, valores, "_id = ?",
                    new String[]{contatos.get_id().toString()});
        }
    return getDatabase().insert(DatabaseHelper.Contatos.TABELA, null, valores);
    }

    public boolean removerContato(int id){
    return getDatabase().delete(DatabaseHelper.Contatos.TABELA, "_id = ?",
        new String[]{Integer.toString(id)}) > 0;
    }

    public Contatos buscarContatos(int id){
        Cursor cursor = getDatabase().query(DatabaseHelper.Contatos.TABELA,
                DatabaseHelper.Contatos.COLUNAS, "_id = ?",
                new String[]{Integer.toString(id)}, null, null, null);
        if (cursor.moveToNext()){
            Contatos model = criarContato(cursor);
            cursor.close();
            return model;
        }
    return null;
    }

    public void fechar(){
        databaseHelper.close();
        database = null;
    }
}
