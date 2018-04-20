package com.mspl.com.hotelithaca;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import usbserial.util.Cls_iptvchannellogger;

public class MainActivity extends AppCompatActivity {
    Cls_iptvchannellogger var_IPTVchannellogger= new Cls_iptvchannellogger();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickBtn(View v)
    {

        switch (v.getId()) {
            case  R.id.btnenablewifi: {
               // new enablewifi_asyntask().execute("");
                if (!DetectConnection.checkInternetConnection(MainActivity.this)) {
                    Toast.makeText(getApplicationContext(), "No Internet!", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getApplicationContext(), "connected Internet!", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(this, "WiFi enable ", Toast.LENGTH_LONG).show();
                //Cls_iptvchannellogger var_IPTVchannellogger= new Cls_iptvchannellogger();
                //var_IPTVchannellogger.write_channel_notavl("We tried to disable and enable WIFI");
                break;
            }

            case R.id.btndisenwifi: {
                new disableenablewifi_asyntask().execute("");
                Toast.makeText(this, "Disable and enable wifi", Toast.LENGTH_LONG).show();
                break;
            }

        }

    }
String TAG="MainActivity";
    private class enablewifi_asyntask extends AsyncTask<String, String, String> {

        String varIRcmd;
        @Override
        protected String doInBackground(String... params) {
            varIRcmd=params[0];
            DetectConnection.enableConnection(MainActivity.this);
            return varIRcmd;
        }
        protected void onPostExecute(String result) {
            //DetectConnection.enableConnection(HomeActivity.this);
            Log.d(TAG,"Due to failed load page eabling wifi");
        }
    }

    private class disableenablewifi_asyntask extends AsyncTask<String, String, String> {

        String varIRcmd;
        @Override
        protected String doInBackground(String... params) {
            varIRcmd=params[0];
            DetectConnection.enabledisableConnection(MainActivity.this);
            return varIRcmd;
        }
        protected void onPostExecute(String result) {
            //DetectConnection.enableConnection(HomeActivity.this);
            Log.d(TAG,"Due to failed load page eabling wifi");
        }
    }


}
