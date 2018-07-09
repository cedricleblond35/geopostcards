package com.example.cleblond2016.geopostcards2.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GestionBddHelper extends SQLiteOpenHelper{

    private static final String DATABASENAME = "GeoPostCardsDB";
    private static final int VERSION = 1;

    public GestionBddHelper(Context context) {
        super(context, DATABASENAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserContract.CREATE_TABLE);
        db.execSQL(PostCardContract.CREATE_TABLE);
        db.execSQL(MediaContract.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserContract.DROP_TABLE);
        db.execSQL(PostCardContract.DROP_TABLE);
        db.execSQL(MediaContract.DROP_TABLE);
        onCreate(db);
    }
}
