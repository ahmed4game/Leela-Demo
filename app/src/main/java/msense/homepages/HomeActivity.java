package msense.homepages;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.system.Os;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mspl.com.hotelithaca.AlarmReceiver;
import com.mspl.com.hotelithaca.Cls_logger;
import com.mspl.com.hotelithaca.R;
import com.mspl.com.hotelithaca.Utils;
import com.mspl.com.hotelithaca.clscmdexecutor;
import com.mspl.com.hotelithaca.msense_addour_preferences;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import usb.usbcmd.ProlificSerialDriver;
import usb.usbcmd.UsbSerialDriver;
import usb.usbcmd.UsbSerialPort;
import usb.usbcmd.UsbSerialProber;
import usbserial.util.HexDump;
import usbserial.util.SerialInputOutputManager;

public class HomeActivity extends AppCompatActivity implements RecognitionListener {


    /**
     * TCLS code Start
     **/
    private SpeechRecognizer stt;
    public static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 123;
    private TextView tvVoiceMsg;
    private FrameLayout ll_speech_main;
    private Handler handler;
    private GifView imgEventTrigger, gifVoiceAnim;
    /**
     * TCLS code End
     **/
    private WebView mtvWebView;
    private ProgressDialog progressBar;
    //private ProgressDialog progressBarwifi;
    //Cls_iptvchannellogger var_IPTVchannellogger= new Cls_iptvchannellogger();
    private Handler m_handler;
    private Runnable m_handlerTask;
    boolean webserver_reachable = false;
    private String sourecestate = "home";
    private String cmdsent = "00";
    ProgressBar loading_spinner;
    LinearLayout dim;
    private HomeWatcher mHomeWatcher = null;
    //Intent intentexception;
    protected SharedPreferences tv_test_preference;
    private String webserver_IP = "0";
    private static final String TAG = HomeActivity.class.getSimpleName();
    private static final String ACTION_USB_ATTACHED = "android.hardware.usb.action.USB_DEVICE_ATTACHED";
    private static final String ACTION_USB_DETACHED = "android.hardware.usb.action.USB_DEVICE_DETACHED";
    //boolean eventhappended5sec=false;
    private Cls_logger objCls_logger = null;
    private String varcodesent;
    private static ScheduledFuture<?> adbofflinecheck_future_thread = null;
    private static ScheduledFuture<?> sumsungudb_future_thread = null;
    private boolean adbofflinechk = false;
    private boolean usb_serialcheck = false;
    private boolean picmode_check_flag = false;


    void showInfo(String message) {
        Log.i(TAG, "showInfo: " + message);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showInfo("onCreate");
        // Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        //Setting full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.layout_homeactivity);

        loading_spinner = (ProgressBar) findViewById(R.id.loading_spinner);
        dim = (LinearLayout) findViewById(R.id.dim);
        isFirstLoader = true;
        progressDialog = new ProgressDialog(HomeActivity.this);

        /** TCLS code start**/
        GifView gifImageView = (GifView) findViewById(R.id.GifImageView);
        gifImageView.setGifImageResource(R.drawable.voicecalling);
        gifVoiceAnim = (GifView) findViewById(R.id.GifImageView);
        imgEventTrigger = (GifView) findViewById(R.id.imgEventTrigger);
        gifVoiceAnim.setGifImageResource(R.drawable.mike1);
        /** TCLS code End**/

        try {
            loadApps();
        } catch (Exception e) {
            e.printStackTrace();
            objCls_logger.new SendPostRequest().execute("Exception Loading Apps:" + e.getLocalizedMessage());

        }

        tv_test_preference = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());

        SharedPreferences.Editor editor = tv_test_preference.edit();
        editor.putBoolean("adboffline_checker", false);
        editor.putBoolean("sumsung_checker", false);
        editor.putInt("wifiendis", 0);
        editor.commit();

        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

        mtvWebView = (WebView) findViewById(R.id.activity_home_webview);

        //mtvWebView.clearCache(true);
         /*Creating intstance for websettings*/
        WebSettings webSettings = mtvWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mtvWebView.requestFocus();
        mtvWebView.setBackgroundColor(0x00000000);
        mtvWebView.addJavascriptInterface(new JsInterface_homescreen_activity(this), "homescreenpage");

        //mtvWebView.setWebViewClient(new WebViewClient());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowUniversalAccessFromFileURLs(true);

        }

