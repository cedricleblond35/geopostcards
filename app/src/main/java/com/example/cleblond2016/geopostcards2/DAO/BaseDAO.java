package com.example.cleblond2016.geopostcards2.DAO;

import java.util.List;

public interface BaseDAO<T> {

    /* ##### SELECT ##### */
    List<T> selectAll();

    /* ##### INSERT ##### */
    Integer insert(T object);
    List<T> insertAll(List<T> listObject);

    /* ##### UPDATE ##### */
    Integer update(T object);
    List<T> updateAll(List<T> listObject);

    /* ##### DELETE ##### */
    boolean delete(T object);
    List<T> deleteAll(List<T> listObject);
}
