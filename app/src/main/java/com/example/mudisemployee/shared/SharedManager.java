package com.example.mudisemployee.shared;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.mudisemployee.model.OrderModel;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class SharedManager {
    public SharedManager(Context baseContext ){
        this.sharedPreferences = baseContext.getSharedPreferences("MudisEmployee", Context.MODE_PRIVATE);
        this.gson = new Gson();
    }
    private Gson gson;
    private SharedPreferences sharedPreferences;

    public boolean isUserAuthorized(){
        return sharedPreferences.getBoolean("isUserAuthorized", false);
    }
    public void userAuthorize(){
        sharedPreferences.edit().putBoolean("isUserAuthorized", true).apply();
    }
    public void userLogout(){
        sharedPreferences.edit().putBoolean("isUserAuthorized", false).apply();
    }
    public void saveArchive(OrderModel order, boolean isDelete) {
        ArrayList<OrderModel> listArchive = getListArchive();
        if(!isDelete){
            listArchive.add(0,order);
        }
        else
            listArchive.removeIf(m -> Objects.equals(m.getNumber(), order.getNumber()));
        sharedPreferences.edit().putString("listArchive", gson.toJson(listArchive)).apply();
    }

    public ArrayList<OrderModel> getListArchive() {
        Type listType = new TypeToken<ArrayList<OrderModel>>() {
        }.getType();
        return gson.fromJson(sharedPreferences.getString("listArchive",
                gson.toJson(new ArrayList<OrderModel>())), listType);
    }

}
