package com.example.mudisemployee.shared;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.mudisemployee.model.OrderModel;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class SharedManager {
    public SharedManager(Context baseContext ){
        //sharedPreferences is link to repository of saved info
        this.sharedPreferences = baseContext.getSharedPreferences("MudisEmployee", Context.MODE_PRIVATE);
        this.gson = new Gson();
    }
    private Gson gson;
    private SharedPreferences sharedPreferences;
    //check if user authorized in system
    public boolean isUserAuthorized(){
        return sharedPreferences.getBoolean("isUserAuthorized", false);
    }
    //if user authorized, change value
    public void userAuthorize(){
        sharedPreferences.edit().putBoolean("isUserAuthorized", true).apply();
    }
    //logout user
    public void userLogout(){
        sharedPreferences.edit().putBoolean("isUserAuthorized", false).apply();
    }
    //save list of finished orders
    public void saveArchive(OrderModel order, boolean isDelete) {
        ArrayList<OrderModel> listArchive = getListArchive();
        if(!isDelete){
            listArchive.add(0,order);
        }
        else listArchive.removeIf(m -> Objects.equals(m.getId(), order.getId()));
        sharedPreferences.edit().putString("listArchive", gson.toJson(listArchive)).apply();
    }
    //get list of finished orders
    public ArrayList<OrderModel> getListArchive() {
        Type listType = new TypeToken<ArrayList<OrderModel>>() {
        }.getType();
        return gson.fromJson(sharedPreferences.getString("listArchive",
                gson.toJson(new ArrayList<OrderModel>())), listType);
    }

}
