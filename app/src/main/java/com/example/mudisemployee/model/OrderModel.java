package com.example.mudisemployee.model;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    private String image;
    private String time;
    private String payment;
    private Integer number;
    private List<String> meals;
    private Boolean isLate;

    public OrderModel(String image, String time, String payment, Integer number, List<String> meals, Boolean isLate){
        this.image = image;
        this.time = time;
        this.meals = meals;
        this.number = number;
        this.payment = payment;
        this.isLate = isLate;
    }


    public String getImage() {
        return image;
    }

    public String getTime() {
        return time;
    }

    public String getPayment() {
        return payment;
    }

    public Integer getNumber() {
        return number;
    }


    public List<String> getMeals() {
        return meals;
    }

    public Boolean getLate() {
        return isLate;
    }
}
