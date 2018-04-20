package voiceapp.cls;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mspl.com.hotelithaca.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaeman on 5/29/2017.
 */

public class SrActivity extends Activity{
    protected SpeechRecognizer sr;
    TextView textView;
    Button button;
    String TAG="SrActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sractivity);


        textView = (TextView) findViewById(R.id.srtextView);
        button = (Button) findViewById(R.id.srbutton);

        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new MySpeechRecognitionListener());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
           /*     intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,SrActivity.this.getPackageName());*/
                Log.d(TAG, "EXTRA_CALLING_PACKAGE  " + SrActivity.this.getPackageName());

                Boolean sravail=sr.isRecognitionAvailable(SrActivity.this);

                PackageManager pm = getPackageManager();
                List activities = pm.queryIntentActivities(new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
                // retrieving data from string list array in for loop
                for (int i=0;i < activities.size();i++)
                {
                    Log.i("Value of element "+i,activities.get(i).toString());
                }

                if (activities.size() == 0) {
                    textView.setEnabled(false);
                    textView.setText("Voice recognizer is not available in your device");
                    Toast.makeText(SrActivity.this, "Voice recognizer is not available in your device",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sravail)
                    sr.startListening(intent);

   /*             Intent intent1 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                intent1.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);
                intent1.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,SrActivity.this.getPackageName());
                String pkg = "com.google.android.googlequicksearchbox";
                String cls = "com.google.android.voicesearch.intentapi.IntentApiActivity";
                intent1.setComponent(new ComponentName(pkg, cls));
                startActivityForResult(intent1,9000); */

               // Toast.makeText(SrActivity.this, "recognizer available  " + b, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "recognizer available  " + sravail);




            }
        });
    }

    private class MySpeechRecognitionListener implements RecognitionListener {
        @Override
        public void onReadyForSpeech(Bundle bundle) {
            Toast.makeText(SrActivity.this, "Ready", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(int i) {
            Toast.makeText(SrActivity.this, "Error " + i, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResults(Bundle bundle) {
            String str = "";
            ArrayList data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (int i = 0 ; i < data.size(); i++) {
                str += data.get(i);
            }

            textView.setText(str);
        }
        @Override
        public void onBeginningOfSpeech()
        {
            // speech input will be processed, so there is no need for count down anymore
      /*      if (mIsCountDownOn)
            {
                mIsCountDownOn = false;
                mNoSpeechCountDown.cancel();
            }*/
            Log.d(TAG, "onBeginingOfSpeech"); //$NON-NLS-1$
        }

        @Override
        public void onBufferReceived(byte[] buffer)
        {

        }

        @Override
        public void onEndOfSpeech()
        {
            Log.d(TAG, "onEndOfSpeech"); //$NON-NLS-1$
        }



        @Override
        public void onEvent(int eventType, Bundle params)
        {
            Log.d(TAG, "onEvent");
        }

        @Override
        public void onPartialResults(Bundle partialResults)
        {
            Log.d(TAG, "onPartialResults");
        }


        @Override
        public void onRmsChanged(float rmsdB)
        {
            Log.d(TAG, "onRmsChanged");
        }


    }


}
