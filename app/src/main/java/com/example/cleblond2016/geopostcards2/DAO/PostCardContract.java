package com.example.cleblond2016.geopostcards2.DAO;

public abstract class PostCardContract {

    public static final String TABLE_NAME = "PostCards";

    public static final String COL_ID = "id";
    public static final String COL_LATITUDE = "latitude";
    public static final String COL_LONGITUDE = "longitude";
    public static final String COL_TITLE = "title";
    public static final String COL_USER = "user";
    public static final String COL_FK_USER_NAME = "fk_user";
    public static final String COL_MESSAGE = "message";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                    + COL_ID + " TEXT PRIMARY KEY,"
                    + COL_LATITUDE + " REAL NOT NULL,"
                    + COL_LONGITUDE + " REAL NOT NULL,"
                    + COL_TITLE + " TEXT NOT NULL,"
                    + COL_USER + " INTEGER NOT NULL,"
                    + COL_MESSAGE + " TEXT NULL," +
                    " FOREIGN KEY (" + COL_FK_USER_NAME + ") REFERENCES " + UserContract.TABLE_NAME + "(" + UserContract.COL_ID + "))";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
