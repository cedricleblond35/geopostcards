package com.geopostcards.cleblond2016.geopostcards.DAO;

import com.geopostcards.cleblond2016.geopostcards.BO.Media;
import com.geopostcards.cleblond2016.geopostcards.BO.PostCard;
import com.geopostcards.cleblond2016.geopostcards.BO.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {

    public static List<User> generateUsers() {
        List<User> users = new ArrayList<>();
        User user1 = new User("Vulkain", "sh42fh5s4h6s4h6r4","vulkain@gmail.com", "pass1");
        User user2 = new User("cedricleblond35", "qergqerg684qergqerg","cedricleblond35@gmail.com", "pass2");

        users.add(user1);
        users.add(user2);

        return users;
    }

    public static List<PostCard> generatePostCards() {
        List<PostCard> postCards = new ArrayList<>();
        List<Long> coords = new ArrayList<>();
        coords.add(Math.round(69.5645458));
        coords.add(Math.round(244.54768));
        PostCard postCard1 = new PostCard(coords.get(0),coords.get(1),"Card1","Hehe si ça c'est pas être peinard ...", generateUsers().get(1));
        PostCard postCard2 = new PostCard(coords.get(1),coords.get(0),"Card2","Vive les vacs !!!!", generateUsers().get(0));

        postCards.add(postCard1);
        postCards.add(postCard2);

        return postCards;
    }

    public static List<Media> generateMediaForPostCards() {
        List<Media> medias = new ArrayList<>();
        Media media1 = new Media("Image","http://imagetrash.gouv/1234", "What the fuck ?!?!?!",generatePostCards().get(0));
        Media media2 = new Media("Image","http://imagetrash.gouv/6789", "Ha ouais carrément ?!?!?!",generatePostCards().get(1));

        return medias;
    }
}
