package br.ufc.ubicomp.mihealth.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.drive.Contents;

import java.util.ArrayList;
import java.util.List;

import br.ufc.ubicomp.mihealth.model.Contatos;
import br.ufc.ubicomp.mihealth.model.Medicines;

public class MedicinesDAO {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public MedicinesDAO (Context context){
        databaseHelper = new DatabaseHelper(context);
    }
    private SQLiteDatabase getDatabase(){
        if (database == null){
            database = databaseHelper.getWritableDatabase();
        }
        return (database);
    }

    private Medicines criarMedicine(Cursor cursor){
        Medicines medicines = new Medicines(
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Medicamentos._ID)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Medicamentos.NOME)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Medicamentos.HORARIOS))
        );
        return medicines;
    }

    public List<Medicines> listarMedicines(){
        Cursor cursor = getDatabase().query(DatabaseHelper.Medicamentos.TABELA,
                DatabaseHelper.Medicamentos.COLUNAS, null, null, null, null, null, null);

        List<Medicines> medicines = new ArrayList<Medicines>();
        while (cursor.moveToNext()){
            Medicines model = criarMedicine(cursor);
            medicines.add(model);
        }
        cursor.close();
        return medicines;
    }

    public long salvarMedicines(Medicines medicines){
        ContentValues valores = new ContentValues();
        valores.put(DatabaseHelper.Medicamentos.NOME, medicines.getNome());
        valores.put(DatabaseHelper.Medicamentos.HORARIOS, medicines.getHorario());

        if(medicines.get_id() != null){
            return getDatabase().update(DatabaseHelper.Medicamentos.TABELA, valores, "_id = ?",
                    new String[]{medicines.get_id().toString()});
        }
        return getDatabase().insert(DatabaseHelper.Medicamentos.TABELA, null, valores);
    }

    public boolean removerMedicines(int id){
        return getDatabase().delete(DatabaseHelper.Medicamentos.TABELA, "_id = ?",
                new String[]{Integer.toString(id)}) > 0;
    }

    public Medicines buscarMedicines(int id){
        Cursor cursor = getDatabase().query(DatabaseHelper.Medicamentos.TABELA,
                DatabaseHelper.Medicamentos.COLUNAS, "_id = ?",
                new String[]{Integer.toString(id)}, null, null, null);
        if (cursor.moveToNext()){
            Medicines model = criarMedicine(cursor);
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
