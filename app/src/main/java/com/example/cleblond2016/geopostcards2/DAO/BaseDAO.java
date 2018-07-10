package com.example.cleblond2016.geopostcards2.DAO;

import java.util.List;

public interface BaseDAO<T> {

    /* ##### SELECT ##### */

    List<T> selectAll();

    /* ##### INSERT ##### */

    boolean insert(T object);
    boolean insertAll(List<T> listObject);

    /* ##### UPDATE ##### */

    boolean update(T object);
    boolean updateAll(List<T> listObject);

    /* ##### DELETE ##### */

    boolean delete(T object);
    boolean deleteAll(List<T> listObject);
}
