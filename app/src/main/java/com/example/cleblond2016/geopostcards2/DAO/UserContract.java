package com.example.cleblond2016.geopostcards2.DAO;

public abstract class UserContract {

    public static final String TABLE_NAME = "Users";

    public static final String COL_ID = "id";
    public static final String COL_USERNAME = "username";
    public static final String COL_TOKEN = "token_id";
    public static final String COL_EMAIL = "email";
    public static final String COL_PASSWORD = "password";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                    + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_USERNAME + " TEXT NOT NULL,"
                    + COL_TOKEN + " TEXT NULL,"
                    + COL_EMAIL + " TEXT NOT NULL,"
                    + COL_PASSWORD + " TEXT NOT NULL)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
