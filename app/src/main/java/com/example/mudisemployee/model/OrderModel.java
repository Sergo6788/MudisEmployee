package com.example.mudisemployee.model;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    private int totalAmount;
    private List<MenuModel> orderMenu;
    private String orderDate;
    private String orderStatus;
    private String paymentMethod;
    private String id;
    private String uid;

    public OrderModel(List<MenuModel> orderMenu, String orderDate, String paymentMethod, String uid, String orderStatus){
        orderMenu.forEach(menuModel -> {
            totalAmount += menuModel.getPrice();
        });
        this.orderMenu = orderMenu;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.paymentMethod = paymentMethod;
        this.uid = uid;
    }
    public OrderModel(){
    }


    public List<MenuModel> getOrderMenu() {
        return orderMenu;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public String getUid() {
        return uid;
    }

    public String getId() {
        return id;
    }
    public void setId(String id){this.id = id;}

    public void setStatus(String status){
        this.orderStatus = status;
    }

}

