package com.example.cleblond2016.geopostcards2.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cleblond2016.geopostcards2.BO.User;

import static com.example.cleblond2016.geopostcards2.DAO.UserContract.*;

import java.util.ArrayList;
import java.util.List;

public class UserDAO implements BaseDAO<User>{

    private GestionBddHelper helper;

    public UserDAO(Context context){
        helper = new GestionBddHelper(context);
    }

    private User map(Cursor c) {
        int id = c.getInt(c.getColumnIndex(COL_ID));
        String username = c.getString(c.getColumnIndex(COL_USERNAME));
        String token_id = c.getString(c.getColumnIndex(COL_TOKEN));
        String email = c.getString(c.getColumnIndex(COL_EMAIL));
        String password = c.getString(c.getColumnIndex(COL_PASSWORD));
        return new User(id, username, token_id, email, password);
    }

    /* ##### SELECT ##### */

    public User selectOneById(Integer objectId) {
        User user = null;
        String whereClause = "id = ?";
        String[] whereArgs = new String[] {
                String.valueOf(objectId)
        };
        try(SQLiteDatabase db = helper.getReadableDatabase()) {
            Cursor cursor = db.query(TABLE_NAME, null, whereClause, whereArgs, null, null, null);
            if(cursor.getCount() > 0)
            {
                user = new User();
                while(cursor.moveToNext()) {
                    user = map(cursor);
                }
            }
        }
        return user;
    }

    public User selectOneByEmail(String email) {
        User user = new User();
        String whereClause = "email = ?";
        String[] whereArgs = new String[] {
                String.valueOf(email)
        };
        try(SQLiteDatabase db = helper.getReadableDatabase()) {
            Cursor cursor = db.query(TABLE_NAME, null, whereClause, whereArgs, null, null, null);
            while(cursor.moveToNext()) {
                user = map(cursor);
            }
        }
        return user;
    }

    @Override
    public List<User> selectAll() {
        List<User> listUsers = new ArrayList<>();
        try(SQLiteDatabase db = helper.getReadableDatabase()) {
            Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
            while(cursor.moveToNext()) {
                listUsers.add(map(cursor));
            }
        }
        return listUsers;
    }

    /* ##### INSERT ##### */

    @Override
    public boolean insert(User object) {
        boolean result = true;
        Long res;
        try(SQLiteDatabase db = helper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(COL_USERNAME, object.getUsername());
            values.put(COL_TOKEN, object.getToken_id());
            values.put(COL_EMAIL, object.getEmail());
            values.put(COL_PASSWORD, object.getPassword());

            res = db.insert(TABLE_NAME, null, values);

            if (res == null) result = false;
        }
        return result;
    }

    @Override
    public boolean insertAll(List<User> listObject) {
        boolean result = true;
        Long res;
        try(SQLiteDatabase db = helper.getWritableDatabase()) {
            for (User u : listObject) {
                ContentValues values = new ContentValues();
                values.put(COL_ID, u.getId());
                values.put(COL_USERNAME, u.getUsername());
                values.put(COL_TOKEN, u.getToken_id());
                values.put(COL_EMAIL, u.getEmail());
                values.put(COL_PASSWORD, u.getPassword());

                res = db.insert(TABLE_NAME, null, values);

                if (res == null) result = false;
            }
        }
        return result;
    }

    /* ##### UPDATE ##### */

    @Override
    public boolean update(User object) {
        return false;
    }

    @Override
    public boolean updateAll(List<User> listObject) {
        return false;
    }

    /* ##### DELETE ##### */

    @Override
    public boolean delete(User object) {
        return false;
    }

    @Override
    public boolean deleteAll(List<User> listObject) {
        return false;
    }
}
