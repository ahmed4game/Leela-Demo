package com.mspl.com.hotelithaca;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import msense.homepages.MyApplication;


public class BootCompletedIntentReceiver extends BroadcastReceiver {  
	
	protected SharedPreferences tv_test_preference;
    private Cls_logger objCls_logger=new Cls_logger();

 @Override  
 public void onReceive(Context context, Intent intent) {
     tv_test_preference= PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
     SharedPreferences.Editor editor = tv_test_preference.edit();
     editor.putLong("longboottimevalue", System.currentTimeMillis());
     editor.putBoolean("SCREENSAVERON", false);
     editor.putBoolean("flagbootcomplte", true);
     editor.commit();
  if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {  
	  
	  //Toast.makeText(context, "Start of On boot completed", Toast.LENGTH_LONG).show();
      objCls_logger.new SendPostRequest().execute("OTT BOOT_COMPLETED");
	  exec_strechmode_command();
	  
	 // Toast.makeText(context, "End of On boot completed", Toast.LENGTH_LONG).show();
	  
 
  }  
  
 }  
 
 
 protected void exec_strechmode_command() {
 	try
  {
      Process process = Runtime.getRuntime().exec("su");
      DataOutputStream outputStream = new DataOutputStream(process.getOutputStream()); 
      DataInputStream inputStream = new DataInputStream(process.getInputStream());

      //outputStream.writeBytes(command + "\n");
      outputStream.writeBytes("echo 1 > /sys/class/video/screen_mode" + "\n");
      outputStream.writeBytes("setprop services.adb.tcp.port 5555" + "\n");
      outputStream.writeBytes("stop adbd" + "\n");
      outputStream.writeBytes("start adbd" + "\n");
      //outputStream.writeBytes("chmod 777 /dev/ttyUSB0" + "\n");
      outputStream.flush();

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
