package com.example.mudisemployee;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mudisemployee.model.BannerModel;
import com.example.mudisemployee.model.MenuModel;
import com.example.mudisemployee.model.OrderModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FirebaseRepository extends ViewModel {
    public MutableLiveData<Boolean> isTaskReady = new MutableLiveData<>(false);
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public HashMap<String, String> dishInfo = new HashMap<>();
    public ArrayList<OrderModel> orders = new ArrayList<>();
    private ArrayList<MenuModel> menuList = new ArrayList<>();
    private ArrayList<BannerModel> banner = new ArrayList<>();

    public void getDishes(List<String> list) {
        firestore.collection("Dishes").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (String name : list) {
                            for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                                MenuModel dish = snapshot.toObject(MenuModel.class);
                                if (Objects.equals(name, dish.getName())) {
                                    dishInfo.put(dish.getName(), dish.getImage());
                                    break;
                                }
                            }
                        }
                    }
                    isTaskReady.setValue(true);
                });
    }

    public void getOrders() {
        firestore.collection("Orders").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                            if(!Objects.equals(snapshot.toObject(OrderModel.class).getOrderStatus(), "CANCELED")){
                                orders.add(snapshot.toObject(OrderModel.class));
                            }

                        }
                    }
                    isTaskReady.setValue(true);
                });
    }
    public void getMenu() {
        menuList.clear();
        firestore.collection("Dishes").get()
                .addOnSuccessListener(task -> {
                    for (DocumentSnapshot documentSnapshot : task.getDocuments()) {
                        MenuModel data = documentSnapshot.toObject(MenuModel.class);
                        data.setId(documentSnapshot.getId());
                        menuList.add(data);
                    }
                    isTaskReady.setValue(true);
                })
                .addOnFailureListener(error -> {
                    Log.d("ERROR", error.getMessage());
                });
    }
    public void getBannersFromFirebase(){
        banner.clear();
        firestore.collection("Discounts").get()
                .addOnSuccessListener(task -> {
                    for (DocumentSnapshot documentSnapshot : task.getDocuments()) {
                        BannerModel data = documentSnapshot.toObject(BannerModel.class);
                        data.setId(documentSnapshot.getId());
                        banner.add(data);
                    }
                    isTaskReady.setValue(true);
                })
                .addOnFailureListener(error -> {
                    Log.d("ERROR", error.getMessage());
                });
    }

    public ArrayList<MenuModel> getMenuList() {
        isTaskReady.setValue(false);
        return menuList;
    }

    public ArrayList<BannerModel> getBanner() {
        return banner;
    }

}
