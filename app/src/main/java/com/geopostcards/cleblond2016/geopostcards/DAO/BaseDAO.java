package com.geopostcards.cleblond2016.geopostcards.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

import java.util.List;


@Dao
public interface BaseDAO<T> {
    @Insert
    boolean insert(T object);

    @Insert
    boolean insertAll(List<T> listObject);
    
    @Update
    boolean update(T object);

    @Delete
    boolean delete(T object);
}
