package com.mspl.com.hotelithaca;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by kaeman on 7/7/2017.
 */

public class clscmdexecutor {

    public void exec_strechmode_command() {
        try
        {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(process.getOutputStream());
            DataInputStream inputStream = new DataInputStream(process.getInputStream());

            outputStream.writeBytes("setprop services.adb.tcp.port 5555" + "\n");
            outputStream.writeBytes("stop adbd" + "\n");
            outputStream.writeBytes("start adbd" + "\n");
            //outputStream.writeBytes("chmod 777 /dev/ttyUSB0" + "\n");
            outputStream.flush();
            Log.d("kaeman","Command of adbd executed");
            outputStream.writeBytes("exit\n");
            outputStream.flush();
            process.waitFor();
        }
        catch (IOException e)
        {
            try {
                throw new Exception(e);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        catch (InterruptedException e)
        {
            try {
                throw new Exception(e);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }
    public void exec_forcestop_command() {
        try
        {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(process.getOutputStream());
            DataInputStream inputStream = new DataInputStream(process.getInputStream());

            outputStream.writeBytes("am force-stop com.mspl.com.hotelithaca" + "\n");
            outputStream.flush();
            Log.d("kaeman","Command of adbd executed");
            outputStream.writeBytes("exit\n");
            outputStream.flush();
            process.waitFor();
        }
        catch (IOException e)
        {
            try {
                throw new Exception(e);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        catch (InterruptedException e)
        {
            try {
                throw new Exception(e);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }
}
