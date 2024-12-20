package com.example.mudisemployee.model;

public class FireStoreUser {
    private boolean isAdmin = true;


    public FireStoreUser (boolean isAdmin){
        this.isAdmin = isAdmin;
    }
    public boolean getIsAdmin(){
        return isAdmin;
    }
    public void setIsAdmin(boolean value){
        this.isAdmin = value;
    }

}
