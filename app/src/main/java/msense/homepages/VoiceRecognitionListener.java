package msense.homepages;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;

public class VoiceRecognitionListener implements RecognitionListener {
	
	private static VoiceRecognitionListener instance = null;
	
	IVoiceControl listener; // This is your running activity (we will initialize it later)
	
	public static VoiceRecognitionListener getInstance() {
		if (instance == null) {
			instance = new VoiceRecognitionListener();
		}
		return instance;
	}
	
	private VoiceRecognitionListener() { }
	
	public void setListener(IVoiceControl listener) {
        this.listener = listener;
    }
	
    public void processVoiceCommands(String voiceCommands) {
        listener.processVoiceCommands(voiceCommands);
    }
	
    // This method will be executed when voice commands were found
	public void onResults(Bundle data) {


		ArrayList<String> matches = data.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
		//ArrayList<String> list = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
		Log.d("voice" , "Voice received" + matches.get(0).toString().trim());
		if (matches != null && matches.size()>0) {
			String matchedText = matches.get(0).toString().trim();
			processVoiceCommands(matchedText);
		}else{
			processVoiceCommands("Not matched");
		}
		//String[] commands = new String[matches.size()];
		//for (String command : matches) {
			//System.out.println(command);
		//}
		//commands = matches.toArray(commands);
		//processVoiceCommands(commands);
	}
	
	// User starts speaking
	public void onBeginningOfSpeech() {
		//processVoiceCommands("Starting to listen");
		Log.d("voice" , "Begin");
	}
	
	public void onBufferReceived(byte[] buffer) { }
	
	// User finished speaking
	public void onEndOfSpeech() {
		//System.out.println("Waiting for result...");
		Log.d("voice" , "End");
	}
	
	// If the user said nothing the service will be restarted
	public void onError(int error) {
		if (listener != null) {
			//Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
			Log.e("Error" , Integer.toString(error));
			//processVoiceCommands(Integer.toString(error));
			listener.restartListeningService();
		}
	}
	public void onEvent(int eventType, Bundle params) {	}
	
	public void onPartialResults(Bundle data) {
		ArrayList<String> matches = data.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
		//ArrayList<String> list = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
		Log.d("voice1" , "Voice received" + matches.get(0).toString().trim());
		if (matches != null && matches.size()>0) {
			String matchedText = matches.get(0).toString().trim();
			processVoiceCommands(matchedText);
		}else{
			processVoiceCommands("Not matched");
		}
	}
	
	public void onReadyForSpeech(Bundle params) {
		Log.d("voice" , "Ready for speech");
	}
	
	public void onRmsChanged(float rmsdB) {	}
}