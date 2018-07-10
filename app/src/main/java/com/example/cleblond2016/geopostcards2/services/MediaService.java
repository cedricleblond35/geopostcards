package com.example.cleblond2016.geopostcards2.services;

import android.content.Context;

import com.example.cleblond2016.geopostcards2.BO.Media;
import com.example.cleblond2016.geopostcards2.DAO.MediaDAO;

import java.util.List;

public class MediaService {

    private static MediaService instance;

    public static MediaService getInstance() {
        if(instance == null) {
            instance = new MediaService();
        }
        return instance;
    }

    private MediaService() { }

    /* ##### SELECT ##### */

    public Media getUserById(Context context, String card, String type){
        MediaDAO dao = new MediaDAO(context);
        return dao.selectOneById(card, type);
    }

    public List<Media> getAllUsers(Context context){
        MediaDAO dao = new MediaDAO(context);
        return dao.selectAll();
    }

    /* ##### INSERT ##### */

    public boolean insert(Context context, Media media){
        MediaDAO dao = new MediaDAO(context);
        return dao.insert(media);
    }

    public boolean insertAll(Context context, List<Media> medias){
        MediaDAO dao = new MediaDAO(context);
        return dao.insertAll(medias);
    }

    /* ##### UPDATE ##### */

    /* ##### DELETE ##### */

    /* ##### OTHER ##### */
}
