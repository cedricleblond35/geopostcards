package com.example.cleblond2016.geopostcards2.DAO;

import android.content.Context;

import com.example.cleblond2016.geopostcards2.BO.User;

import java.util.List;

public class UserDAO implements BaseDAO<User>{

    private GestionBddHelper helper;

    public UserDAO(Context context){
        helper = new GestionBddHelper(context);
    }

    public User selectOneById(int objectId) {
        return null;
    }

    @Override
    public List<User> selectAll() {
        return null;
    }

    @Override
    public Integer insert(User object) {
        return null;
    }

    @Override
    public List<User> insertAll(List<User> listObject) {
        return null;
    }

    @Override
    public Integer update(User object) {
        return null;
    }

    @Override
    public List<User> updateAll(List<User> listObject) {
        return null;
    }

    @Override
    public boolean delete(User object) {
        return false;
    }

    @Override
    public List<User> deleteAll(List<User> listObject) {
        return null;
    }
}
