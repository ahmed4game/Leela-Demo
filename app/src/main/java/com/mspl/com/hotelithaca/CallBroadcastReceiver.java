package com.mspl.com.hotelithaca;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import usb.usbcmd.UsbSerialDriver;
import usb.usbcmd.UsbSerialPort;
import usb.usbcmd.UsbSerialProber;
import usbserial.util.HexDump;
import usbserial.util.SerialInputOutputManager;

import static android.content.ContentValues.TAG;

/**
 * Created by kaeman on 7/5/2017.
 */


public class CallBroadcastReceiver extends BroadcastReceiver {
    private static UsbSerialPort sPort = null;
    private UsbManager mUsbManager;
    private SerialInputOutputManager mSerialIoManager= null;
    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    byte[] outBuff = null;
    Context receivercontext;
    private static final String ACTION_USB_PERMISSION ="com.android.example.USB_PERMISSION";
    @Override
    public void onReceive(Context context, Intent intent) {
        int volume = (Integer)intent.getExtras().get("android.media.EXTRA_VOLUME_STREAM_VALUE");
        Log.i("Tag", "Action : "+ intent.getAction() + " / volume : "+volume);
        receivercontext=context;
      /*  Intent broadcastintent = new Intent();
        intent.setAction("msense.vol.receiver");
        context.sendBroadcast(broadcastintent);*/

        mUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);

        if (mSerialIoManager == null) {
            startIoManager();
        }



    }
    private void initialiseUSBport() {

        UsbDeviceConnection connection = mUsbManager.openDevice(sPort.getDriver().getDevice());
        if (connection == null) {
            //mTitleTextView.setText("Opening device failed");
            Log.e(TAG, "Opening device failed");
            return;
        }

        try {
            sPort.open(connection);
            sPort.setParameters(9600, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);


        } catch (IOException e) {
            Log.e(TAG, "Error setting up device: " + e.getMessage(), e);
            //mTitleTextView.setText("Error opening device: " + e.getMessage());
            try {
                sPort.close();
            } catch (IOException e2) {
                // Ignore.
            }
            sPort = null;
            return;
        }
    }
    private void startIoManager() {
        //Toast.makeText(HomeActivity.this, "Hi no wory i been called", Toast.LENGTH_LONG).show();
        final List<UsbSerialDriver> drivers =
                UsbSerialProber.getDefaultProber().findAllDrivers(mUsbManager);
        List<UsbSerialPort> ports=null;
        for (final UsbSerialDriver driver : drivers) {
            ports = driver.getPorts();
            Log.d(TAG, String.format("+ %s: %s port%s",
                    driver, Integer.valueOf(ports.size()), ports.size() == 1 ? "" : "s"));
            // result.addAll(ports);
            //sPort=driver.();
        }
        if (drivers.size()>0) {
            sPort = ports.get(0);
            initialiseUSBport();
            if (sPort != null) {
                Log.i(TAG, "Starting io manager ..");
                mSerialIoManager = new SerialInputOutputManager(sPort, mListener);
                mExecutor.submit(mSerialIoManager);
            }
            outBuff = new byte[]{(byte) 0x08, (byte) 0x22, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x32, (byte) 0xA3};//Fix sound 50;
            mSerialIoManager.writeAsync(outBuff);
            Log.d(TAG, "receiver command cmd sent ");

        }
        else
            Log.e(TAG, "No drivers found from receiver");

    }
    private final SerialInputOutputManager.Listener mListener =
            new SerialInputOutputManager.Listener() {

                @Override
                public void onRunError(Exception e) {
                    Log.d(TAG, "Runner stopped.");
                }

                @Override
                public void onNewData(final byte[] data) {
                    // Log.d(TAG, "Source state =  " + sourecestate);
                    //   Log.d(TAG, "cmd sent =  " + cmdsent);

                   // String varres= HexDump.toHexString(data);
                     Log.d(TAG, "New Data Tv response=" + HexDump.toHexString(data));
                }
            };


    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {

                if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                    initialiseUSBport();
                    if (sPort != null) {
                        Log.i(TAG, "Starting io manager ..");
                        mSerialIoManager = new SerialInputOutputManager(sPort, mListener);
                        mExecutor.submit(mSerialIoManager);
                    }
                    outBuff = new byte[]{(byte) 0x08, (byte) 0x22, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x32, (byte) 0xA3};//Fix sound 50;
                    mSerialIoManager.writeAsync(outBuff);
                    Log.d(TAG, "receiver command cmd sent ");

                } else {
                    Log.d(TAG, "permission denied for device " );
                }

            }
        }
    };
}