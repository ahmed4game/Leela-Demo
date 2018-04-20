package usbserial.util;

import android.hardware.input.InputManager;
import android.view.MotionEvent;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by kaeman on 8/28/2017.
 */


public class Wrap_NVMouseExtensions {
    private static Method mInputManager_setCursorVisibility;
    private static int nMotionEvent_AXIS_RELATIVE_X = 0;
    private static int nMotionEvent_AXIS_RELATIVE_Y = 0;
    private static boolean nvExtensionSupported;

    //**************************************************************************
    static {
        try {
            mInputManager_setCursorVisibility = InputManager.class.getMethod("setCursorVisibility", boolean.class);

            Field fieldRelX = MotionEvent.class.getField("AXIS_RELATIVE_X");
            Field fieldRelY = MotionEvent.class.getField("AXIS_RELATIVE_Y");

            nMotionEvent_AXIS_RELATIVE_X = (Integer) fieldRelX.get(null);
            nMotionEvent_AXIS_RELATIVE_Y = (Integer) fieldRelY.get(null);

            nvExtensionSupported = true;
        } catch (Exception e) {
            nvExtensionSupported = false;
        }

      /* DO THE SAME FOR RELATIVEY */
    }
    //**************************************************************************
    public static void checkAvailable () {
        //return nvExtensionSupported;
    };

    //**************************************************************************
    public static boolean setCursorVisibility(InputManager im, boolean fVisibility) {
        try {
            mInputManager_setCursorVisibility.invoke(im, fVisibility);
        }
        catch (InvocationTargetException ite) {
            return false; }
        catch (IllegalAccessException iae)    {
            return false; }

        return true;
    }

    //**************************************************************************
    public static int getAxisRelativeX() {
        return nMotionEvent_AXIS_RELATIVE_X;
    }
    public static int getAxisRelativeY() {
        return nMotionEvent_AXIS_RELATIVE_Y;
    }
}
