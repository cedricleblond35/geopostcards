package com.geopostcards.cleblond2016.geopostcards.DAO;

import android.arch.persistence.room.Query;

import com.geopostcards.cleblond2016.geopostcards.BO.PostCard;
import com.geopostcards.cleblond2016.geopostcards.BO.User;

import java.util.List;

public abstract class PostCardDAO implements BaseDAO<PostCard> {

    @Query("SELECT * FROM postcards")
    public abstract List<PostCard> getAllPostCards();

    @Query("SELECT * FROM postcards WHERE id = :id")
    public abstract PostCard getPostCardById(int id);

}
