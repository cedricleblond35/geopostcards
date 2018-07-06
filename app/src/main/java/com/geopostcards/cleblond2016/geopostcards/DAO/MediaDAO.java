package com.geopostcards.cleblond2016.geopostcards.DAO;

import android.arch.persistence.room.Query;

import com.geopostcards.cleblond2016.geopostcards.BO.Media;
import com.geopostcards.cleblond2016.geopostcards.BO.PostCard;

import java.util.List;

public abstract class MediaDAO extends BaseDAO<Media> {

    @Query("SELECT * FROM medias")
    public abstract List<PostCard> getAllMedias();

    @Query("SELECT * FROM medias WHERE card = :id")
    public abstract PostCard getMediaById(int id);
}
