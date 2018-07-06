package com.geopostcards.cleblond2016.geopostcards.BO;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "postcards", foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user"))
public class PostCard {

    @PrimaryKey
    @NonNull
    private String id;

    private long latitude;
    private long longitude;
    private String title;
    private String message;
    private User user;

    @Ignore
    private Media media;

    public PostCard() {
    }

    public PostCard(String id, long latitude, long longitude, String title, String message, User user) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.message = message;
        this.user = user;
    }

    public PostCard(String id, long latitude, long longitude, String title, String message, User user, Media media) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.message = message;
        this.user = user;
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

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
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
