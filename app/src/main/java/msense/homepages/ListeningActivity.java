package msense.homepages;


import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class ListeningActivity extends AppCompatActivity implements IVoiceControl {

	protected SpeechRecognizer sr;
	protected Context context;
	private boolean beepOff = false;
	private AudioManager audioManager;
	private int mStreamVolume = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		Toast.makeText(ListeningActivity.this, "Init",Toast.LENGTH_LONG).show();
	}
	
	// starts the service
	protected void startListening() {
		mStreamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC); // getting system volume into var for later un-muting
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
		try {
			initSpeech();
			Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			//recognizer_intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");

			//intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
			//if (!intent.hasExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE))
		 // {
				//intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,  "com.dummy");
		  //}
			Toast.makeText(ListeningActivity.this,"Started",Toast.LENGTH_SHORT).show();
			sr.startListening(intent);
		} catch(Exception ex) {
		//	Log.d("SpeechRecognitionService", "Bei der Initialisierung des SpeechRecognizers ist ein Fehler aufgetreten");
			Toast.makeText(ListeningActivity.this, "Error",Toast.LENGTH_LONG).show();
		}
	}
	
	// stops the service
	protected void stopListening() {

		if (sr != null) {
			sr.stopListening();
        	sr.cancel();
        	sr.destroy();
			Toast.makeText(ListeningActivity.this,"stopped",Toast.LENGTH_SHORT).show();
        }
		sr = null;
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mStreamVolume, 0);

	}

	protected void initSpeech() {
		//Toast.makeText(ListeningActivity.this, "Init will be started",Toast.LENGTH_LONG).show();
		if (sr == null) {
			sr = SpeechRecognizer.createSpeechRecognizer(this);
			if (!SpeechRecognizer.isRecognitionAvailable(ListeningActivity.this)) {
				//Toast.makeText(ListeningActivity.this, "Speech Recognition is not available",
						//Toast.LENGTH_LONG).show();
				finish();
			}
			sr.setRecognitionListener(VoiceRecognitionListener.getInstance());
		}
	}
	
	@Override
	public void finish() {
		stopListening();
		super.finish();
	}
	
	@Override
	protected void onStop() {
		stopListening();
		super.onStop();
	}
	
    @Override
	protected void onDestroy() {
    	if (sr != null) {
        	sr.stopListening();
        	sr.cancel();
        	sr.destroy();
        }
    	super.onDestroy();
    }
    
    @Override
    protected void onPause() {
        if(sr!=null){
            sr.stopListening();
            sr.cancel();
            sr.destroy();              

        }
        sr = null;

        super.onPause();
    }
    
    //is abstract so the inheriting classes need to implement it. Here you put your code which should be executed once a command was found
	@Override
	public abstract void processVoiceCommands(String  voiceCommands);
    
	@Override
	public void restartListeningService() {
		stopListening();
		startListening();
	}
}
