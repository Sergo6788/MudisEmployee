package com.example.mudisemployee.shared;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedManager {
    public SharedManager(Context baseContext){
        this.sharedPreferences = baseContext.getSharedPreferences("MudisEmployee", Context.MODE_PRIVATE);
    }
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

}
