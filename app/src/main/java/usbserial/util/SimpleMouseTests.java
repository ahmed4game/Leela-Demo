package usbserial.util;

import android.app.Activity;
import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;

/**
 * Created by kaeman on 8/28/2017.
 */


public class SimpleMouseTests extends Activity {

    int gnAPILevel;
    InputManager inputManager;
    boolean gfPtrVisibility;

    //***** For reflection checking of NVIDIA mouse extensions
    private static boolean gf_NVMouseExtensions;

    static {
        try {
            Wrap_NVMouseExtensions.checkAvailable();
            gf_NVMouseExtensions = true;
        }
        catch (Throwable t) {
            gf_NVMouseExtensions = false;
        }
    }

    //**************************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gnAPILevel = Build.VERSION.SDK_INT;
        gfPtrVisibility = true;
        inputManager = null;

        if (gnAPILevel >= Build.VERSION_CODES.JELLY_BEAN)  {
            inputManager = (InputManager) getSystemService(Context.INPUT_SERVICE);
        }

        setMouseVisibility(gfPtrVisibility);
    }

    //**************************************************************************
    @Override
    public void onResume() {

        super.onResume();
      //  setMouseVisibility(gfPtrVisibility);
    }

    //**************************************************************************
    public void setMouseVisibility(boolean fVisibility) {
        if (! gf_NVMouseExtensions)
            return;
        gfPtrVisibility = fVisibility;
        Wrap_NVMouseExtensions.setCursorVisibility(inputManager, gfPtrVisibility);
    }

    //**************************************************************************
    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        int nGetAction = event.getActionMasked();

   /*     if ((nSourceBits & InputDevice.SOURCE_MOUSE) == 0) {
            return super.dispatchGenericMotionEvent(event);
        }*/

        if (gf_NVMouseExtensions) {
            float flRelativeX =
                    event.getAxisValue(Wrap_NVMouseExtensions.getAxisRelativeX(), 0);
            float flRelativeY =
                    event.getAxisValue(Wrap_NVMouseExtensions.getAxisRelativeY(), 0);
        }

        return true;
    }
}