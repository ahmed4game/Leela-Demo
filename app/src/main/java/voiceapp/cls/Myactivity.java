package voiceapp.cls;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import static android.content.ContentValues.TAG;
import static com.mspl.com.hotelithaca.BuildConfig.DEBUG;

/**
 * Created by kaeman on 5/29/2017.
 */

public class Myactivity extends Activity{

    private int mBindFlag;
    Messenger mServiceMessenger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context activityContext = getApplicationContext();
        Intent service = new Intent(activityContext, MyService.class);
        activityContext.startService(service);
        mBindFlag = Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH ? 0 : Context.BIND_ABOVE_CLIENT;
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            if (DEBUG) {
                Log.d(TAG, "onServiceConnected");} //$NON-NLS-1$

            mServiceMessenger = new Messenger(service);
            Message msg = new Message();
            msg.what = MyService.MSG_RECOGNIZER_START_LISTENING;

            try
            {
                mServiceMessenger.send(msg);
            }
            catch (RemoteException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            //if (DEBUG) {Log.d(TAG, "onServiceDisconnected");} //$NON-NLS-1$
            mServiceMessenger = null;
        }

    };
    @Override
    protected void onStart()
    {
        super.onStart();

        bindService(new Intent(this, MyService.class), mServiceConnection, mBindFlag);
    }
    @Override
    protected void onStop()
    {
        super.onStop();

        if (mServiceMessenger != null)
        {
            unbindService(mServiceConnection);
            mServiceMessenger = null;
        }
    }
}
