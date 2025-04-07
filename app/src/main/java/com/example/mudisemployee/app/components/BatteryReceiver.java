package com.example.mudisemployee.app.components;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.Toast;

public class BatteryReceiver extends BroadcastReceiver {
    private static int previousLvl = 100;
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            if (level == 15 && previousLvl > level) {
                Toast.makeText(context, "Low battery", Toast.LENGTH_SHORT).show();
            }
            previousLvl=level;
        } else if (Intent.ACTION_POWER_CONNECTED.equals(action)) {
            Toast.makeText(context, "Charge connected", Toast.LENGTH_SHORT).show();
        } else if (Intent.ACTION_POWER_DISCONNECTED.equals(action)) {
            Toast.makeText(context, "Charge disconnected", Toast.LENGTH_SHORT).show();
        }
    }
}
