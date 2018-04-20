package voiceapp.cls;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * Created by kaeman on 6/1/2017.
 */

public class ReceiveVoicesearch extends BroadcastReceiver {

    protected SharedPreferences tv_test_preference;

    @Override
    public void onReceive(Context context, Intent intent) {

        if ("com.google.android.gms.actions.SEARCH_ACTION".equals(intent.getAction())) {

            Toast.makeText(context, "Received SEARCH_ACTION", Toast.LENGTH_LONG).show();

            String query = "";
            if (intent.getAction() != null && intent.getAction().equals("com.google.android.gms.actions.SEARCH_ACTION")) {
                query = intent.getStringExtra(SearchManager.QUERY);
                Log.d(TAG, "onCreate: " + query);
            }


        }

    }

}