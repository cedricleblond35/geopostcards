package com.example.cleblond2016.geopostcards2.utils;

import com.example.cleblond2016.geopostcards2.BO.Media;
import com.example.cleblond2016.geopostcards2.BO.PostCard;
import com.example.cleblond2016.geopostcards2.BO.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PostCardAPI {

    public void addPostCard(PostCard postCard, User user, List<Media> medias) throws JSONException {
        JSONObject jsonPostCard = new JSONObject();
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("id", user.getId());
        jsonUser.put("username", user.getUsername());
        jsonUser.put("token_id", user.getToken_id());
        jsonUser.put("email", user.getEmail());
        jsonUser.put("password", user.getPassword());
        JSONObject jsonMediaPicture = new JSONObject();
        Media mediaPicture = searchIfTypeInListMedia(medias, "PICTURE");
        if(mediaPicture != null) {
            jsonMediaPicture.put("card", null);
            jsonMediaPicture.put("type", "PICTURE");
            jsonMediaPicture.put("url", mediaPicture.getUrl());
            jsonMediaPicture.put("description", mediaPicture.getDescription());
        }
        JSONObject jsonMediaMusic = new JSONObject();
        Media mediaMusic = searchIfTypeInListMedia(medias, "MUSIC");
        if(mediaMusic != null) {
            jsonMediaMusic.put("card", null);
            jsonMediaMusic.put("type", "MUSIC");
            jsonMediaMusic.put("url", mediaMusic.getUrl());
            jsonMediaMusic.put("description", mediaMusic.getDescription());
        }
        JSONObject jsonMediaVideo = new JSONObject();
        JSONObject jsonMediaSketch = new JSONObject();

        jsonPostCard.put("id", 0);
        jsonPostCard.put("latitude", postCard.getLatitude());
        jsonPostCard.put("longitude", postCard.getLongitude());
        jsonPostCard.put("title", postCard.getTitle());
    }

    public Media searchIfTypeInListMedia(List<Media> medias, String type) {
        Media media = null;
        for(Media m : medias) {
            if(m.getType() == type){
                media = m;
            }
        }
        return media;
    }
}
