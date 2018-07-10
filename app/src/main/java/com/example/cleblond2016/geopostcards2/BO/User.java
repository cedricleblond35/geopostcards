package com.example.cleblond2016.geopostcards2.BO;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;
    private String username;
    private String token_id;
    private String email;
    private String password;

    private List<PostCard> postCards;

    /**
     * CONSTRUCTEUR
     */
    public User() {}

    public User(String username, String token_id, String email, String password) {
        this();
        this.username = username;
        this.token_id = token_id;
        this.email = email;
        this.password = password;
    }

    public User(int id, String username, String token_id, String email, String password) {
        this(username,token_id,email,password);
        this.id = id;
    }

    public User(String username, String token_id, String email, String password, ArrayList<PostCard> postCards) {
        this(username,token_id,email,password);
        this.postCards = postCards;
    }

    public User(int id, String username, String token_id, String email, String password, ArrayList<PostCard> postCards) {
        this(username,token_id,email,password,postCards);
        this.id = id;
    }

    /**
     * GETTER / SETTER
     * @return
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", token_id='" + token_id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
