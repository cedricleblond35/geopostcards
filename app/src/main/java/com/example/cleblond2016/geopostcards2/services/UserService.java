package com.example.cleblond2016.geopostcards2.services;

import android.content.Context;

import com.example.cleblond2016.geopostcards2.BO.User;
import com.example.cleblond2016.geopostcards2.DAO.UserDAO;

import java.util.List;

public class UserService {

    private static UserService instance;

    public static UserService getInstance() {
        if(instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    private UserService() { }

    /* ##### SELECT ##### */

    public User getUserById(Context context, int id){
        UserDAO dao = new UserDAO(context);
        return dao.selectOneById(id);
    }

    public List<User> getAllUsers(Context context){
        UserDAO dao = new UserDAO(context);
        return dao.selectAll();
    }

    /* ##### INSERT ##### */

    public boolean insert(Context context, User user){
        UserDAO dao = new UserDAO(context);
        return dao.insert(user);
    }

    public boolean insertAll(Context context, List<User> users){
        UserDAO dao = new UserDAO(context);
        return dao.insertAll(users);
    }

    /* ##### UPDATE ##### */

    /* ##### DELETE ##### */

    /* ##### OTHER ##### */
}
