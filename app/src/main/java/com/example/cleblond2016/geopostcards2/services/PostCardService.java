package com.example.cleblond2016.geopostcards2.services;

import android.content.Context;
import android.util.Log;

import com.example.cleblond2016.geopostcards2.BO.PostCard;
import com.example.cleblond2016.geopostcards2.DAO.PostCardDAO;
import com.example.cleblond2016.geopostcards2.utils.Conversion;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

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
        //PostCard p = new PostCard(48.0406841,-1.6841663,"Rando", "message perso");
        //return p;
        return dao.selectOneById(id);
    }

    /**
     * Selection de l'ensemble des cartes postal
     * @param context
     * @return List<PostCard>
     */
    public List<PostCard> getAllPostCards(Context context){
        PostCardDAO dao = new PostCardDAO(context);
        //return dao.selectAll();

        List<PostCard> postCards = new ArrayList<>();
        PostCard p2 = new PostCard(48.1119800, -1.6742900,"rennes", "message perso");
        PostCard p3 = new PostCard(48.0392400,-1.7053300,"Chartres-de-Bretagne", "message perso");
        postCards.add(p2);
        postCards.add(p3);


        return postCards;
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

    /**
     * Selectionner toutes les cartes postal dans une zone ayant une distance en km
     * @param context
     * @param latitude
     * @param longitude
     * @return List<PostCard>
     */
    public List<PostCard> selectAllPostCardswhithLimit(Context context, double latitude, double longitude, double limit) {
        List<PostCard> postCards = new ArrayList<>();
        List<PostCard> postCardsAll = getAllPostCards(context);
        for(PostCard postCard : postCardsAll)
        {
            double km = Conversion.calculateDistance(latitude,longitude, postCard.getLatitude(), postCard.getLongitude(), Conversion.Unit.UNIT_K);
            Log.i(TAG, "---------------------------------------------------");
            Log.i(TAG, "Distance de " + postCard.getTitle() + " est de :"+km);
            Log.i(TAG, "---------------------------------------------------");

            //Ajout des carte postal dans le tableau si la distance est plus peit ou egale à 1.00 km
            if (km <= limit)
            {
                postCards.add(postCard);
            }
        }
        Log.i(TAG, postCards.toString() );
        return postCards;
    }
}
