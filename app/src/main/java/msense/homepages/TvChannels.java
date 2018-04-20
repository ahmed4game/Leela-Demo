package msense.homepages;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.VideoView;

import com.mspl.com.hotelithaca.R;

import java.sql.Date;

/**
 * Created by kaeman on 3/16/2017.
 */

public class TvChannels extends Activity
        implements MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener {
    private static Context mAppContext = null;
    protected SharedPreferences tv_test_preference;
    private WebView mtvWebView;
    private static final String TAG = "TvChannels";
    private ProgressDialog progressBar;
    private VideoView videoView_vd;
    private Handler m_handler;
    private String udpurl;
    private HomeWatcher mHomeWatcher =null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        //Setting full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar = ProgressDialog.show(TvChannels.this, "IPTV TV CHANNELS ", "Loading... Please wait");
        setContentView(R.layout.activity_tvchannels);

        mAppContext = getApplicationContext();
        tv_test_preference= PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        videoView_vd = (VideoView)findViewById(R.id.tvchannelvideoView);
        mtvWebView = (WebView) findViewById(R.id.tvchannels_webview);
        //mtvWebView.clearCache(true);
	     /*Creating intstance for websettings*/
        WebSettings webSettings = mtvWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mtvWebView.requestFocus();
        mtvWebView.setBackgroundColor(0x00000000);
        mtvWebView.addJavascriptInterface(new JsInterface_TVChannels(this), "JSTVChannels");

        //mtvWebView.setWebViewClient(new WebViewClient());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        }

       // progressBar = ProgressDialog.show(Iptvhomescreen.this, "IPTV Home Page ", "Loading... Please wait");

        mtvWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "Finished loading URL: " +url);
                try {
                    if (progressBar!= null)
                    {
                        if (progressBar.isShowing()) {
                            progressBar.dismiss();
                            progressBar = null;
                        }
                    }
                } catch (Exception e) {
                    Log.d(TAG, "Finished Error: " +e.toString());
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }
        });

    //   mtvWebView.loadUrl("http://192.168.22.3/HotelIthaca/home_screen/tv.html?channel_number=1");

      mtvWebView.loadUrl("file:///android_asset/home_screen/tv.html?channel_number=1");




    }
    @Override
    protected void onStart() {
        super.onStart();
        mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
            // @Override
            public void onHomePressed() {
                // do something here...
                mtvWebView.loadUrl("javascript:tvchannelhomebtn();");
            }
            // @Override
            public void onHomeLongPressed() {
            }
        });
        mHomeWatcher.startWatch();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mHomeWatcher!=null)
            mHomeWatcher.stopWatch();
    }
    @Override
    protected void onRestart() {
        stop_video_view();
        mtvWebView.loadUrl("javascript:tvchannelhomebtn();");
        super.onRestart();
    }
    public void onPrepared(MediaPlayer mp) {
        Log.d("kaeman", "Videoview Prepared event called");
        videoView_vd.start();
        mp.setLooping(true);
    }

    private void stop_video_view()
    {
        if (videoView_vd.isPlaying())
            videoView_vd.stopPlayback();
    }
    @Override
    protected void onPause() {

        super.onPause();
    }
    private void play_udp_stream_channel(String ar_udp_url)
    {

        videoView_vd.setVideoPath(ar_udp_url);
        videoView_vd.requestFocus();
        videoView_vd.setOnPreparedListener(this);
        videoView_vd.setOnErrorListener(this);

        //new async_my_channel_tracker().execute();
        mtvWebView.requestFocus();

    }
    Runnable handlerplaych = new Runnable() {

        public void run() {

            Log.d("kaeman","handlerplaych  handler " + udpurl);
            new asyc_check_udp_avail().execute(udpurl);

        }
    };

    public class JsInterface_TVChannels {

        Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        JsInterface_TVChannels(Context c) {
            mContext = c;
            //Toast.makeText(Iptvhomescreen.this, "I am initialised ", Toast.LENGTH_LONG).show();
        }

        @JavascriptInterface
        public void bckbtntvchannel() {
            finish();
        }
        @JavascriptInterface
        public void redirect_to_activity(String jsarg_udpurl,String chname)
        {

            Long previouspresstime=tv_test_preference.getLong("previoustime", 0L);
            //getting the current time in milliseconds, and creating a Date object from it:
            Date date = new Date(System.currentTimeMillis()); //or simply new Date();
            //converting it back to a milliseconds representation:
            long millis = date.getTime();
            long diffInMs = millis - previouspresstime;


            SharedPreferences.Editor editor = tv_test_preference.edit();
            editor.putLong("previoustime", millis);
            editor.commit();

            Log.d("kaeman","Time diffrence    " + diffInMs);
            udpurl=jsarg_udpurl;
            if (diffInMs >2000)
                handlerplaych.run();
            else
            {
                m_handler.removeCallbacks(handlerplaych);
                m_handler.postDelayed(handlerplaych, 1000);
            }
        }
        @JavascriptInterface
        public void stopvideoviewjs(String jsarg_udpurl)
        {
            new stopvideoviewasyntask().execute(jsarg_udpurl);

        }
        @JavascriptInterface
        public void tvchannelpresshandler(String argstr)
        {
            new tvchannelpresshandler_asyntask().execute(argstr);
        }
        @JavascriptInterface
        public void PIPChannelchanger(String argstr)
        {
           // Toast.makeText(TvChannels.this, "Channel packageName :   " + argstr, Toast.LENGTH_LONG).show();
            new PIPChannelchanger_asyntask().execute(argstr);
        }
        @JavascriptInterface
        public void PIPFull(String argstr)
        {
            new PIPFull_asyntask().execute(argstr);
        }

    }
    public boolean onError(MediaPlayer mp, int what, int extra) {

        Log.d(TAG, "OnError - Error code: "+ what +" Extra code: "+extra);

        switch (what){
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                stop_video_view();
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                stop_video_view();
                break;
            default:
                stop_video_view();
                break;
        }
        Log.d("kaeman", "Videoview onError End");
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        Log.d("kaeman","key code" + keyCode);

        switch (keyCode)
        {
            //default:
            case 4:												//Back button --keycode_back
                mtvWebView.loadUrl("javascript:tvchannelbackbtn();");
                Log.d("kaeman","Bck button pressed on remote");
                return true;
            case 24:
               // sendIRcodetoIRtac("volplus");
                return true;
            case 25:
               // sendIRcodetoIRtac("volminus");
                return true;
            case 82:												//Back button --keycode_back
                mtvWebView.loadUrl("javascript:showchlist();");
                return true;

        }

        return super.onKeyDown(keyCode, event);

    }

    private void sendtvchannels_hdmiin(String chname1)
    {
        Log.d(TAG, "TVPIP " +chname1);



        //	 Toast.makeText(Iptvhomescreen.this, "Channel packageName :   " + chname1, Toast.LENGTH_LONG).show();
    }

    private class PIPChannelchanger_asyntask extends AsyncTask<String, String, String> {

        String varIRcmd;
        @Override
        protected String doInBackground(String... params) {
            varIRcmd=params[0];
            return varIRcmd;
        }
        protected void onPostExecute(String result) {
           // TVChannelIRcodetoIRtac(result);
        }
    }
    private class PIPFull_asyntask extends AsyncTask<String, String, String> {

        String varIRcmd;
        @Override
        protected String doInBackground(String... params) {
            varIRcmd=params[0];
            return varIRcmd;
        }
        protected void onPostExecute(String result) {
            MakePIPFULL(result);
        }
    }
    private class tvchannelpresshandler_asyntask extends AsyncTask<String, String, String> {

        String varIRcmd;
        @Override
        protected String doInBackground(String... params) {
            varIRcmd=params[0];
            return varIRcmd;
        }
        protected void onPostExecute(String result) {
            //sendSerialcmdtotv("hometotv");
            sendtvchannels_hdmiin(result);
            // lauchanotherapp("org.apache.android.media","com.msense.hdmiin.HDMIINActivity");
        }
    }
    private class stopvideoviewasyntask extends AsyncTask<String, String, String> {
        String a;
        @Override
        protected String doInBackground(String... params) {
            a=params[0];
            return a;
        }
        protected void onPostExecute(String result) {
            stop_video_view();
        }
    }
    private class asyc_check_udp_avail extends AsyncTask<String, String, String> {
      //  udpport_packet_available udptest = new udpport_packet_available();
        String fromweburl;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            fromweburl = params[0];
            return fromweburl;
        }
			/*	@Override
				protected void onProgressUpdate(Integer... values) {
					super.onProgressUpdate(values);
				}*/

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            play_udp_stream_channel(result);
        }

    }

    private void MakePIPFULL(String chname) {
     /*   Intent Fserviceintent = new Intent(TvChannels.this, FloatWindowService.class);
        stopService(Fserviceintent); */

     /*   Intent activityintent1 = new Intent();
        activityintent1.setClass(TvChannels.this, FullActivity.class);
        activityintent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activityintent1.putExtra("source", -1);
        activityintent1.putExtra("internal", true);
        activityintent1.putExtra("FROM_ACTIVITY_SOURCE", "PIPFULL");
        startActivity(activityintent1);*/



     /*   Intent activityintent1 = new Intent();
        activityintent1.setClass(TvChannels.this, com.amlogic.osdoverlay.SettingsActivity.class);
        activityintent1.putExtra("source", -1);
        activityintent1.putExtra("internal", true);
        activityintent1.putExtra("FROM_ACTIVITY_SOURCE", "PIPFULL");
        startActivity(activityintent1);*/
    }


    private void redirecttofull()
    {
       /* Intent intent = new Intent(TvChannels.this, com.amlogic.osdoverlay.SettingsActivity.class);
        intent.putExtra("source", -1);
        intent.putExtra("internal", true);
        intent.putExtra("FROM_ACTIVITY_SOURCE", "PIP");
        Log.d(TAG, "startPip(), start FloatWindowService");
        startActivity(intent);*/
    }
}