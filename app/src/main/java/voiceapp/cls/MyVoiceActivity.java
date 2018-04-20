package voiceapp.cls;

import android.app.Activity;
import android.app.SearchManager;
import android.os.Bundle;
import android.util.Log;

import com.mspl.com.hotelithaca.R;


/**
 * Created by kaeman on 5/31/2017.
 */

public class MyVoiceActivity extends Activity {

    String TAG=MyVoiceActivity.this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sractivity);
        String query = "";
        if (getIntent().getAction() != null && getIntent().getAction().equals("com.google.android.gms.actions.SEARCH_ACTION")) {
            query = getIntent().getStringExtra(SearchManager.QUERY);
            Log.d(TAG, "onCreate: " + query);
        }
        finish();
    }


    }
