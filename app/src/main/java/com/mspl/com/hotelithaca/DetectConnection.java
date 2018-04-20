package com.mspl.com.hotelithaca;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import msense.homepages.MyApplication;

/**
 * Created by kaeman on 8/1/2017.
 */


public class DetectConnection {
    public static boolean checkInternetConnection(Context context) {

        ConnectivityManager con_manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (con_manager.getActiveNetworkInfo() != null
                && con_manager.getActiveNetworkInfo().isAvailable()
                && con_manager.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean enableConnection(Context context) {

        try
        {
	         	/* WIFi Ntwork check */
            ConnectivityManager connManager = (ConnectivityManager) MyApplication.getAppContext().getApplicationContext(). getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (mWifi.isConnected()) {
                return true;
            }
            else {
                WifiManager wifi = (WifiManager) MyApplication.getAppContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                wifi.setWifiEnabled(true);
                return false;
            }
        } catch(Exception error)
        {
            Log.e("Debug", "error: " + error.getMessage(), error);
            return false;
        }
    }
    public static boolean enabledisableConnection(Context context) {

        try
        {
            /* WIFi Ntwork check */
            WifiManager wifi = (WifiManager) MyApplication.getAppContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wifi.setWifiEnabled(false);
            wifi.setWifiEnabled(true);
            return false;

        } catch(Exception error)
        {
            Log.e("Debug", "error: " + error.getMessage(), error);
            return false;
        }
    }
}