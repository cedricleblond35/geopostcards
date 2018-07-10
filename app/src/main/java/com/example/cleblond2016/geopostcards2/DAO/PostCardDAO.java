package com.example.cleblond2016.geopostcards2.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cleblond2016.geopostcards2.BO.PostCard;

import static com.example.cleblond2016.geopostcards2.DAO.PostCardContract.*;

import java.util.ArrayList;
import java.util.List;

public class PostCardDAO implements BaseDAO<PostCard> {

    private GestionBddHelper helper;

    public PostCardDAO(Context context){
        helper = new GestionBddHelper(context);
    }

    private PostCard map(Cursor c) {
        String id = c.getString(c.getColumnIndex(COL_ID));
        double latitude = c.getDouble(c.getColumnIndex(COL_LATITUDE));
        double longitude = c.getDouble(c.getColumnIndex(COL_LONGITUDE));
        String title = c.getString(c.getColumnIndex(COL_TITLE));
        String message = c.getString(c.getColumnIndex(COL_MESSAGE));
        return new PostCard(id, latitude, longitude, title, message);
    }

    /* ##### SELECT ##### */

    public PostCard selectOneById(String objectId) {
        PostCard postCard = new PostCard();
        String whereClause = "id = ?";
        String[] whereArgs = new String[] {
                objectId
        };
        try(SQLiteDatabase db = helper.getReadableDatabase()) {
            Cursor cursor = db.query(TABLE_NAME, null, whereClause, whereArgs, null, null, null);
            while(cursor.moveToNext()) {
                postCard = map(cursor);
            }
        }
        return postCard;
    }

    @Override
    public List<PostCard> selectAll() {
        List<PostCard> listPostCards = new ArrayList<>();
        try(SQLiteDatabase db = helper.getReadableDatabase()) {
            Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
            while(cursor.moveToNext()) {
                listPostCards.add(map(cursor));
            }
        }
        return listPostCards;
    }

    /* ##### INSERT ##### */

    @Override
    public boolean insert(PostCard object) {
        boolean result = true;
        Long res;
        try(SQLiteDatabase db = helper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(COL_ID, object.getId());
            values.put(COL_LATITUDE, object.getLatitude());
            values.put(COL_LONGITUDE, object.getLongitude());
            values.put(COL_TITLE, object.getTitle());
            values.put(COL_MESSAGE, object.getMessage());

            res = db.insert(TABLE_NAME, null, values);

            if (res == null) result = false;
        }
        return result;
    }

    @Override
    public boolean insertAll(List<PostCard> listObject) {
        boolean result = true;
        Long res;
        try(SQLiteDatabase db = helper.getWritableDatabase()) {
            for (PostCard p : listObject) {
                ContentValues values = new ContentValues();
                values.put(COL_ID, p.getId());
                values.put(COL_LATITUDE, p.getLatitude());
                values.put(COL_LONGITUDE, p.getLongitude());
                values.put(COL_TITLE, p.getTitle());
                values.put(COL_MESSAGE, p.getMessage());

                res = db.insert(TABLE_NAME, null, values);

                if (res == null) result = false;
            }
        }
        return result;
    }

    /* ##### UPDATE ##### */

    @Override
    public boolean update(PostCard object) {
        return false;
    }

    @Override
    public boolean updateAll(List<PostCard> listObject) {
        return false;
    }

    /* ##### DELETE ##### */

    @Override
    public boolean delete(PostCard object) {
        return false;
    }

    @Override
    public boolean deleteAll(List<PostCard> listObject) {
        return false;
    }
}
