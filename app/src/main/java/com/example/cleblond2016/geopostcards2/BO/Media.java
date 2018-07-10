package com.example.cleblond2016.geopostcards2.BO;

public class Media {

    private String card;
    private String type;
    private String url;
    private String description;

    public Media() {}

    public Media(String card, String type, String url, String description) {
        this.type = type;
        this.url = url;
        this.description = description;
        this.card = card;
    }

    public String getCard() { return card; }

    public void setCard(String card) { this.card = card; }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
