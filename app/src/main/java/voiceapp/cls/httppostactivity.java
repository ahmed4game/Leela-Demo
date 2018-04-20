package voiceapp.cls;
/**
 * Created by kaeman on 9/6/2017.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mspl.com.hotelithaca.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import msense.homepages.MyApplication;


public class httppostactivity extends Activity implements OnClickListener {

    TextView tvIsConnected;
    EditText etName,etCountry,etTwitter;
    Button btnPost;


    Person person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.http_post_layout);

        // get reference to the views
        tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);
        etName = (EditText) findViewById(R.id.etName);
        etCountry = (EditText) findViewById(R.id.etCountry);
        etTwitter = (EditText) findViewById(R.id.etTwitter);
        btnPost = (Button) findViewById(R.id.btnPost);

        // check if you are connected or not
        if(isConnected()){
            tvIsConnected.setBackgroundColor(0xFF00CC00);
            tvIsConnected.setText("You are conncted");
        }
        else{
            tvIsConnected.setText("You are NOT conncted");
        }

        // add click listener to Button "POST"
        btnPost.setOnClickListener(this);
        // The request code used in ActivityCompat.requestPermissions()
// and returned in the Activity's onRequestPermissionsResult()
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public class SendPostRequest extends AsyncTask<String, Void, String> {
        String texttowrite;
        protected void onPreExecute(){}

        protected String doInBackground(String... params) {
            texttowrite=params[0];
            try {

                URL url = new URL("http://172.16.63.11/HotelIthacav1/jsonlogger.php?strlog="+texttowrite); // here is your URL path

           /*     JSONObject postDataParams = new JSONObject();
                postDataParams.put("name", "abc");
                postDataParams.put("email", "abc@gmail.com");
                Log.e("params",postDataParams.toString()); */

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
              //  writer.write(getPostDataString(postDataParams));
                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

             /*       BufferedReader in=new BufferedReader(
                            new InputStreamReader(
                                    conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();*/
                   // return sb.toString();
                    return "";

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();
        }
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
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.btnPost:
               /* if(!validate())
                    Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();*/
                // call AsynTask to perform network operation on separate thread
                //new SendPostRequest().execute("http://hmkcode.appspot.com/jsonservlet");
                wifiresult1();
                break;
        }

    }

    private boolean validate(){
        if(etName.getText().toString().trim().equals(""))
            return false;
        else if(etCountry.getText().toString().trim().equals(""))
            return false;
        else if(etTwitter.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private void wifiresult1()
    {
        WifiManager wifiManager = (WifiManager) MyApplication.getAppContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);

// Level of a Scan Result
        List<ScanResult> wifiList = wifiManager.getScanResults();
        for (ScanResult scanResult : wifiList) {
            int level = WifiManager.calculateSignalLevel(scanResult.level, 5);
            //System.out.println("Level is " + level + " out of 5");
            long _startTimeMillis = SystemClock.uptimeMillis();
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(_startTimeMillis),
                    TimeUnit.MILLISECONDS.toMinutes(_startTimeMillis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(_startTimeMillis)),
                    TimeUnit.MILLISECONDS.toSeconds(_startTimeMillis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(_startTimeMillis)));
            if (scanResult.SSID.equalsIgnoreCase("guest"))
                Log.d("WifiscanResult "," BSSID " + scanResult.BSSID + " SSID " + scanResult.SSID +  " level dbm " + scanResult.level +  " uptime " + hms);

        }
    }







    public class Person {

        private String name;
        private String country;
        private String twitter;

//getters & setters....

    }

}