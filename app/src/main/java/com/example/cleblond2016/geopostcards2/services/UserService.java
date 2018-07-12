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
        //User user1 = new User(id,"user1", "erosmlg5resg6r64er68", "user1@campus-eni.fr", "pass1");
        //return user1;
        return dao.selectOneById(id);
    }

    public User getUserByEmail(Context context, String email){
        UserDAO dao = new UserDAO(context);
        //User user1 = new User("user1", "erosmlg5resg6r64er68", email, "pass1");
        //return user1;
        return dao.selectOneByEmail(email);
    }

    public List<User> getAllUsers(Context context){
        UserDAO dao = new UserDAO(context);
        return dao.selectAll();
    }

    /* ##### INSERT ##### */

    public boolean insertUser(Context context, User user){
        UserDAO dao = new UserDAO(context);
        return dao.insert(user);
    }

    public boolean insertAllUsers(Context context, List<User> users){
        UserDAO dao = new UserDAO(context);
        return dao.insertAll(users);
    }

    /* ##### UPDATE ##### */

    /* ##### DELETE ##### */

    /* ##### OTHER ##### */
}
