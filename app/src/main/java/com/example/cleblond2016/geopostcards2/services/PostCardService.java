package com.example.cleblond2016.geopostcards2.services;

import android.content.Context;

import com.example.cleblond2016.geopostcards2.BO.PostCard;
import com.example.cleblond2016.geopostcards2.DAO.PostCardDAO;

import java.util.List;

public class PostCardService {

    private static PostCardService instance;

    public static PostCardService getInstance() {
        if(instance == null) {
            instance = new PostCardService();
        }
        return instance;
    }

    private PostCardService() { }

    /* ##### SELECT ##### */

    public PostCard getPostCardById(Context context, String id){
        PostCardDAO dao = new PostCardDAO(context);
        return dao.selectOneById(id);
    }

    public List<PostCard> getAllPostCards(Context context){
        PostCardDAO dao = new PostCardDAO(context);
        return dao.selectAll();
    }

    /* ##### INSERT ##### */

    public boolean insertPostCard(Context context, PostCard postCard){
        PostCardDAO dao = new PostCardDAO(context);
        return dao.insert(postCard);
    }

    public boolean insertAllPostCards(Context context, List<PostCard> postCards){
        PostCardDAO dao = new PostCardDAO(context);
        return dao.insertAll(postCards);
    }

    /* ##### UPDATE ##### */

    /* ##### DELETE ##### */

    /* ##### OTHER ##### */
}
