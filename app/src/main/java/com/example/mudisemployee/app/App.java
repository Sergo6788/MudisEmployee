package com.example.mudisemployee.app;

import android.app.Application;
import android.content.Context;

import com.example.mudisemployee.shared.SharedManager;

public class App extends Application {
    public static SharedManager sharedManager;
    public static Context appContext;
    public static int actionId = -1;

    public void onCreate(){
        super.onCreate();
        appContext = getApplicationContext();
        sharedManager = new SharedManager(getBaseContext());
    }
}
