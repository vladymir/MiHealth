package br.ufc.ubicomp.mihealth.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

import br.ufc.ubicomp.mihealth.R;
import br.ufc.ubicomp.mihealth.model.HeartBeatFreq;
import br.ufc.ubicomp.mihealth.model.User;
/**
 * Created by vladymirbezerra on 25/04/15.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "mihealth.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<User, Integer> uerDao;
    private Dao<HeartBeatFreq, Integer> heartBeatDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }


    public DatabaseHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
