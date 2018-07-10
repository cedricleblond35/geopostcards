package com.example.cleblond2016.geopostcards2.BO;

public class PostCard {

    private String id;

    private double latitude;
    private double longitude;
    private String title;
    private String message;
    private User user;

    private Media media;

    public PostCard() {}

    public PostCard(double latitude, double longitude, String title, String message) {
        this();
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.message = message;
    }

    public PostCard(double latitude, double longitude, String title, String message, User user) {
        this(latitude, longitude, title, message);
        this.user = user;
    }

    public PostCard(String id, double latitude, double longitude, String title, String message) {
        this(latitude, longitude, title, message);
        this.id = id;
    }

    public PostCard(String id, double latitude, double longitude, String title, String message, User user) {
        this(latitude,longitude,title,message,user);
        this.id = id;
    }

    public PostCard(String id, double latitude, double longitude, String title, String message, User user, Media media) {
        this(id,latitude,longitude,title,message,user);
        this.media = media;
    }

    /**
     * getter / setter
     */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    @Override
    public String toString() {
        return "PostCard{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
