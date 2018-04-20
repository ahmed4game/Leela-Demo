package com.mspl.com.hotelithaca;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import msense.homepages.MyApplication;

/**
 * Created by kaeman on 8/30/2017.
 */

public class Cls_logger  {
    String TAG = Cls_logger.this.getClass().getSimpleName();
    boolean webserver_reachable=false;
    protected SharedPreferences tv_test_preference;

    public  synchronized  void OTTboxfilelogger(String texttowrite) {
       // synchronized (this) {
            File sdcardpath1 = Environment.getExternalStorageDirectory();
            String str_today_date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            File logFile = new File(sdcardpath1.getAbsolutePath() + "/OTTlog/" + str_today_date + ".txt");
            //Log.i(TAG, "\n logFile : " + logFile);
            if (!logFile.exists()) {
                try {
                    if (logFile.getParentFile().mkdir()) {
                        if (!logFile.createNewFile())
                            Log.v(TAG, "error creating log file");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                DateFormat var1_dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date var1_date = new Date();
                String str_today_currenttime = var1_dateFormat.format(var1_date);

                BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
                buf.append(str_today_currenttime + " ---- " + texttowrite);
                buf.newLine();
                buf.flush();
                buf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
       // }
    }
    public static String shortenedStackTrace(Exception e, int maxLines) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        String[] lines = writer.toString().split("\n");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(lines.length, maxLines); i++) {
            sb.append(lines[i]).append("\n");
        }
        return sb.toString();
    }
    public  synchronized  void OTTboxerrorlogger(String texttowrite) {
        // synchronized (this) {
        File sdcardpath1 = Environment.getExternalStorageDirectory();
        String str_today_date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        File logFile = new File(sdcardpath1.getAbsolutePath() + "/OTTlog/Error_" + str_today_date + ".txt");
        //Log.i(TAG, "\n logFile : " + logFile);
        if (!logFile.exists()) {
            try {
                if (logFile.getParentFile().mkdir()) {
                    if (!logFile.createNewFile())
                        Log.v(TAG, "error creating log file");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            DateFormat var1_dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date var1_date = new Date();
            String str_today_currenttime = var1_dateFormat.format(var1_date);

            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(str_today_currenttime + " ---- " + texttowrite);
            buf.newLine();
            buf.flush();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // }
    }
    public class SendPostRequest extends AsyncTask<String, Void, String> {
        String texttowrite="null";
        protected void onPreExecute(){}

        protected String doInBackground(String... params) {

            tv_test_preference= PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
            webserver_IP = tv_test_preference.getString("webserverip", "172.16.63.11");
            texttowrite=params[0];
            Log.v(TAG,"SendPostRequest " + texttowrite);
            if (texttowrite.equalsIgnoreCase("boxstatus"))
               texttowrite= wifiresult1();
            try {
                OTTboxfilelogger(texttowrite);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Log.v(TAG,"SendPostRequest " + texttowrite);
            synchronized (this) {
                try {
                    if(isNetworkAvailable()) {
                        URL url = new URL("http://172.16.63.11/Ithaca/jsonlogger.php?strlog=" + URLEncoder.encode(texttowrite, "UTF-8")); // here is your URL path

                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                         conn.setReadTimeout(15000);
//                        conn.setConnectTimeout(2000 );
                        conn.setRequestMethod("GET");
                        conn.setDoInput(true);
                        conn.setDoOutput(true);

                        OutputStream os = conn.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(
                                new OutputStreamWriter(os, "UTF-8"));
                        writer.flush();
                        writer.close();
                        os.close();

                        int responseCode = conn.getResponseCode();

                        if (responseCode == HttpsURLConnection.HTTP_OK) {
                        } else {
                            //return new String("false : " + responseCode);
                        }
                    }else {
                        //Log.d("Debug", "Webserver NOT REACHABLE");
                        OTTboxfilelogger("Webserver NOT REACHABLE");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    StringWriter stackTrace = new StringWriter();
                    e.printStackTrace(new PrintWriter(stackTrace));
                    OTTboxfilelogger(stackTrace.toString());

                }
                return "";
            }

        }

        @Override
        protected void onPostExecute(String result) {
          //  Toast.makeText(getApplicationContext(), result,Toast.LENGTH_LONG).show();
        }
    }
    private String webserver_IP="0";

    protected String wifiresult1()
    {
        WifiManager wifiManager = (WifiManager) MyApplication.getAppContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        String strboxstatus =null;
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        // Level of a Scan Result
        List<ScanResult> wifiList = wifiManager.getScanResults();
        for (ScanResult scanResult : wifiList) {
            int level = WifiManager.calculateSignalLevel(scanResult.level, 5);
            //System.out.println("Level is " + level + " out of 5");
            long _startTimeMillis = SystemClock.uptimeMillis();
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(_startTimeMillis),
                    TimeUnit.MILLISECONDS.toMinutes(_startTimeMillis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(_startTimeMillis)),
                    TimeUnit.MILLISECONDS.toSeconds(_startTimeMillis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(_startTimeMillis)));
            if (scanResult.SSID.equalsIgnoreCase("hotel Ithaca") || scanResult.SSID.equalsIgnoreCase("guest")
                    && WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState()) == NetworkInfo.DetailedState.CONNECTED)
                strboxstatus ="BSSID " + scanResult.BSSID + " SSID " + scanResult.SSID +  " leveldbm " + scanResult.level +  " uptime " + hms;

        }
        return strboxstatus;
    }
    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    private boolean isNetworkAvailable()
    {
        tv_test_preference= PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        Long varnetcheck=tv_test_preference.getLong("isnetworkcheck", 0L);
        Date date = new Date(System.currentTimeMillis());
        long millis = date.getTime();
        long diffInMs = millis - varnetcheck;

        if (diffInMs < 3000 && diffInMs>0L)
        {
            boolean boolnetcheck=tv_test_preference.getBoolean("networkprevstat", false);
            return boolnetcheck;
        }
        SharedPreferences.Editor editor = tv_test_preference.edit();
        editor.putLong("isnetworkcheck", millis);
        editor.commit();

        try
        {
            SocketAddress sockaddr = new InetSocketAddress("172.16.63.11", 80);
            // Create an unbound socket
            Socket sock = new Socket();
            // This method will block no more than timeoutMs.
            // If the timeout occurs, SocketTimeoutException is thrown.
            int timeoutMs = 1000;   // 500 milliseconds
            sock.connect(sockaddr, timeoutMs);
            sock.close();

            editor.putBoolean("networkprevstat", true);
            editor.commit();
            return true;

        }
        catch (Exception ex){
            Log.e("Debug", "error: connecting socket");
            //ex.printStackTrace();
          /*  StringWriter stackTrace = new StringWriter();
            ex.printStackTrace(new PrintWriter(stackTrace));
            OTTboxfilelogger(stackTrace.toString());*/
            OTTboxfilelogger("error: connecting Webserver IP");
            editor.putBoolean("networkprevstat", false);
            editor.commit();
            return false;
        }

    }
    public interface AsyncResponse {
        void processFinish(String output);
    }
    public class  checkadbofflinestatus extends AsyncTask<String, Void, String> {
        String texttowrite="null";
        protected void onPreExecute(){}
        //public AsyncResponse delegate = null;
        Handler mainUIHandler;

        public checkadbofflinestatus(Handler mainUIHandler){
            this.mainUIHandler = mainUIHandler;
        }

        protected String doInBackground(String... params) {
            tv_test_preference= PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
            webserver_IP = tv_test_preference.getString("webserverip", "172.16.63.11");
            texttowrite=params[0];
            Log.v(TAG,"checkadbofflinestatus " + texttowrite);

            OTTboxfilelogger(texttowrite);
            //Log.v(TAG,"SendPostRequest " + texttowrite);
            synchronized (this) {
                try {
                    String output;
                    StringBuilder sb=null;

                    if(isNetworkAvailable()) {
                        URL url = new URL("http://"+webserver_IP+"/Ithaca/ottofflinestatus.php?strlog=" + URLEncoder.encode(texttowrite, "UTF-8")); // here is your URL path

                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        // conn.setReadTimeout(15000 /* milliseconds */);
                        conn.setConnectTimeout(2000 /* milliseconds */);
                        conn.setRequestMethod("GET");
                        conn.setDoInput(true);
                        conn.setDoOutput(true);

                        OutputStream os = conn.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(
                                new OutputStreamWriter(os, "UTF-8"));
                        writer.flush();
                        writer.close();
                        os.close();

                        int responseCode = conn.getResponseCode();

                        if (responseCode == HttpsURLConnection.HTTP_OK) {
                            try{
                            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                            sb = new StringBuilder();

                            while ((output = br.readLine()) != null) {
                                sb.append(output);
                                Log.d(TAG, "Response:"+sb.toString());
                                return sb.toString();
                            }
                            }catch (IOException e)   {

                            }

                        } else {
                            //return new String("false : " + responseCode);
                            Log.d(TAG, "HttpErrorResponse: "+responseCode);
                        }
                    }else {
                        Log.d(TAG, "ResponseCode:"+sb.toString());
                        // Log.d("Debug", "Webserver NOT REACHABLE");
                        OTTboxfilelogger("Webserver NOT REACHABLE");
                    }

                } catch (Exception e) {
                    Log.d(TAG, "ErrorResponseCode:"+e.getLocalizedMessage());
                    e.printStackTrace();
                    StringWriter stackTrace = new StringWriter();
                    e.printStackTrace(new PrintWriter(stackTrace));
                    OTTboxfilelogger(stackTrace.toString());

                }
                return "";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            //  Toast.makeText(getApplicationContext(), result,Toast.LENGTH_LONG).show();
            //return result;
            //delegate.processFinish(result);
            Message msg = Message.obtain();
            if (result.equalsIgnoreCase("yes")){
            msg.what = 1; //A public enumeration signifying success would be better.
            mainUIHandler.sendMessage(msg);
            }else if (result.equalsIgnoreCase("reboot")){
                /*msg.what = 2; //A public enumeration signifying success would be better.
                mainUIHandler.sendMessage(msg);*/
            }
            else
            {
                msg.what = 3; //A public enumeration signifying success would be better.
                mainUIHandler.sendMessage(msg);
            }
        }
    }

}