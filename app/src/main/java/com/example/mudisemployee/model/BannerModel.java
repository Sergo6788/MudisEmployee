package com.example.mudisemployee.model;

public class BannerModel {
    private String image;
    private String title;
    private String description;
    private String date;
    private String id;

    public BannerModel(String image, String title, String description, String date){
        this.date=date;
        this.description=description;
        this.title=title;
        this.image=image;
    }
    public BannerModel() {

    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
