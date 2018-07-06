package com.geopostcards.cleblond2016.geopostcards.BO;

public class Media {

    private String type;
    private String url;
    private String descrioption;
    private PostCard postCard;

    public Media() {    }

    public Media(String type, String url, String descrioption, PostCard postCard) {
        this.type = type;
        this.url = url;
        this.descrioption = descrioption;
        this.postCard = postCard;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescrioption() {
        return descrioption;
    }

    public void setDescrioption(String descrioption) {
        this.descrioption = descrioption;
    }

    public PostCard getPostCard() {
        return postCard;
    }

    public void setPostCard(PostCard postCard) {
        this.postCard = postCard;
    }
}
