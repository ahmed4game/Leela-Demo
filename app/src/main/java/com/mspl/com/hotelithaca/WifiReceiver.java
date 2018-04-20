package com.mspl.com.hotelithaca;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by kaeman on 7/7/2017.
 */


public class WifiReceiver extends BroadcastReceiver {
    clscmdexecutor instanexecutor=new clscmdexecutor();
    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager con_manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (con_manager.getActiveNetworkInfo() != null
                && con_manager.getActiveNetworkInfo().isAvailable()
                && con_manager.getActiveNetworkInfo().isConnected()) {
            Toast.makeText(context, "Wifi connected  received", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Wifi DISconnected  received", Toast.LENGTH_SHORT).show();
        }
     /*   NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if (info != null && info.isConnected()) {
           Toast.makeText(context, "Wifi connected  received", Toast.LENGTH_SHORT).show();
           // new exececmd_asyntask().execute("");
            Log.d("kaeman","Wifi connected adbd restart   ");
        }
        else
            Toast.makeText(context, "Wifi DISconnected  received", Toast.LENGTH_SHORT).show(); */
    }


    private class exececmd_asyntask extends AsyncTask<String, String, String> {

        String varIRcmd;
        @Override
        protected String doInBackground(String... params) {
            varIRcmd=params[0];
            return varIRcmd;
        }
        protected void onPostExecute(String result) {
            instanexecutor.exec_strechmode_command();
        }
    }




}