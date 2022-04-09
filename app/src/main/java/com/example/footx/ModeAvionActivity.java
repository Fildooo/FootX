package com.example.footx;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;


public class ModeAvionActivity extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(ModeAvionON(context)) {
            Toast.makeText(context, "Le mode avion est activé ! Désactivez-le pour continuer", Toast.LENGTH_SHORT).show();
        }
    }

    private static boolean ModeAvionON(Context context) {
        return Settings.System.getInt(context.getContentResolver(),Settings.Global.AIRPLANE_MODE_ON,0)!=0;
    }
}
