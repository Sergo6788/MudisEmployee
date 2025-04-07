package com.example.mudisemployee.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.example.mudisemployee.app.components.BatteryReceiver;
import com.example.mudisemployee.shared.SharedManager;

public class App extends Application {
    public static SharedManager sharedManager;
    public static Context appContext;
    public static int actionId = -1;
    public static BatteryReceiver receiver;

    public void onCreate(){
        super.onCreate();
        appContext = getApplicationContext();
        sharedManager = new SharedManager(getBaseContext());
        receiver = new BatteryReceiver();
        IntentFilter batteryFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        IntentFilter chargeFilter = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
        IntentFilter chargeDisconFilter = new IntentFilter(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(receiver,batteryFilter);
        registerReceiver(receiver,chargeDisconFilter);
        registerReceiver(receiver,chargeFilter);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(receiver);
    }
}
