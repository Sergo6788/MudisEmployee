package com.example.mudisemployee.model;

public class MenuModel {
    private String image;
    private String name;
    private int price;
    private String type;
    private String id;
    private boolean ready;


    public MenuModel(String image, String name, String price, String type){
        this.image = image;
        this.name = name;
        this.price = Integer.parseInt(price);
        this.type = type;
    }
    public MenuModel(){

    }
    public int getPrice(){
        return price;
    }
    public String getName(){
        return name;
    }
    public String getImage(){
        return image;
    }
    public String getType(){return type;}
    public String getId(){return id;}

    public void setId(String id){this.id = id;}


    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}
