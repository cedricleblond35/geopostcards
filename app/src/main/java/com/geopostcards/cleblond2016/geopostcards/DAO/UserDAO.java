package com.geopostcards.cleblond2016.geopostcards.DAO;

import android.arch.persistence.room.Query;

import com.geopostcards.cleblond2016.geopostcards.BO.User;

import java.util.List;

public abstract class UserDAO implements BaseDAO<User> {


    @Query("SELECT * FROM users")
    public abstract List<User> getAllUsers();

    @Query("SELECT * FROM users WHERE id = :id")
    public abstract User getUserById(int id);

    @Override
    public boolean insert(User object) {
        return false;
    }

    @Override
    public boolean update(User object) {
        return false;
    }

    @Override
    public boolean delete(User object) {
        return false;
    }
}
