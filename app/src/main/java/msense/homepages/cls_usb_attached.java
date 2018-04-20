package msense.homepages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by kaeman on 7/29/2017.
 */

public class cls_usb_attached extends Activity
{
    private static final String ACTION_USB_ATTACHED ="android.hardware.usb.action.USB_DEVICE_ATTACHED";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent usbintent = getIntent();
        String usbaction = usbintent.getAction();
        if (ACTION_USB_ATTACHED.equals(usbaction)) {
                /* STARTING USB LISTENER */
            finish();
            Log.d(TAG, "usbaction  = " + usbaction);
        }

    }


}