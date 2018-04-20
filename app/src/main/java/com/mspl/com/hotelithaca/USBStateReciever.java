package com.mspl.com.hotelithaca;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class USBStateReciever extends BroadcastReceiver {
    String usbStateChangeAction = "android.hardware.usb.action.USB_STATE";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String action = intent.getAction();
        Log.d("USBStateReciever", "Received Broadcast: "+action);
        if(action.equalsIgnoreCase(usbStateChangeAction)) { //Check if change in USB state
            if(intent.getExtras().getBoolean("connected")) {
                // USB was connected
                Log.d("USBStateReciever", "onReceive: connected");
            } else {
                // USB was disconnected
                Log.d("USBStateReciever", "onReceive: connected");
            }
        }
    }
}
