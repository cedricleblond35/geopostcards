package com.geopostcards.cleblond2016.geopostcards.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;


@Dao
public abstract class BaseDAO<T> {
    @Insert
    abstract boolean insert(T object);

    @Update
    abstract boolean update(T object);

    @Delete
    abstract boolean delete(T object);
}
