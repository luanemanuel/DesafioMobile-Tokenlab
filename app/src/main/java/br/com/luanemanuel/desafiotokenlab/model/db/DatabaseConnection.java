package br.com.luanemanuel.desafiotokenlab.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseConnection extends SQLiteOpenHelper {

    private static final String mName = "tokenlabdesafio.db";
    private static final int mVersion = 1;

    public DatabaseConnection( Context context) {
        super(context, mName, null, mVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS movie(id integer not null primary key, title varchar(200), poster_url varchar(1000), " +
                "overview text, votes varchar(20), adult bool, release_date varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
