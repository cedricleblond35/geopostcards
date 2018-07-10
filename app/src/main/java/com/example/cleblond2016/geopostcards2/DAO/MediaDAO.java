package com.example.cleblond2016.geopostcards2.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cleblond2016.geopostcards2.BO.Media;

import static com.example.cleblond2016.geopostcards2.DAO.MediaContract.*;

import java.util.ArrayList;
import java.util.List;

public class MediaDAO implements BaseDAO<Media> {

    private GestionBddHelper helper;

    public MediaDAO(Context context){
        helper = new GestionBddHelper(context);
    }

    private Media map(Cursor c) {
        String card = c.getString(c.getColumnIndex(COL_CARD));
        String type = c.getString(c.getColumnIndex(COL_TYPE));
        String url = c.getString(c.getColumnIndex(COL_URL));
        String description = c.getString(c.getColumnIndex(COL_DESCRIPTION));
        return new Media(card, type, url, description);
    }

    /* ##### SELECT ##### */

    public Media selectOneById(String objectId, String objectId2) {
        Media media = new Media();
        String whereClause = "card = ? and type = ?";
        String[] whereArgs = new String[] {
                objectId,
                objectId2
        };
        try(SQLiteDatabase db = helper.getReadableDatabase()) {
            Cursor cursor = db.query(TABLE_NAME, null, whereClause, whereArgs, null, null, null);
            while(cursor.moveToNext()) {
                media = map(cursor);
            }
        }
        return media;
    }

    @Override
    public List<Media> selectAll() {
        List<Media> listMedias = new ArrayList<>();
        try(SQLiteDatabase db = helper.getReadableDatabase()) {
            Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
            while(cursor.moveToNext()) {
                listMedias.add(map(cursor));
            }
        }
        return listMedias;
    }

    /* ##### INSERT ##### */

    @Override
    public boolean insert(Media object) {
        boolean result = true;
        Long res;
        try(SQLiteDatabase db = helper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(COL_CARD, object.getCard());
            values.put(COL_TYPE, object.getType());
            values.put(COL_URL, object.getUrl());
            values.put(COL_DESCRIPTION, object.getDescription());

            res = db.insert(TABLE_NAME, null, values);

            if (res == null) result = false;
        }
        return result;
    }

    @Override
    public boolean insertAll(List<Media> listObject) {
        boolean result = true;
        Long res;
        try(SQLiteDatabase db = helper.getWritableDatabase()) {
            for (Media m : listObject) {
                ContentValues values = new ContentValues();
                values.put(COL_CARD, m.getCard());
                values.put(COL_TYPE, m.getType());
                values.put(COL_URL, m.getUrl());
                values.put(COL_DESCRIPTION, m.getDescription());

                res = db.insert(TABLE_NAME, null, values);

                if (res == null) result = false;
            }
        }
        return result;
    }

    /* ##### UPDATE ##### */

    @Override
    public boolean update(Media object) {
        return false;
    }

    @Override
    public boolean updateAll(List<Media> listObject) {
        return false;
    }

    /* ##### DELETE ##### */

    @Override
    public boolean delete(Media object) {
        return false;
    }

    @Override
    public boolean deleteAll(List<Media> listObject) {
        return false;
    }
}
