package com.example.mudisemployee.model;

public class DiscountModel {
    private String name;
    private Integer price;
    private String date;

    public DiscountModel(String name, Integer price, String date){
        this.name = name;
        this.price = price;
        this.date = date;
    }
    private DiscountModel(){

    }
    public String getName(){
        return this.name;
    }
    public String getDate(){
        return this.date;
    }
    public Integer getPrice(){
        return this.price;
    }


}
