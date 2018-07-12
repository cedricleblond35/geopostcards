package com.example.cleblond2016.geopostcards2.DAO;

public abstract class MediaContract {

    public static final String TABLE_NAME = "Medias";

    public static final String COL_CARD = "card";
    public static final String COL_TYPE = "type";
    public static final String COL_URL = "url";
    public static final String COL_DESCRIPTION = "description";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                    + COL_CARD + " TEXT NOT NULL,"
                    + COL_TYPE + " TEXT NOT NULL,"
                    + COL_URL + " TEXT NOT NULL,"
                    + COL_DESCRIPTION + " TEXT NULL,"
                    + " PRIMARY KEY(" + COL_CARD + ", " + COL_TYPE + "),"
                    + " FOREIGN KEY (" + COL_CARD + ") REFERENCES " + PostCardContract.TABLE_NAME + "(" + PostCardContract.COL_ID + "))";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
