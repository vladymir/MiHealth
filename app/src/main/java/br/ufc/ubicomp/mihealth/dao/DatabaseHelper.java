package br.ufc.ubicomp.mihealth.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String BANCO_DADOS = "mihealth";
    private static final int VERSÃO = 1;

    public DatabaseHelper (Context context){
        super(context, BANCO_DADOS, null, VERSÃO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //medicamentos
        db.execSQL("create table medicamentos(_id integer primary key autoincrement, "
                    +"nome text not null, horarios text not null)");

        //contatos
        db.execSQL("create table contatos(_id integer primary key autoincrement, "
                +"nome text not null, telefone text not null)");
        //freq cardiaca
        db.execSQL("create table freqCard(_id integer primary key autoincrement, "
                +"valor text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public static class Medicamentos{
        public static final String TABELA = "medicamentos";
        public static final String _ID = "_id";
        public static final String NOME = "nome";
        public static final String HORARIOS = "horarios";

        public static final String[] COLUNAS = new String[]{TABELA, _ID, NOME, HORARIOS};
    }
    public static class Contatos{
        public static final String TABELA = "contatos";
        public static final String _ID = "_id";
        public static final String NOME = "nome";
        public static final String TELEFONE = "telefone";

        public static final String[] COLUNAS = new String[]{TABELA, _ID, NOME, TELEFONE};
    }
    public static class FreqCard{
        public static final String TABELA = "freqCard";
        public static final String _ID = "_id";
        public static final String VALOR = "valor";

        public static final String[] COLUNAS = new String[]{TABELA, _ID, VALOR};
    }
}