//        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        mtvWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "Finished loading URL: " + url);


                try {
                    if (progressBar != null) {
                        if (progressBar.isShowing()) {
                            progressBar.dismiss();
                            progressBar = null;
                        }
                    }

                    varcodesent = "Page load " + url + " successfully";
                    objCls_logger.new SendPostRequest().execute(varcodesent);


                } catch (Exception e) {
                    m_handler.postDelayed(cancelprogressdialog1, 1000);
                    StringWriter stackTrace = new StringWriter();
                    e.printStackTrace(new PrintWriter(stackTrace));
                    objCls_logger.new SendPostRequest().execute(stackTrace.toString());

                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG, "Error description: " + description + " Error errorCode: " + errorCode + " Error failingUrl: " + failingUrl);
                //  Toast.makeText(HomeActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();

                if (failingUrl.contains("http://"))
                    m_handler.postDelayed(shownointernetmess, 1500);


                mtvWebView.loadUrl("file:///android_asset/index.html");
                try {
                    if (progressBar != null) {
                        if (progressBar.isShowing()) {
                            progressBar.dismiss();
                            progressBar = null;
                        }
                    }

                    varcodesent = "Page load " + "Error description: " + description + " Error errorCode: " + errorCode + " Error failingUrl: " + failingUrl;

                    objCls_logger.new SendPostRequest().execute(varcodesent);


                } catch (Exception e) {
                    m_handler.postDelayed(cancelprogressdialog1, 1000);
                    StringWriter stackTrace = new StringWriter();
                    e.printStackTrace(new PrintWriter(stackTrace));
                    objCls_logger.new SendPostRequest().execute(stackTrace.toString());

                }


            }
        });
        ///WEBVIEW LOADING
        loadurlwebview();
        /// Initialize voice portion
        initializeVoicecmd();

        objCls_logger = new Cls_logger();
        //// isStoragePermissionGranted();

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.RECORD_AUDIO};

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        varcodesent = "Activity oncreate executed";
        objCls_logger.new SendPostRequest().execute(varcodesent);

        // new StartUSBmanagerTask().execute("");
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        registerReceiver(mUsbReceiver, filter);
    }


    private void showSpinner() {
        loading_spinner.setVisibility(View.VISIBLE);
        loading_spinner.invalidate();
        dim.setVisibility(View.VISIBLE);
        makeUserNotTouchable();
    }

    private void makeUserNotTouchable() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void hideSpinner() {
        loading_spinner.setVisibility(View.GONE);
        loading_spinner.invalidate();
        dim.setVisibility(View.GONE);
        makeUserTouchable();
    }

    private void makeUserTouchable() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    /**
     * TCLS code Start
     **/
    private SpeechRecognizer getSpeechRecognizerobject() {
        if (stt == null) {
            stt = SpeechRecognizer.createSpeechRecognizer(HomeActivity.this, ComponentName.unflattenFromString("com.google.android.googlequicksearchbox/com.google.android.voicesearch.serviceapi.GoogleRecognitionService"));
            stt.setRecognitionListener(HomeActivity.this);
        }

        return stt;
    }

    public void stopVoiceRecognition() {
        //Log.d("mj", "voice reg stop");
        if (stt != null) {
            //   stt.cancel();
            stt.destroy();
            stt = null;
        }
        if (ll_speech_main.getVisibility() == View.VISIBLE) {
            ll_speech_main.setVisibility(View.INVISIBLE);
        }

    }


    private void startSpeechListening() {

        tvVoiceMsg.setTextColor(Color.WHITE);

        Intent recognizer_intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        // recognizer_intent.putExtra("android.speech.extra.PROMPT", "");
        String language = "en-US";
        // recognizer_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language);
        recognizer_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizer_intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 5000);
        recognizer_intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 2000);
        recognizer_intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 2000);
        recognizer_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, language);
        recognizer_intent.putExtra(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES, language);
        recognizer_intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, language);
        recognizer_intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        recognizer_intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        recognizer_intent.putExtra(RecognizerIntent.EXTRA_RESULTS, language);
        recognizer_intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{"en-GB", "en-CA", "en-IN", "en"});
        recognizer_intent.putExtra("android.speech.extras.SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS", 10000);
        recognizer_intent.putExtra("EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS", 10000);

        getSpeechRecognizerobject().startListening(recognizer_intent);


    }

    public void clearMsg(final String str) {

        //  Toast.makeText(HomeActivity.this, "clearmsg", Toast.LENGTH_SHORT).show();
        Log.i("mj", "clearmsg" + str);
        handler = new Handler();
        handler.removeCallbacks(clearMsgRunner);
        handler.postDelayed(clearMsgRunner, 2000);

    }

    Runnable clearMsgRunner = new Runnable() {
        public void run() {
            Log.i("mj", "run");
            tvVoiceMsg.setText("");
            stopVoiceRecognition();

        }
    };

    public void clearMsg() {
        tvVoiceMsg.setText("");
        if (ll_speech_main.getVisibility() == View.VISIBLE) {
            ll_speech_main.setVisibility(View.INVISIBLE);
            stopVoiceRecognition();

        }
    }

    public void clearMsgAndOpenBrowser(final String matchedText) {

        varcodesent = "Browser Voice search text " + matchedText;
        objCls_logger.new SendPostRequest().execute(varcodesent);


        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Log.d("TCT", "clear2");
                tvVoiceMsg.setText("");
                if (ll_speech_main.getVisibility() == View.VISIBLE) {
                    ll_speech_main.setVisibility(View.INVISIBLE);
                    //if(stt!=null){
                    stopVoiceRecognition();
                    try {

                        String packageName = "acr.browser.barebones";

                        if (appIsInstalled(packageName)) {
                            Intent i = HomeActivity.this.getPackageManager().getLaunchIntentForPackage(packageName);
                            i.putExtra(SearchManager.QUERY, matchedText);
                            i.setAction(Intent.ACTION_WEB_SEARCH);
                            i.setType("text/plain");
                            startActivity(i);
                        } else {
                            Toast.makeText(HomeActivity.this, "App not installed on the device.",
                                    Toast.LENGTH_LONG).show();
                            varcodesent = "Msense Browser App not installed on the device.";
                            objCls_logger.new SendPostRequest().execute(varcodesent);

                        }

                    } catch (Exception e) {
                        tvVoiceMsg.setText("Try again.");
                        clearMsg("b");
                        StringWriter stackTrace = new StringWriter();
                        e.printStackTrace(new PrintWriter(stackTrace));
                        objCls_logger.new SendPostRequest().execute(stackTrace.toString());

                    }

                }
            }
        }, 500);

    }

    public void clearMsgAndOpenInApp(final String secondWord) {

        varcodesent = "Voice search text " + secondWord;
//        state = secondWord;
        objCls_logger.new SendPostRequest().execute(varcodesent);


        handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Log.d("TCT", "clear3");
                tvVoiceMsg.setText("");

                if (ll_speech_main.getVisibility() == View.VISIBLE) {
                    ll_speech_main.setVisibility(View.INVISIBLE);
                    //if(stt!=null){
                    stopVoiceRecognition();
                    //Log.d("TCT", "secondWord");
                    mtvWebView.loadUrl("javascript:openPageFromVoice('" + secondWord + "')");
                    //  }
                }
            }
        }, 500);

    }

    @Override
    public void onReadyForSpeech(Bundle params) {

        if (ll_speech_main.getVisibility() == View.INVISIBLE || ll_speech_main.getVisibility() == View.GONE) {
            ll_speech_main.setVisibility(View.VISIBLE);
            tvVoiceMsg.setText("");
            tvVoiceMsg.setVisibility(View.GONE);
            imgEventTrigger.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onBeginningOfSpeech() {
        //Log.d("TCT", "onBeginningOfSpeech");

    }

    @Override
    public void onRmsChanged(float rms_dB) {
        // Log.d("TCT","onRmsChanged");
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        //Log.d("TCT", "onBufferReceived");
    }

    @Override
    public void onEndOfSpeech() {
        //Log.d("TCT", "onEndOfSpeech");
    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        if (partialResults != null && partialResults.containsKey(SpeechRecognizer.RESULTS_RECOGNITION)) {
            ArrayList<String> transcript = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            //Log.d("TCT", "onPartialResults : " + transcript.get(0));
            imgEventTrigger.setVisibility(View.GONE);
            tvVoiceMsg.setVisibility(View.VISIBLE);
            tvVoiceMsg.setTextColor(Color.parseColor("#49FE43"));
            // tvVoiceMsg.setText(transcript.get(0).substring(0,1).toUpperCase()+transcript.get(0).substring(1).toUpperCase());
            tvVoiceMsg.setText(transcript.get(0));

        }
    }

    @Override
    public void onError(int error) {
        String message = "";

        tvVoiceMsg.setTextColor(Color.RED);

        if (error == SpeechRecognizer.ERROR_AUDIO) message = "Audio error";
        else if (error == SpeechRecognizer.ERROR_CLIENT) message = "Client error";
        else if (error == SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS)
            message = "Insufficient permissions";
        else if (error == SpeechRecognizer.ERROR_NETWORK) message = "Network error";
        else if (error == SpeechRecognizer.ERROR_NETWORK_TIMEOUT) message = "Network timeout";
        else if (error == SpeechRecognizer.ERROR_NO_MATCH) message = "No match found";
        else if (error == SpeechRecognizer.ERROR_RECOGNIZER_BUSY) message = "Voice recognizer busy";
        else if (error == SpeechRecognizer.ERROR_SERVER) message = "Server error";
        else if (error == SpeechRecognizer.ERROR_SPEECH_TIMEOUT) message = "Speech timeout";

        //Log.d("mj", message);
        objCls_logger.new SendPostRequest().execute("Voice recognition error message: " + message);

        if (error == SpeechRecognizer.ERROR_NO_MATCH || error == SpeechRecognizer.ERROR_SPEECH_TIMEOUT) {
            imgEventTrigger.setVisibility(View.GONE);
            tvVoiceMsg.setVisibility(View.VISIBLE);
            tvVoiceMsg.setTextColor(Color.RED);
            tvVoiceMsg.setText(message);
            clearMsg("inn " + message);
            return;
        }

        if (error != SpeechRecognizer.ERROR_CLIENT && error != SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS) {
            if (stt != null) {
                //   stt.cancel();
                stt.destroy();
                stt = null;
            }
            startSpeechListening();

        }
    }

    @Override
    public void onResults(Bundle results) {
        Log.i("mj", "onResults");

        //clearMsg("inn ");
        ArrayList<String> list = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (list != null && list.size() > 0) {
            String matchedText = list.get(0).toString().trim();
            Log.i("mj", "Result :" + matchedText);
            objCls_logger.new SendPostRequest().execute("Voice recognition Result: " + matchedText);

            imgEventTrigger.setVisibility(View.GONE);
            tvVoiceMsg.setTextColor(Color.parseColor("#49FE43"));
            tvVoiceMsg.setVisibility(View.VISIBLE);
            tvVoiceMsg.setText(matchedText);
            matchedText = matchedText.toLowerCase();

            if (matchedText.contains("tv") || matchedText.contains("channel") ||
                    matchedText.contains("television")) {
                clearMsg();
                htmlpagecodehandler("tv");
                mtvWebView.post(new Runnable() {
                    @Override
                    public void run() {
                        mtvWebView.loadUrl("javascript:homepageshowing();");
                    }
                });
                tvmodelonvoicehler.postDelayed(tvmodelonvoice, 2000);
            } else if (matchedText.contains("info") || matchedText.contains("hotel")) {

                clearMsgAndOpenInApp("info hotel");
            } else if (matchedText.contains("about")) {

                clearMsgAndOpenInApp("about");
            } else if (matchedText.contains("rooms") || matchedText.contains("accommodation")) {

                clearMsgAndOpenInApp("rooms accommodation");
            } else if (matchedText.contains("dining") || matchedText.contains("restaurant")) {

                clearMsgAndOpenInApp("dining restaurant");
            } else if (matchedText.contains("meeting") || matchedText.contains("conference")) {

                clearMsgAndOpenInApp("meeting conference");
            } else if (matchedText.contains("fitness") || matchedText.contains("gym")) {

                clearMsgAndOpenInApp("fitness gym");
            } else if (matchedText.contains("pool") || matchedText.contains("swimming")) {

                clearMsgAndOpenInApp("pool swimming");
            } else if (matchedText.contains("movie") || matchedText.contains("video")) {
                if (matchedText.contains("amazon")) {
                    clearMsg();
                    applaunch("amazon");
                } else if (matchedText.contains("youtube")) {
                    clearMsg();
                    applaunch("YouTube");
                } else if (matchedText.contains("netflix")) {
                    clearMsg();
                    applaunch("netflix");
                } else {
                    clearMsgAndOpenInApp("movie video");
                }

            } else if (matchedText.contains("hungry") || matchedText.contains("food")) {

                clearMsgAndOpenInApp("hungry food");
            } else if (matchedText.contains("amazon")) {
                clearMsg();
                applaunch("amazon");
            } else if (matchedText.contains("youtube")) {
                clearMsg();
                applaunch("YouTube");
            } else if (matchedText.contains("netflix")) {
                clearMsg();
                applaunch("netflix");
            } else if (matchedText.contains("hulu")) {
                clearMsg();
                applaunch("hulu");
            } /*else if (matchedText.contains("share")) {

                clearMsgAndOpenInApp("share");
            } else if (matchedText.contains("android")) {
                clearMsg();
                applaunch("miracast");
            } */ else if (matchedText.contains("browser") || matchedText.contains("internet")) {
                clearMsg();
                applaunch("BROWSER");
            } /*else if (matchedText.contains("ios") || matchedText.contains("ipad") || matchedText.contains("iphone")) {

                clearMsgAndOpenInApp("ios ipad iphone");
            } else if (matchedText.contains("laptop")) {

                clearMsgAndOpenInApp("laptop");
            } */ else if (matchedText.contains("help")) {

                clearMsgAndOpenInApp("help");

            } else if (matchedText.contains("home")) {

                clearMsgAndOpenInApp("home");
            } else {
                clearMsgAndOpenBrowser(matchedText);
            }
        } else {
            // Toast.makeText(getApplicationContext(), "nothing Matched", Toast.LENGTH_SHORT).show();
            tvVoiceMsg.setText("");
            imgEventTrigger.setVisibility(View.GONE);
            tvVoiceMsg.setVisibility(View.VISIBLE);
            tvVoiceMsg.setTextColor(Color.RED);
            tvVoiceMsg.setText("No match found");
            clearMsg("3");
        }

    }

    /**
     * TCLS code End
     **/
    @Override
    protected void onResume() {
        super.onResume();
        showInfo("onResume State:" + sourecestate);
//        mtvWebView.loadUrl("javascript:homepageshowing();");
    }


    ScheduledExecutorService USBcheckschedulestarter = Executors.newSingleThreadScheduledExecutor();
    boolean isFirstLoader = false, isAppinForeGround;
    int MESSAGE_WAITING_TIME = 3000;

    void loadMessage() {
        showAlert();
        new Handler().postDelayed(() -> hideAlert(), MESSAGE_WAITING_TIME);
    }

    @Override
    protected void onStart() {
        super.onStart();
        isAppinForeGround = true;
        //Log.d(TAG, "onstart event");
        showInfo("onStart");
        objCls_logger.new SendPostRequest().execute("Activity OnStart");
        showInfo("OS: "+ getOSDetails());


        IntentFilter filter1 = new IntentFilter();
        filter1.addAction(Intent.ACTION_DREAMING_STARTED);
        filter1.addAction(Intent.ACTION_DREAMING_STOPPED);
        registerReceiver(screenSaverListener, filter1);

        IntentFilter filter = new IntentFilter(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter = new IntentFilter(UsbManager.ACTION_USB_DEVICE_DETACHED);
        registerReceiver(mUsbDetachReceiver, filter);

        mtvWebView.clearCache(true);

        /** Home btn press handler */
        mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
            // @Override
            public void onHomePressed() {
                sourecestate = tv_test_preference.getString("sourecestate", "tv");
                //Log.d(TAG, "inside onHomePressed Source state  = " + sourecestate);
                // Toast.makeText(HomeActivity.this," HOME key Pressed  ", Toast.LENGTH_LONG).show();

                if (sourecestate.equalsIgnoreCase("tv")) {
                    //Log.d(TAG, "onHomePressed: coming from TV");

                    new htmlkeycodehandler_task().execute("228");
                    loadMessage();

                } else {
                    //Log.d(TAG, "onHomePressed: coming from Other State");
                    mtvWebView.loadUrl("javascript:homepageshowing();");
                }
            }

            // @Override
            public void onHomeLongPressed() {
            }
        });
        mHomeWatcher.startWatch();


        usb_serialcheck = tv_test_preference.getBoolean("sumsung_checker", false);
        if (!usb_serialcheck) {
            sumsungudb_future_thread = USBcheckschedulestarter.scheduleAtFixedRate
                    (new Runnable() {
                        public void run() {
                            try {
                                SharedPreferences.Editor editor = tv_test_preference.edit();
                                editor.putBoolean("sumsung_checker", true);
                                editor.commit();
                                boolean screenstate = tv_test_preference.getBoolean("SCREENSAVERON", false);
                                long _startTimeMillis = SystemClock.uptimeMillis();
                                if (_startTimeMillis < 180000 && mSerialIoManager == null) {
                                    objCls_logger.new SendPostRequest().execute("Error Code 001 : USB CHECKER: USB ATTACH NOT DETECT");
                                }
                                objCls_logger.new SendPostRequest().execute("USB CHECKER: SCREEN SERVER STATE: " + screenstate+" OS: "+ getOSDetails()); //OS Version

                            } catch (Exception e) {
                                SharedPreferences.Editor editor = tv_test_preference.edit();
                                editor.putBoolean("sumsung_checker", false);
                                editor.commit();
                            }
                        }
                    }, 2, 5, TimeUnit.MINUTES);


        }

        adbofflinechk = tv_test_preference.getBoolean("adboffline_checker", false);
        if (!adbofflinechk) {
            adbofflinecheck_future_thread = USBcheckschedulestarter.scheduleAtFixedRate
                    (new Runnable() {
                        public void run() {
                            try {
                                SharedPreferences.Editor editor = tv_test_preference.edit();
                                editor.putBoolean("adboffline_checker", true);
                                editor.commit();
                                objCls_logger.new checkadbofflinestatus(asyncHandler).execute("adb offlie check");
                            } catch (Exception e) {
                                SharedPreferences.Editor editor = tv_test_preference.edit();
                                editor.putBoolean("adboffline_checker", false);
                                editor.commit();
                            }
                        }
                    }, 1, 5, TimeUnit.MINUTES);

        }

    }

    String getOSDetails(){
        StringBuilder builder = new StringBuilder();
        builder.append("android : ").append(Build.VERSION.RELEASE);

        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            int fieldValue = -1;

            try {
                fieldValue = field.getInt(new Object());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (fieldValue == Build.VERSION.SDK_INT) {
                builder.append(" : ").append(fieldName).append(" : ");
                builder.append("sdk=").append(fieldValue);
            }
        }

        return builder.toString();
    }

    private boolean screensaver_flag = false;
    private int DETACH_WAITING_TIME = 5000;
    BroadcastReceiver mUsbDetachReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                //Log.d(TAG, "onReceive: Detached");
                objCls_logger.new checkadbofflinestatus(asyncHandler).execute("Error Code 003 : USB CABLE: DETACHED");
                stopIoManager();
                CloseSerialport();
                SharedPreferences.Editor editor = tv_test_preference.edit();
                editor.putBoolean("USBDETACHFLAG", true);
                editor.commit();
                new Handler().postDelayed(() -> detachreboot(), DETACH_WAITING_TIME);
            }
        }
    };
    private final int ASK_FOR_REBOOT = 2;
    private final int ASK_FOR_REPORT = 12;
    private final String LAST_REBOOT_TIMESTAMP = "lastrebooot", LAST_EXCEPTION_REBOOT = "exceptionTS2", CHECK_FOR_FIRST_TIME = "second", EXCEPTION_REBOOT = "exceptionreboot";
    boolean isFirstTime, isExceptionReboot;

    boolean handlingReboot(long previoustime) {
        isFirstTime = tv_test_preference.getBoolean(CHECK_FOR_FIRST_TIME, true);
        long differences = System.currentTimeMillis() - previoustime;
        differences /= 1000; // now it's Seconds
        if (differences > (60 * 10/*Minutes*/)) {
            showInfo("diff:" + differences);
            showInfo("More than 10 minutes");
            return true;
        } else {
            if (isFirstTime) {
                showInfo("diff:" + differences);
                showInfo("This is first time");
                return true;
            } else {
                showInfo("diff:" + differences);
                return false;
            }
        }
    }

    int countVillan;

    void handlingVillan(String mtvresponse, long previoustime) {
        long differences = System.currentTimeMillis() - previoustime;

        if (differences < 500) {
            countVillan++;
            if (countVillan > 10) {
                //power off
//                setfixsoundontv("poweroff");
                setfixsoundontv("picmode");


            }
        } else {
            countVillan = 0;
            objCls_logger.new SendPostRequest().execute(mtvresponse);
        }
    }

    boolean handlingRebootOnException(long previoustime) {
        isExceptionReboot = tv_test_preference.getBoolean(EXCEPTION_REBOOT, true);
        long differences = System.currentTimeMillis() - previoustime;
        differences /= 1000; // now it's Seconds
        if (differences > (60 * 10/*Minutes*/)) {
            showInfo("diff:" + differences);
            showInfo("More than 10 minutes");
            return true;
        } else {
            if (isExceptionReboot) {
                showInfo("diff:" + differences);
                showInfo("This is first time");
                return true;
            } else {
                showInfo("diff:" + differences);
                return false;
            }
        }
    }

    void handlingMiddlewareLogging(String mwarelog, long previoustime) {
        long differences = System.currentTimeMillis() - previoustime;

        if (differences < 500) {

        } else {
            objCls_logger.new SendPostRequest().execute(mwarelog);
        }
    }

    MyCustomDialog builder;

    Handler asyncHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //What did that async task say?
            switch (msg.what) {
                case 1:
                    new exececmd_asyntask().execute("");
                    // Log.d("kaeman", "yes received");
                    break;
                case ASK_FOR_REBOOT:
                    objCls_logger.new SendPostRequest().execute("Error Code 005 : Help desk Pop-Up Appeared");
                    builder = new MyCustomDialog(HomeActivity.this, getString(R.string.info_title), getString(R.string.help_desk), "OK", null);
                    break;

                case ASK_FOR_REPORT:
                    //showInfo("Call Help desk");
                    objCls_logger.new SendPostRequest().execute("Error Code 005 : Help desk Pop-Up Appeared");
                    builder = new MyCustomDialog(HomeActivity.this, getString(R.string.info_title), getString(R.string.help_desk), "OK", null);

                    break;
            }
        }
    };
    clscmdexecutor instanexecutor = new clscmdexecutor();

    private class exececmd_asyntask extends AsyncTask<String, String, String> {
        String varIRcmd;


        @Override
        protected String doInBackground(String... params) {

            varIRcmd = params[0];
            instanexecutor.exec_strechmode_command();
            return varIRcmd;
        }

        protected void onPostExecute(String result) {
            //Log.d(TAG, "adb command executed executed");
            varcodesent = "ADBD service resetted";
            objCls_logger.new SendPostRequest().execute(varcodesent);

        }
    }

    protected void initializeVoicecmd() {
        /** TCLS code Start**/
        tvVoiceMsg = (TextView) findViewById(R.id.tv_voice_msg);
        ll_speech_main = (FrameLayout) findViewById(R.id.ll_speech_main);

        tvVoiceMsg.setVisibility(View.GONE);
        imgEventTrigger.setGifImageResource(R.drawable.listening);

        /** TCLS code End**/
        //Log.d(TAG, "TCLS voice initialize");
    }

    protected void loadurlwebview() {
        try {
            progressBar = ProgressDialog.show(HomeActivity.this, "Home page ", "Loading... Please wait");

//            webserver_IP = tv_test_preference.getString("webserverip", "172.16.63.148"); // HOTEL ITHACA
//            webserver_IP = tv_test_preference.getString("webserverip", "172.16.63.148"); // HOTEL ITHACA
            webserver_IP = tv_test_preference.getString("webserverip", "172.16.63.11"); // HOTEL ITHACA
//            http://172.16.63.11/Ithaca/index.html
            m_handler = new Handler();
            m_handlerTask = new Runnable() {

                public void run() {
                    mtvWebView.loadUrl("http://" + webserver_IP + "/itcdemo/index.html?mac=" + Utils.getMACAddress("eth0") + "&ip=" + Utils.getIPAddress(true));
//                     mtvWebView.loadUrl("file:///android_asset/index.html");
                }
            };
            m_handlerTask.run();

        } catch (NullPointerException npe) {
            //write your code here
            // startActivity(new Intent(this, Iptvhomescreen1.class));
            npe.printStackTrace();
            StringWriter stackTrace = new StringWriter();
            npe.printStackTrace(new PrintWriter(stackTrace));
            objCls_logger.new SendPostRequest().execute(stackTrace.toString());

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showInfo("onRestart");
        loadMessage();

        /*if (mSerialIoManager==null){
            if (sPort!=null) {
                mSerialIoManager=new SerialInputOutputManager(sPort,mListener);
                mExecutor.submit(mSerialIoManager);
                objCls_logger.new SendPostRequest().execute("OnRestart()-> turning IO manager ON");
            }else{
                startIoManager();
                objCls_logger.new SendPostRequest().execute("OnReStart()->fromScreenSaver->Port is null");
            }
        }*/

    }


    @Override
    protected void onPause() {
        super.onPause();
        //Log.d(TAG, "onPause event called");
        showInfo("onPause");

    }

    private void redirecttoconfig() {
        //Intent intent = new Intent(HomeActivity.this, TvChannels.class);
        Intent intent = new Intent(HomeActivity.this, msense_addour_preferences.class);
        //Log.d(TAG, "startPip(), start FloatWindowService");
        startActivity(intent);
    }

    private void redirecttotvchannels() {
        Intent intent = new Intent(HomeActivity.this, TvChannels.class);
        //Log.d(TAG, "startPip(), start FloatWindowService");
        startActivity(intent);
    }

    private void stopIoManager() {
        if (mSerialIoManager != null) {
            Log.i(TAG, "Stopping io manager ..");
            mSerialIoManager.stop();
            varcodesent = "Stopping io manager ..";
            objCls_logger.new SendPostRequest().execute(varcodesent);
            mSerialIoManager = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        objCls_logger.new SendPostRequest().execute("Activity onStop");
        showInfo("onStop");
        isAppinForeGround = false;

        /*if (mUsbReceiver != null)
            unregisterReceiver(mUsbReceiver);*/

        if (mHomeWatcher != null)
            mHomeWatcher.stopWatch();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
        showInfo("onDestroy");
        if (mUsbReceiver != null)
            unregisterReceiver(mUsbReceiver);

        if (screenSaverListener != null) {
            unregisterReceiver(screenSaverListener);
        }

        if (mUsbDetachReceiver != null) {
            unregisterReceiver(mUsbDetachReceiver);
        }

        stopIoManager();

    }


    Runnable cancelprogressdialog1 = new Runnable() {

        public void run() {
            try {
                if (progressBar != null) {
                    if (progressBar.isShowing()) {
                        progressBar.dismiss();
                        progressBar = null;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                StringWriter stackTrace = new StringWriter();
                e.printStackTrace(new PrintWriter(stackTrace));
                objCls_logger.new SendPostRequest().execute(stackTrace.toString());

            }
        }
    };
    Runnable shownointernetmess = new Runnable() {

        public void run() {
            try {

                mtvWebView.loadUrl("javascript:shownointernetmessage();");

            } catch (Exception e) {
                e.printStackTrace();
                StringWriter stackTrace = new StringWriter();
                e.printStackTrace(new PrintWriter(stackTrace));
                objCls_logger.new SendPostRequest().execute(stackTrace.toString());

            }
        }
    };


    public class JsInterface_homescreen_activity {

        Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        JsInterface_homescreen_activity(Context c) {
            mContext = c;
            //Toast.makeText(Iptvhomescreen.this, "I am initialised ", Toast.LENGTH_LONG).show();
        }

        @JavascriptInterface
        public void redirect_to_setting(String jsarg_udpurl) {
            // Toast.makeText(Iptvhomescreen.this, "App Name received:   " + jsarg_udpurl, Toast.LENGTH_LONG).show();
            //Iptvhomescreen1.this.play_udp_stream_channel(jsarg_udpurl);
            //  startActivity(new Intent(Iptvhomescreen.this, msense_addour_preferences.class));

        }

        @JavascriptInterface
        public void htmlkeycodehandler(String jsarg_udpurl) {
            new htmlkeycodehandler_task().execute(jsarg_udpurl);
        }

        @JavascriptInterface
        public void launchappfromhtml(String jsargpackage, String jsargclsname) {
            // new applanchtask().execute(jsarg_udpurl);
            new applanchtask().execute(jsargpackage, jsargclsname);
        }

        @JavascriptInterface
        public void launchappfromhtml(String jsarg_udpurl) {
            new applanchtask().execute(jsarg_udpurl);
//            new applanchtask().execute(jsargpackage,jsargclsname);
        }

        @JavascriptInterface
        public void launchvoicerecognizor(String jsarg_udpurl) {
            new voicerecognizortask().execute(jsarg_udpurl);
        }

        @JavascriptInterface
        public void volumeListener(int key) {
            //Log.d("Listener", "Listening:" + key);
            if (key == 37)
                if (sourecestate.equalsIgnoreCase("tv"))
                    setfixsoundontv("mute");
        }

    }

    private class StartUSBmanagerTask extends AsyncTask<String, String, String> {

        String varIRcmd;

        @Override
        protected String doInBackground(String... params) {
            varIRcmd = params[0];
            return varIRcmd;
        }

        protected void onPostExecute(String result) {
            startIoManager();
            //Log.d(TAG, "StartUSBmanagerTask executed");
        }
    }

    private class htmlkeycodehandler_task extends AsyncTask<String, String, String> {

        String varhtmlkeycode;
        private Activity ownerActivity;
        private Exception exceptionToBeThrown;

        @Override
        protected String doInBackground(String... params) {
            try {
                varhtmlkeycode = params[0];
                //Log.d(TAG, "htmlkeycodehandler_task called with param:" + params[0]);
                htmlpagecodehandler(varhtmlkeycode);
                return varhtmlkeycode;
            } catch (Exception e) {
                // save exception and re-thrown it then.
                exceptionToBeThrown = e;
                return varhtmlkeycode;
            }
        }

        protected void onPostExecute(String result) {
            if (exceptionToBeThrown != null) {
                objCls_logger.new SendPostRequest().execute("onPostExecute: htmlkeycodehandler has Exception");

                //Log.d(TAG, "onPostExecute: htmlkeycodehandler has Exception");
                if (isTvOn && sourecestate.equalsIgnoreCase("home"))
                    powerListener.whenMalFunction();
                StringWriter stackTrace = new StringWriter();
                exceptionToBeThrown.printStackTrace(new PrintWriter(stackTrace));

                objCls_logger.new SendPostRequest().execute(stackTrace.toString());

            }
        }
    }

    private class applanchtask extends AsyncTask<String, String, String> {

        String varjspakname;
        String varjsclassname;

        @Override
        protected String doInBackground(String... params) {
            varjspakname = params[0];
            return varjspakname;
        }

        protected void onPostExecute(String result) {
            //Log.d(TAG, "applanchtask called");
            applaunch(varjspakname);
            /*if (result.contains("|")){
                lauchanotherapp(varjspakname,varjsclassname);
            }else{
                applaunch(varjspakname);
            }*/
        }
    }

    private class voicerecognizortask extends AsyncTask<String, String, String> {

        String varhtmlkeycode;

        @Override
        protected String doInBackground(String... params) {
            varhtmlkeycode = params[0];
            return varhtmlkeycode;
        }

        protected void onPostExecute(String result) {
            voicerecogcaller(result);
            //Log.d(TAG, "voicerecognizortask called");

        }
    }

    private SerialInputOutputManager mSerialIoManager = null;
    private static UsbSerialPort sPort = null;
    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    private UsbManager mUsbManager;
    private static final String ACTION_USB_PERMISSION = "com.mspl.com.hotelithaca.USB_PERMISSION";

    private boolean Checkserialportstatus() {
        if (mSerialIoManager != null) {
            if (mSerialIoManager.getmState() == SerialInputOutputManager.State.STOPPED) {
                mExecutor.submit(mSerialIoManager);
            }
            return true;
        } else {
            startIoManager();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadMessage();
                }
            });
            return false;
        }
    }
    IRcodehandler varircodehandler= new IRcodehandler();
    private void htmlpagecodehandler(String argcode) {

        runOnUiThread(()->showToast("argCode:"+argcode));

        //AIRTE DTH:: chplus	chminus	guide	menu	uparrow		downarrow	rightarrow	leftarrow	okbtn	backbtn
        //SAMSUNG TV:: hometotv     volplus		volminus	tvtohome	poweron	mute
        //DTH:: chplus	chminus	guide	menu	uparrow	downarrow	okbtn	backbtn	1	2	3	4	5	6	7	8	9	0


        //Log.d("HTML Code", "argCode:" + argcode);
        sourecestate = tv_test_preference.getString("sourecestate", "home");
        //Log.d(TAG, "Source state  = " + sourecestate + " ArgCode:" + argcode);
        isresNotreceived = false;
        if (cmdexecutewithin300sec())
            return;

       /* if (!Checkserialportstatus())
            return;*/

        if (argcode.equalsIgnoreCase("228"))   // Go to HOME from TV Channels
        {
            cmdsent = "home";
            outBuff = new byte[]{(byte) 0x08, (byte) 0x22, (byte) 0x0a, (byte) 0x00, (byte) 0x05, (byte) 0x00, (byte) 0xC7};//HDMI1;
            mSerialIoManager.writeAsync(outBuff);
        }
        // if (argcode.equalsIgnoreCase("tv") && sourecestate.equalsIgnoreCase("home")) {   // TV icon click
        if (argcode.equalsIgnoreCase("tv")) {   // TV icon click

            cmdsent = "tv";
            isTvOn = true;
            TVIconClick();
        }

        if (argcode.equalsIgnoreCase("openconfig")) {
            redirecttoconfig();
            //Log.d("kaeman", " config page pressed ");
        }

        if (argcode.equalsIgnoreCase("179"))   // Media Pause key
        {
            // Toast.makeText(getApplicationContext(),"media pause",Toast.LENGTH_LONG).show();
            cmdsent = "Media pause";
            outBuff = new byte[]{(byte) 0x08, (byte) 0x22, (byte) 0x0a, (byte) 0x00, (byte) 0x05, (byte) 0x01, (byte) 0xC6};//HDMI2;
            mSerialIoManager.writeAsync(outBuff);

        }
        if (argcode.equalsIgnoreCase("33")) // PG+
        {
//            varircodehandler.Aiteldth_sendIRcodetoIRtac("chplus");
            varircodehandler.send_dishdth_IRcodetoIRtac("chplus");
        }
        if (argcode.equalsIgnoreCase("ok")) // PG+
        {
//            varircodehandler.Aiteldth_sendIRcodetoIRtac("chplus");
            varircodehandler.send_dishdth_IRcodetoIRtac("okbtn");
        }
        if (argcode.equalsIgnoreCase("34")) // PG-
        {
            varircodehandler.send_dishdth_IRcodetoIRtac("chminus");
        }
        if (argcode.equalsIgnoreCase("38")) {   // UP arrow
            varircodehandler.send_dishdth_IRcodetoIRtac("uparrow");
        }
        if (argcode.equalsIgnoreCase("40")) {   // Down arrow
            varircodehandler.send_dishdth_IRcodetoIRtac("downarrow");
        }
        if (argcode.equalsIgnoreCase("ok") && sourecestate.equalsIgnoreCase("tv")) {   // ok arrow

        }
        if (argcode.equalsIgnoreCase("37")) {   // left arrow

            varircodehandler.send_dishdth_IRcodetoIRtac("leftarrow");
        }
        if (argcode.equalsIgnoreCase("39")) {   // Right arrow
            varircodehandler.send_dishdth_IRcodetoIRtac("rightarrow");

        }
        if (argcode.equalsIgnoreCase("return")) {
            varircodehandler.send_dishdth_IRcodetoIRtac("bckbtn");
        }

        if (argcode.equalsIgnoreCase("1")) {
            varircodehandler.send_dishdth_IRcodetoIRtac("1");
        }

        if (argcode.equalsIgnoreCase("2")) {
            varircodehandler.send_dishdth_IRcodetoIRtac("2");
        }

        if (argcode.equalsIgnoreCase("3")) {
            varircodehandler.send_dishdth_IRcodetoIRtac("3");
        }

        if (argcode.equalsIgnoreCase("4")) {
            varircodehandler.send_dishdth_IRcodetoIRtac("4");
        }

        if (argcode.equalsIgnoreCase("5")) {
            varircodehandler.send_dishdth_IRcodetoIRtac("5");
        }

        if (argcode.equalsIgnoreCase("6")) {
            varircodehandler.send_dishdth_IRcodetoIRtac("6");
        }

        if (argcode.equalsIgnoreCase("7")) {
            varircodehandler.send_dishdth_IRcodetoIRtac("7");
        }

        if (argcode.equalsIgnoreCase("8")) {
            varircodehandler.send_dishdth_IRcodetoIRtac("8");
        }

        if (argcode.equalsIgnoreCase("9")) {
            varircodehandler.send_dishdth_IRcodetoIRtac("9");
        }

        if (argcode.equalsIgnoreCase("0")) {
            varircodehandler.send_dishdth_IRcodetoIRtac("0");
        }

        if (argcode.equalsIgnoreCase("guide")) {
            varircodehandler.send_dishdth_IRcodetoIRtac("guide");
        }

        if (argcode.equalsIgnoreCase("return")) {
            varircodehandler.send_dishdth_IRcodetoIRtac("bckbtn");
        }

        if (argcode.equalsIgnoreCase("menu")) {
            varircodehandler.send_dishdth_IRcodetoIRtac("menu");
        }

        m_soundHandler.removeCallbacks(resChecker);
        m_soundHandler.postDelayed(resChecker, 2000);
        varcodesent = "Cmd sent " + cmdsent;
        objCls_logger.new SendPostRequest().execute(varcodesent);

           /* }catch (Exception e)
            {
                StringWriter stackTrace = new StringWriter();
                e.printStackTrace(new PrintWriter(stackTrace));
                objCls_logger.new SendPostRequest().execute(stackTrace.toString());
            }*/
    }

    private void okArrow() {
        outBuff = new byte[]{(byte) 0x08, (byte) 0x22, (byte) 0x0d, (byte) 0x00, (byte) 0x00, (byte) 0x68, (byte) 0x61}; //ok
        mSerialIoManager.writeAsync(outBuff);

    }

    private void TVIconClick() {

        //Log.d(TAG, "htmlpagecodehandler: going to TV State:" + sourecestate);
        outBuff = new byte[]{(byte) 0x08, (byte) 0x22, (byte) 0x0a, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xCC};//TV;
        // if (mSerialIoManager != null)
        mSerialIoManager.writeAsync(outBuff);
    }

    private void channelDecrement() {
        outBuff = new byte[]{(byte) 0x08, (byte) 0x22, (byte) 0x03, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0xD1};//CH-;
        mSerialIoManager.writeAsync(outBuff);
    }

    private void channelIncrement() {
        outBuff = new byte[]{(byte) 0x08, (byte) 0x22, (byte) 0x03, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0xD2};//CH+;
        mSerialIoManager.writeAsync(outBuff);
    }

    private boolean appIsInstalled(String packageName) {

        PackageManager pm = getPackageManager();

        boolean appInstalled = false;

        try {

            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);

            appInstalled = true;

        } catch (PackageManager.NameNotFoundException e) {

            //Log.d("Exception", e.getMessage().toString());
            StringWriter stackTrace = new StringWriter();
            e.printStackTrace(new PrintWriter(stackTrace));

            objCls_logger.new SendPostRequest().execute(stackTrace.toString());

            appInstalled = false;

        }

        return appInstalled;

    }

    public void voicerecogcaller(String argcode) {

        /** TCLS code Start**/
        if (argcode.equalsIgnoreCase("122")) {
            startSpeechListening();
            //Log.d("Layout ", "Shown/Not");
            return;
        }
        /** TCLS code End**/
    }

    public void applaunch(String argcode) {
        try {

            if (cmdexecutewithin300sec())
                return;
            objCls_logger.new SendPostRequest().execute("applaunch " + argcode);


            if (argcode.equalsIgnoreCase("BROWSER")) {
//            Intent youtubeintent = new Intent(Intent.ACTION_VIEW);
//            youtubeintent.setData(Uri.parse("http://www.thehotelithaca.com/"));
//            startActivity(youtubeintent);
                String packageName = "acr.browser.barebones";

                if (appIsInstalled(packageName)) {

                    Intent i = this.getPackageManager().getLaunchIntentForPackage(packageName);
                    // i.putExtra(Intent.EXTRA_TEXT, "Here the text to be passed to the browser.");
                    i.setType("text/plain");
                    startActivity(i);
                } else {
                    Toast.makeText(this, "App not installed on the device.",
                            Toast.LENGTH_LONG).show();
                }
            } else {
                manipulateApps(argcode);
            }

            /*if (!argcode.equalsIgnoreCase("helpresetadb")) {
                mtvWebView.postDelayed(() -> mtvWebView.loadUrl("javascript:homepageshowing()"), 1000);
            }*/
//                mtvWebView.postDelayed(() -> mtvWebView.loadUrl("javascript:bckbtnpressed"), 1000);

        } catch (Exception e) {
            objCls_logger.new SendPostRequest().execute("Exception in AppLaunch e:" + e.getLocalizedMessage());
        }
    }

    class AppData {
        CharSequence label;
        String packageName;
    }

    //    String state = "home";
    ArrayList<AppData> apps;

    void loadApps() throws Exception {
        apps = new ArrayList<>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = getPackageManager().queryIntentActivities(i, 0);
        for (ResolveInfo ri : availableActivities) {
            AppData app = new AppData();
            app.label = ri.loadLabel(getPackageManager());
            app.packageName = ri.activityInfo.packageName;
            apps.add(app);
            Log.d(TAG, "loadApps: Label:" + app.label + " PackageName:" + app.packageName);
        }
    }

    void manipulateApps(String appName) throws Exception {
        //Log.d(TAG, "manipulateApps argname: " + appName);
        boolean isAvailable = false;
        Log.d(TAG, "manipulateApps: appName:"+appName);

        for (AppData data : apps) {
            if (data.packageName.contains(appName.toLowerCase()) || data.label.toString().toLowerCase().contains(appName)) {
                Log.d(TAG, "manipulateApps: inside forloop");
                launchIT(data.packageName);
                isAvailable = true;
                break;
            }
        }

      /*  if (!isAvailable)
            if (Build.VERSION.SDK_INT >= 24)
                if (appName.toLowerCase().contains("setting")) {
                    lauchanotherapp("com.android.tv.settings","com.android.tv.settings.MainSettings");
//
//         launchIT("com.android.tv.settings");
                }
        else
            showToast("App is not Installed");*/
    }

    void launchIT(String packageName) throws Exception {
        Log.d(TAG, "launchIT: PackageName:"+packageName);
        Intent launch = getPackageManager().getLaunchIntentForPackage(packageName);
        startActivity(launch);
    }

    public void lauchanotherapp(String pkgname, String argclasname) {
        // Toast.makeText(Iptvhomescreen.this, "pkg received:   " + pkgname, Toast.LENGTH_LONG).show();
        final Intent intent = new Intent();
        //ComponentName cName = new ComponentName("package_name","package_name.class_name");

        if (appIsInstalled(pkgname)) {
            /*if (pkgname.contains("amazon") || pkgname.contains("youtube") || pkgname.contains("netflix") || pkgname.contains("miracast"))
                state = "otherApps";*/
            ComponentName cName = new ComponentName(pkgname, argclasname);
            intent.setComponent(cName);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        } else {
            Toast.makeText(this, "App not installed on the device.",
                    Toast.LENGTH_LONG).show();
            objCls_logger.new SendPostRequest().execute("App not installed on the device " + pkgname);

        }
    }

    interface TVPowerListener {
        void whenTVPowerOn();

        void whenTVPowerOff();

        void whenMalFunction();
    }

    boolean isTvOn = false;
    long TVswitchonTS;
    private TVPowerListener powerListener = new TVPowerListener() {
        @Override
        public void whenTVPowerOn() {

            long differences = System.currentTimeMillis() - TVswitchonTS;

            if (differences < 1000) {

            } else {
                //            if (!isTvOn) {
                // < Ithaca Impl >
                objCls_logger.new SendPostRequest().execute("TV SWITCHED ON");

                //Log.d(TAG, "PowerListener#:TV ON CODE");

//            }
                isTvOn = true;
                sourecestate = "home";
//            soundhandler.postDelayed(msoundRunnable, 1000);
                mtvWebView.post(new Runnable() {
                    @Override
                    public void run() {
                        mtvWebView.loadUrl("javascript:TVmode1(0);");
                    }
                });
            }


            TVswitchonTS = System.currentTimeMillis();
        }

        @Override
        public void whenTVPowerOff() {
//            if (isTvOn) {
            // < Ithaca Impl >
            objCls_logger.new SendPostRequest().execute("TV SWITCHED OFF");
            try {
                Utils.triggerHomeLaunch();
            } catch (Exception e) {
                e.printStackTrace();
            }

//            }
            isTvOn = false;
        }

        @Override
        public void whenMalFunction() {
            asyncHandler.sendEmptyMessage(ASK_FOR_REPORT);
        }
    };

    void showAlert() {
        progressDialog.setTitle("Message");
        progressDialog.setMessage("Please Wait ...");
        progressDialog.show();
    }

    void hideAlert() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
            //Log.d(TAG, "ProgressDialog is hide isShowing:" + progressDialog.isShowing());
        }
    }

    void detachreboot() {
        screensaver_flag = tv_test_preference.getBoolean("SCREENSAVERON", false);
        isusbdetach1 = tv_test_preference.getBoolean("USBDETACHFLAG", false);
        if (screensaver_flag && isusbdetach1) {
            objCls_logger.new SendPostRequest().execute("DETACH REBOOT");
            new AlarmReceiver().exec_reboot_command();
        }
    }


    ProgressDialog progressDialog;
    boolean isManualtesting = false;
    boolean isbootcomplete1 = false;
    boolean isusbdetach1 = false;
    ProlificSerialDriver prolific;

    private void startIoManager() {
        // Toast.makeText(HomeActivity.this, "Hi no wory i been called", Toast.LENGTH_LONG).show();
        //Log.d(TAG, "Hi no wory i been called");
        final List<UsbSerialDriver> drivers =
                UsbSerialProber.getDefaultProber().findAllDrivers(mUsbManager);
        List<UsbSerialPort> ports = null;
        for (final UsbSerialDriver driver : drivers) {
            Log.d(TAG, "startIoManager: Drivers:" + driver.toString() + " Ports:" + driver.getPorts().toString() + " #####Device:" + driver.getDevice().toString());
            objCls_logger.new SendPostRequest().execute("DriverInfo:: " + driver.toString());
            objCls_logger.new SendPostRequest().execute("PortInfo:: " + driver.getPorts().toString());
            objCls_logger.new SendPostRequest().execute("DeviceInfo:: " + driver.getDevice().toString());

            ports = driver.getPorts();
            //Log.d(TAG, String.format("+ %s: %s port%s", driver, Integer.valueOf(ports.size()), ports.size() == 1 ? "" : "s"));
            objCls_logger.new SendPostRequest().execute(String.format("+ %s: %s port%s", driver, Integer.valueOf(ports.size()), ports.size() == 1 ? "" : "s"));
            // result.addAll(ports);
            //sPort=driver.();
        }
        if (drivers.size() > 0) {
            //Log.d(TAG, " Request pending intent ");
            sPort = ports.get(0);
            PendingIntent mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
            mUsbManager.requestPermission(sPort.getDriver().getDevice(), mPermissionIntent);

            // Assume thisActivity is the current activity
            int permissionCheck = ContextCompat.checkSelfPermission(HomeActivity.this,
                    ACTION_USB_PERMISSION);

            //Log.d(TAG, "permission check status " + permissionCheck);

            if (ActivityCompat.checkSelfPermission(HomeActivity.this, ACTION_USB_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
                //Log.d(TAG, "permission Not Grannted ");
            }
            SharedPreferences.Editor editor = tv_test_preference.edit();
            editor.putBoolean("retryUSB", false);
            editor.commit();
        } else {
//            asyncHandler.sendEmptyMessage(ASK_FOR_REPORT);
            objCls_logger.new SendPostRequest().execute("Error 002 : startIoManager: FAULTY USB CONNECTOR");
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent: action:" + intent.getAction());
        objCls_logger.new SendPostRequest().execute(intent.getAction());

        if (intent.getAction().equals(ACTION_USB_ATTACHED)) {
            SharedPreferences.Editor editor = tv_test_preference.edit();
            editor.putBoolean("USBDETACHFLAG", false);
            editor.commit();
            boolean screenstate = tv_test_preference.getBoolean("SCREENSAVERON", false);
            long _startTimeMillis = SystemClock.uptimeMillis();
            if (!screenstate || _startTimeMillis < 180000) {
                stopIoManager();
                CloseSerialport();
                startIoManager();
            }

        }
    }

    Runnable runretryusb = new Runnable() {
        @Override
        public void run() {
            new StartUSBmanagerTask().execute("");
        }
    };
    private Handler m_soundHandler = new Handler();

    private Handler soundhandler = new Handler();
    private Handler tvmodelonvoicehler = new Handler();
    Runnable msoundRunnable = new Runnable() {
        @Override
        public void run() {
            setfixsoundontv("50");
        }
    };
    Runnable tvmodelonvoice = new Runnable() {
        @Override
        public void run() {
            mtvWebView.loadUrl("javascript:TVmode1(1);");
        }
    };
    Runnable msoundtvRunnable = new Runnable() {
        @Override
        public void run() {
            setfixsoundontv("10");
        }
    };
    Runnable tvonoffhomechange = new Runnable() {
        @Override
        public void run() {
            if (cmdexecutewithin300sec())
                return;
            mtvWebView.loadUrl("javascript:TVmode1(0);");
        }
    };
    Runnable rnpicmodechecker = new Runnable() {
        @Override
        public void run() {
            setfixsoundontv("picmode");
        }
    };
    Runnable resChecker = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "bool value  " + isresNotreceived);
            if (!isresNotreceived) {

                Log.d(TAG, "cmd sent " + cmdsent + " RESPONSE NOT RECEIVED");
                if ((isTvOn && sourecestate.equalsIgnoreCase("home")) || isTvOn && cmdsent.equalsIgnoreCase("tv")) {
                    objCls_logger.new SendPostRequest().execute("Error Code 005 : USBNW");
                   // powerListener.whenMalFunction();

                }
            }
        }
    };




    private boolean isresNotreceived = false;
    private int usbtvoncount = 0;
    private int usbtvoffcount = 0;
    long previousTS, previousTS2;
    private final SerialInputOutputManager.Listener mListener =
            new SerialInputOutputManager.Listener() {

                @Override
                public void onRunError(Exception e) {
                    //Log.d(TAG, "Runner stopped.");
                    objCls_logger.new SendPostRequest().execute("Error Code 004 : SerialIOManager Exception for Rebooting");
//                    asyncHandler.sendEmptyMessage(ASK_FOR_REBOOT);
                }

                @Override
                public void onNewData(final byte[] data) {
//                    Log.d(TAG, "Source state =  " + sourecestate);
                    //Log.d(TAG, "cmd sent =  " + cmdsent);
                    String varres = HexDump.toHexString(data);
                    //Log.d(TAG, "onNewData#:" + varres);
                    handlingVillan(varres, previousTS);

                    previousTS = System.currentTimeMillis();
                    if (varres.equalsIgnoreCase("0200CB0552455345540300")||varres.equalsIgnoreCase("CB05524553455403000200CB0552455345540300")) {
                        usbtvoncount++;
                        m_soundHandler.postDelayed(tvonoffhomechange, 100);
                        powerListener.whenTVPowerOn();
                    }
                    if (varres.equalsIgnoreCase("00")) {
                        usbtvoffcount++;
                        powerListener.whenTVPowerOff();
                        m_soundHandler.postDelayed(tvonoffhomechange, 100);
                    }

//                    objCls_logger.new SendPostRequest().execute(varres);

//                    if ((cmdsent.equalsIgnoreCase("tv") || cmdsent.equalsIgnoreCase("CH+") || cmdsent.equalsIgnoreCase("CH-")) && varres.equalsIgnoreCase("030CF1")) {
                  //  if ((cmdsent.equalsIgnoreCase("tv") && varres.equalsIgnoreCase("030CF1"))) {
                    if ((cmdsent.equalsIgnoreCase("tv") && varres.contains("03"))) {
                        SharedPreferences.Editor editor = tv_test_preference.edit();
                        editor.putString("sourecestate", "tv");
                        editor.commit();
                        if (cmdsent.equalsIgnoreCase("tv"))
//                            setfixsoundontv("10");
                            soundhandler.postDelayed(msoundtvRunnable, 1000);
                   /*     if (cmdsent.equalsIgnoreCase("CH+") || cmdsent.equalsIgnoreCase("CH-"))
                            if (sourecestate.equalsIgnoreCase("home"))
                                fixSound10();*/
                        sourecestate = "tv";
                        cmdsent = "00"; /*state="tv";*/
                        mtvWebView.post(new Runnable() {
                            @Override
                            public void run() {
                                mtvWebView.loadUrl("javascript:TVmode1(1);");
                            }
                        });
                        isTvOn = true;
                    }


//                    if (cmdsent.equalsIgnoreCase("home") && varres.equalsIgnoreCase("030CF1")) {
                        if (cmdsent.equalsIgnoreCase("home") && varres.contains("03")) {
                        SharedPreferences.Editor editor = tv_test_preference.edit();
                        editor.putString("sourecestate", "home");
                        editor.commit();
                        sourecestate = "home";
                        cmdsent = "00";
                        soundhandler.postDelayed(msoundRunnable, 1000);
                        mtvWebView.post(new Runnable() {
                            @Override
                            public void run() {
                                mtvWebView.loadUrl("javascript:TVmode1(0);");
                            }
                        });

                        //Log.d(TAG, "HOME KEY PRESSED");
                    }
//                    if (cmdsent != "00" && varres.equalsIgnoreCase("030CF1")) {
                    if (cmdsent != "00" && varres.contains("03")) {
                        //Log.d(TAG, "cmd sent and respose received");
                        isresNotreceived = true;
                        cmdsent = "00";
                    }
//                    Log.d(TAG, "TV ON count" + usbtvoncount);

                    // Log.d(TAG, "New Data Tv response=" + HexDump.toHexString(data));
                }
            };

    private void initialiseUSBport() {

        UsbDeviceConnection connection = mUsbManager.openDevice(sPort.getDriver().getDevice());
        if (connection == null) {
            //mTitleTextView.setText("Opening device failed");
            Log.e(TAG, "Opening device failed");
            varcodesent = "Opening device failed";
            objCls_logger.new SendPostRequest().execute(varcodesent);

            return;
        }

        try {
            //Log.d(TAG, "Tring to open port");
            sPort.open(connection);
            // sPort.setParameters(115200, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
            sPort.setParameters(9600, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);


        } catch (IOException e) {
            Log.e(TAG, "Error setting up device: " + e.getMessage(), e);
            varcodesent = "Opening device failed";
            objCls_logger.new SendPostRequest().execute(varcodesent);

            //mTitleTextView.setText("Error opening device: " + e.getMessage());
            try {
                sPort.close();
            } catch (IOException e2) {
                // Ignore.
            }
            sPort = null;
            clscmdexecutor inst1 = new clscmdexecutor();
            inst1.exec_forcestop_command();
            StringWriter stackTrace = new StringWriter();
            e.printStackTrace(new PrintWriter(stackTrace));
            objCls_logger.new SendPostRequest().execute(stackTrace.toString());

            return;
        }
    }

    private boolean cmdexecutewithin300sec() {
        Long previouspresstime = tv_test_preference.getLong("previoustime", 0L);
        Date date = new Date(System.currentTimeMillis());
        long millis = date.getTime();
        long diffInMs = millis - previouspresstime;

        if (diffInMs < 300 && diffInMs > 0L) {
            return true;
        }
        SharedPreferences.Editor editor = tv_test_preference.edit();
        editor.putLong("previoustime", millis);
        editor.commit();
        return false;
    }

    private void setfixsoundontv(String argsound) {
        if (cmdexecutewithin300sec())
            return;

        /*if (!Checkserialportstatus())
            return;*/

        isresNotreceived = false;
        try {

            if (argsound.equalsIgnoreCase("50")) {
                cmdsent = "50";
                outBuff = new byte[]{(byte) 0x08, (byte) 0x22, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x32, (byte) 0xA3};//Fix sound 50;
                mSerialIoManager.writeAsync(outBuff);
            }
            if (argsound.equalsIgnoreCase("10")) {
                cmdsent = "10";
                outBuff = new byte[]{(byte) 0x08, (byte) 0x22, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x0A, (byte) 0xCB};//Fix sound 10;
                mSerialIoManager.writeAsync(outBuff);
            }
            if (argsound.equalsIgnoreCase("mute")) {
                cmdsent = "mute";
                outBuff = new byte[]{(byte) 0x08, (byte) 0x22, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xD4};//Mute
                //outBuff=new byte[] {(byte)0x58, (byte)0x80, (byte)0x01,(byte)0x01, (byte)0x80,(byte)0x5a};//power on data port;
                // outBuff=new byte[] {(byte)0x58, (byte)0x80, (byte)0x06,(byte)0x01, (byte)0x80,(byte)0x5f};//Set source from data port;
                mSerialIoManager.writeAsync(outBuff);

            }
            if (argsound.equalsIgnoreCase("vplus")) {
                cmdsent = "vplus";
                //08 22 0d 00 00 03 C6 Sleep mode
                //outBuff=new byte[] {(byte)0x08, (byte)0x22, (byte)0x0d,(byte)0x00, (byte)0x00, (byte)0x03,(byte)0xc6};//Sleep mode
                outBuff = new byte[]{(byte) 0x08, (byte) 0x22, (byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0xD4};//V+;
                //outBuff=new byte[] {(byte)0x58, (byte)0x80, (byte)0x15,(byte)0x02, (byte)0x04, (byte)0x00,(byte)0xF3};//Session activation comd;
                mSerialIoManager.writeAsync(outBuff);

            }
            if (argsound.equalsIgnoreCase("vminus")) {
                cmdsent = "vminus";
                outBuff = new byte[]{(byte) 0x08, (byte) 0x22, (byte) 0x01, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0xD3};//V-;
                // outBuff=new byte[] {(byte)0x58, (byte)0x80, (byte)0x37,(byte)0x03, (byte)0x53, (byte)0x41,(byte)0x4D,(byte)0xF3};//samsung vendor;
                mSerialIoManager.writeAsync(outBuff);

            }
            if (argsound.equalsIgnoreCase("chlist") && sourecestate.equalsIgnoreCase("tv")) {
                /*cmdsent = "chlist";
                outBuff = new byte[]{(byte) 0x08, (byte) 0x22, (byte) 0x0d, (byte) 0x00, (byte) 0x00, (byte) 0x6B, (byte) 0x5E};//Ch List;
                //outBuff=new byte[] {(byte)0x58, (byte)0x80, (byte)0x1e,(byte)0x02, (byte)0x80,(byte)0x00,(byte)0x78};//Mute;
                mSerialIoManager.writeAsync(outBuff);*/
//                varircodehandler.send_dishdth_IRcodetoIRtac();

            }
            if (argsound.equalsIgnoreCase("filmmode")) {
                cmdsent = "filmmode";
                outBuff = new byte[]{(byte) 0x08, (byte) 0x22, (byte) 0x0d, (byte) 0x00, (byte) 0x00, (byte) 0xf3, (byte) 0xd6};//source netflix;
                mSerialIoManager.writeAsync(outBuff);
            }

            //08 22 00 00 00 01 D5

            if (argsound.equalsIgnoreCase("picmode")) {
                cmdsent = "picmode";
                outBuff = new byte[]{(byte) 0x08, (byte) 0x22, (byte) 0x0d, (byte) 0x00, (byte) 0x00, (byte) 0xf4, (byte) 0xd5};//Source Amazon;
                mSerialIoManager.writeAsync(outBuff);
            }

            if (argsound.equalsIgnoreCase("poweroff")) {
                cmdsent = "poweroff";
                outBuff = new byte[]{(byte) 0x08, (byte) 0x22, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0xd5};//Power OFF;
                mSerialIoManager.writeAsync(outBuff);
            }

            m_soundHandler.removeCallbacks(resChecker);
            m_soundHandler.postDelayed(resChecker, 2000);
            varcodesent = "Cmd sent " + argsound;
            objCls_logger.new SendPostRequest().execute(varcodesent);
            Log.v(TAG, "setfixsoundontv varcodesent: " + varcodesent);
        } catch (Exception e) {
            /*Toast.makeText(HomeActivity.this, " USB to serial error encounteree ", Toast.LENGTH_LONG).show();*/
            StringWriter stackTrace = new StringWriter();
            e.printStackTrace(new PrintWriter(stackTrace));
            objCls_logger.new SendPostRequest().execute(stackTrace.toString());
            picmode_check_flag = false;

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // USB-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    //Toast.makeText(HomeActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 1123: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
                    varcodesent = "Permission: " + permissions[0] + "was " + grantResults[0];
                    objCls_logger.new SendPostRequest().execute(varcodesent);


                }

            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void CloseSerialport() {
        if (sPort != null) {
            try {
                sPort.close();
            } catch (IOException e) {
                // Ignore.
            }
            sPort = null;
        }
    }

    private final BroadcastReceiver screenSaverListener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, TAG + " received broacast intent: " + intent);
            objCls_logger.new SendPostRequest().execute("BroadCast for ScreenSaver:" + intent.getAction());
            if (intent.getAction().equals(Intent.ACTION_DREAMING_STOPPED)) {
                Log.d(TAG, "received dream stopped");
                objCls_logger.new SendPostRequest().execute("Action SCREEN SERVER STOPPED");
                stopIoManager();
                startIoManager();
                SharedPreferences.Editor editor = tv_test_preference.edit();
                editor.putBoolean("SCREENSAVERON", false);
                editor.commit();
            }
            if (intent.getAction().equals(Intent.ACTION_DREAMING_STARTED)) {
                objCls_logger.new SendPostRequest().execute("Action SCREEN SERVER STARTED");
                SharedPreferences.Editor editor = tv_test_preference.edit();
                editor.putBoolean("SCREENSAVERON", true);
                editor.commit();
                stopIoManager();
                CloseSerialport();
                Log.d(TAG, "received dream started");
            }
        }
    };


    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                /*Log.d(TAG, "onReceive: permission:"+intent.getExtras().keySet());
                for (String key:intent.getExtras().keySet())
                    Log.d(TAG, "onReceive: Key:"+key);

                Log.d(TAG, "onReceive: Device:"+intent.getStringExtra("device"));
                Log.d(TAG, "onReceive: Device:"+intent.getStringExtra("permission"));*/
                if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                    initialiseUSBport();
                    if (sPort != null) {
                        Log.i(TAG, "Starting io manager ..");
                        mSerialIoManager = new SerialInputOutputManager(sPort, mListener);
                        // if (mSerialIoManager.getState() == SerialInputOutputManager.State.STOPPED)
                        mExecutor.submit(mSerialIoManager);
                        varcodesent = "Starting io manager ..";
                        objCls_logger.new SendPostRequest().execute(varcodesent);


                    }


                } else {
                    //Log.d(TAG, "permission denied for device ");
                }

            }
        }
    };


    byte[] outBuff = null;

    private void showToast(String c) {
        Toast.makeText(this, c, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {

    }

    interface bacbButtonlistener {
        void onBackbuttonPressed();
    }

    //  AudioManager audioManager = (AudioManager) MyApplication.getAppContext().getSystemService(Context.AUDIO_SERVICE);
    int noOfTriggering = 0;
    long startTime;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == 25) {
            noOfTriggering = 0;

        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //Log.d(TAG, "dispatchKeyEvent: " + event.getKeyCode());
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        try {
            showToast("Key Pressed: "+keyCode );
            //Log.d("kaeman", "key code pressed   " + keyCode + " sourceState:" + sourecestate);
            switch (keyCode) {
                //default:

                case 4:
                    varircodehandler.send_dishdth_IRcodetoIRtac("bckbtn");
                    if (sourecestate == "tv") {
                        htmlpagecodehandler("return");
                        //Log.d(TAG, "onKeyDown: temporary 1838");
                    } else {
                        mtvWebView.loadUrl("javascript:bckbtnpressed();");
                        //Log.d(TAG, "onKeyDown: 1842");
                    }
                    //Log.d(TAG, "onKeyDown: SourceState:" + sourecestate);

                    return super.onKeyDown(keyCode, event);
                case 164:
                    setfixsoundontv("mute");
                    //Log.d("kaeman", "mute button pressed");
                    return super.onKeyDown(keyCode, event);
                case 24:
                    setfixsoundontv("vplus");
                    //setfixsoundontv("filmmode");
                    //Log.d("kaeman", "vol PLUS button pressed");
                    return super.onKeyDown(keyCode, event);
                case 25:
                    //setfixsoundontv("picmode");

                    setfixsoundontv("vminus");


                    return super.onKeyDown(keyCode, event);

                case 82:
                    setfixsoundontv("chlist");
//                    mSerialIoManager.stop();
                    //Log.d("kaeman", "Channel List btn");
                    return super.onKeyDown(keyCode, event);


            }
        } catch (Exception e) {
            // e.printStackTrace();
            StringWriter stackTrace = new StringWriter();
            e.printStackTrace(new PrintWriter(stackTrace));
            objCls_logger.new SendPostRequest().execute(stackTrace.toString());

        }
        return false;
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "External storage write Permission is granted");
                return true;
            } else {

                Log.v(TAG, "External storage write Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1123);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}

