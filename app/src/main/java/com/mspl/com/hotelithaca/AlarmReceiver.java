package com.mspl.com.hotelithaca;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.util.Calendar;

import msense.homepages.MyApplication;


public class AlarmReceiver extends BroadcastReceiver {

	boolean webserver_reachable=false;
	
	private Handler handler_press_ok_action = new Handler();	
	protected SharedPreferences tv_test_preference;
	private String TAG="Hotel Ithaca Alarm ";

	private Cls_logger objCls_logger=new Cls_logger();
	//private boolean killMe = false;
@Override
public void onReceive(Context context, Intent intent) {
	
	//Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(context));
	
	 tv_test_preference= PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
	
  try {
	
	
	  if ("android.intent.action.startpingmanager".equals(intent.getAction())) {  		  
		 
		  new doping_and_exec_command().execute();
		  objCls_logger.new SendPostRequest().execute("boxstatus");
	  }  
	  
	  if ("android.intent.action.startalarmreceiver".equals(intent.getAction())) {  		
		  Refresh_internet_DATA();		
	  }  
	  
	  if ("android.intent.action.cancelmyalarmmanager".equals(intent.getAction())) {  
		  
		  cancelmyownalarmmanager();
	  }  
	  
	  if ("com.msense.videoview.beforestop".equals(intent.getAction())) {  
		  
		 // cancelmyownalarmmanager();
		  Beforestopplayer();
	  }  
	  
	  if ("com.msense.videoview.afterstop".equals(intent.getAction())) {  
		  
		  //cancelmyownalarmmanager();
		  afterstopplayer();
	  }  
	  
	  
	  
  	 }
  catch (Exception e) 
  		{
  			//Toast.makeText(context, "There was an error somewhere, but we still received an alarm", Toast.LENGTH_SHORT).show();
  			Log.d(TAG,"Error throwing ..");
  			e.printStackTrace();

   		}
  
 }

private void fnrebooter()
{
	////Reboot count
	  Long currentbootcount=tv_test_preference.getLong("rebootcount", 0L);	
	  Log.d(TAG,"currentbootcount ..."+currentbootcount);	
	
	  if (currentbootcount>=24)
	  {
			String tvonoff=runAsRoot();
	    	int inttvonoff = Integer.parseInt(tvonoff.trim());
	    	Log.d(TAG,"TV on/OFF state ..."+inttvonoff);
	    	if (inttvonoff==0)
	    	{
	    		SharedPreferences.Editor insiderebooteditor = tv_test_preference.edit();
	    		insiderebooteditor.putLong("rebootcount", 0L);
	    		insiderebooteditor.commit();	   		
	    	     
	    	//// Logging reboot time on File
				objCls_logger.new SendPostRequest().execute("rebooted, Currentcount   " + currentbootcount);
	    		exec_reboot_command();
	    		Log.d(TAG,"comming out of reboot loop ...");	
	    		return;
	    	}
	    	else
	    		currentbootcount=currentbootcount+1;
	    	
	  }
	  else			  
		  currentbootcount=currentbootcount+1;
	  
	  SharedPreferences.Editor editor = tv_test_preference.edit();
	  editor.putLong("rebootcount", currentbootcount);
	  editor.commit(); 
	
}

private void Beforestopplayer()
{
	
	SharedPreferences.Editor tv_command_editor_test = tv_test_preference.edit(); 
	tv_command_editor_test.putBoolean("isvideostoped", false);
	tv_command_editor_test.commit(); 
	
	handler_press_ok_action.postDelayed(r_press_ok_mess, 1500);
}

private void afterstopplayer()
{
	
	SharedPreferences.Editor tv_command_editor_test = tv_test_preference.edit(); 
	tv_command_editor_test.putBoolean("isvideostoped", true);
	tv_command_editor_test.commit(); 
	/*
	Toast.makeText(myappacontext, "afterstopplayer!!! =)",
			   Toast.LENGTH_SHORT).show(); 
	*/		   
}

Runnable r_press_ok_mess = new Runnable() {
    @Override
    public void run() {	 
    	Boolean shouldrun1 = tv_test_preference.getBoolean("isvideostoped", true);	
    	 if(shouldrun1)
         return;
    	 
    	 /* 	Toast.makeText(myappacontext, "Msense App manager app Toast!!! =)",
    			   Toast.LENGTH_LONG).show(); 
    	
    	Intent i = new Intent(myappacontext, AlertDialogActivity.class); 
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
        myappacontext.startActivity(i);
     */   
    	/*Cls_shellcmd obj_Cls_shellcmd=new Cls_shellcmd();
    	obj_Cls_shellcmd.getmediaserverID();
    	Log.d("kaeman","Runnable r_press_ok_mess Running");*/
    	return;
    }
};


private void Refresh_internet_DATA() {
	
    	Log.i(TAG, "Refresh_internet()... Starting ...");
	// get a Calendar object with current time
	 Calendar cal = Calendar.getInstance();
	 // add 5 minutes to the calendar object
	 cal.add(Calendar.MINUTE, 1);  
	 
	 Intent intent_alarm = new Intent("android.intent.action.startpingmanager");
	 //intent_alarm.putExtra("alarm_message", "O'Doyle Rules!");
	 // In reality, you would want to have a static variable for the request code instead of 192837
	 PendingIntent sender = PendingIntent.getBroadcast(MyApplication.getAppContext(), 421, intent_alarm, PendingIntent.FLAG_UPDATE_CURRENT);

	 // Get the AlarmManager service	
	 AlarmManager am = (AlarmManager)MyApplication.getAppContext().getSystemService(Context.ALARM_SERVICE);	
	 am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 1000 * 60 * 25 , sender);
	 //am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, AlarmManager.INTERVAL_FIFTEEN_MINUTES, AlarmManager.INTERVAL_FIFTEEN_MINUTES, sender);
	 
	 Log.d(TAG,"Refresh_internet()... End ...");
}

//MsenseUSBdetector obj_MsenseUSBdetector =new MsenseUSBdetector("/sys/bus/hid/drivers/generic-usb/009");
private void pinbcheckwake()
{
	  try {
	    	//webserver_reachable=InetAddress.getByName("10.10.28.2").isReachable(2000);
		  	webserver_reachable=InetAddress.getByName("192.168.2.28").isReachable(2000);	   

		  objCls_logger.new SendPostRequest().execute(TAG + " Reachable " + webserver_reachable);

		} catch (Exception e) {		
			//e.printStackTrace();
			StringWriter stackTrace = new StringWriter();
	    	e.printStackTrace(new PrintWriter(stackTrace));		
			//apperrorlogger objapperrorlogger=new apperrorlogger();
			//objapperrorlogger.writeToFile(stackTrace.toString());
		}
	    Log.d(TAG,"Web server reachable ...." + webserver_reachable);
		
}

private void cancelmyownalarmmanager()
{
	 // Get the AlarmManager service	
	 Intent intent_alarm = new Intent("android.intent.action.startpingmanager");	
	 // In reality, you would want to have a static variable for the request code instead of 192837
	 PendingIntent sender = PendingIntent.getBroadcast(MyApplication.getAppContext(), 421, intent_alarm, PendingIntent.FLAG_UPDATE_CURRENT);
	 
	// Get the AlarmManager service	
	 AlarmManager am = (AlarmManager)MyApplication.getAppContext().getSystemService(Context.ALARM_SERVICE);	
	 am.cancel(sender);
	 
	 Log.d(TAG,"my alarm manager canceled.." );
}
public String runAsRoot() {

    try {
        // Executes the command.
        Process process = Runtime.getRuntime().exec("cat /sys/class/amhdmitx/amhdmitx0/hpd_state\n");
        // Reads stdout.
        // NOTE: You can write to stdin of the command using
        //       process.getOutputStream().
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        int read;
        char[] buffer = new char[4096];
        StringBuffer output = new StringBuffer();
        while ((read = reader.read(buffer)) > 0) {
            output.append(buffer, 0, read);
        }
        reader.close();

        // Waits for the command to finish.
     
        process.destroy();
        //Log.d("msenseAppmanager", " hpd_state  " + output.toString());
        return output.toString();
    } catch (Exception e) {
    	//e.printStackTrace();
    	StringWriter stackTrace = new StringWriter();
    	e.printStackTrace(new PrintWriter(stackTrace));		
		//apperrorlogger objapperrorlogger=new apperrorlogger();
		//objapperrorlogger.writeToFile(stackTrace.toString());
    	return "error";
        //throw new RuntimeException(e);
    }/* catch (InterruptedException e) {
    	e.printStackTrace();
    	return "error";
        //throw new RuntimeException(e);
    }*/
}
public void exec_reboot_command() {
	try
		{
		Process process = Runtime.getRuntime().exec("su");
		DataOutputStream outputStream = new DataOutputStream(process.getOutputStream());     
		outputStream.writeBytes("reboot"+ "\n");
		outputStream.flush(); 
		outputStream.writeBytes("exit\n"); 
		outputStream.flush();
		SystemClock.sleep(1000);
		process.destroy();
		//var_IPTVchannellogger.writeToSDFile(TAG + " Process Exitvalue " + process.exitValue());
			objCls_logger.new SendPostRequest().execute(TAG + " Process Exitvalue " + process.exitValue());
		}
	catch (Exception e) {
		//e.printStackTrace();
		StringWriter stackTrace = new StringWriter();
    	e.printStackTrace(new PrintWriter(stackTrace));		
	//	apperrorlogger objapperrorlogger=new apperrorlogger();
		//objapperrorlogger.writeToFile(stackTrace.toString());

	} /*catch (InterruptedException e) {
		e.printStackTrace();

	}*/
}
protected void exec_clearcache_command() {
		try
			{
			//Log.d("msenseAppmanager", " I am start of cleacr cache");
			Process process = Runtime.getRuntime().exec("su");
			DataOutputStream outputStream = new DataOutputStream(process.getOutputStream());     
			outputStream.writeBytes("pm clear com.msense.iptvstbweb\n");
			outputStream.flush(); 
			outputStream.writeBytes("exit\n"); 
		    outputStream.flush(); 
		    //Log.d("msenseAppmanager", " before wait for");
			//process.waitFor();
			process.destroy();
			//Log.d("msenseAppmanager", " I am out of cleacr cache");
			}
		catch (IOException e) {
			//e.printStackTrace();
			StringWriter stackTrace = new StringWriter();
	    	e.printStackTrace(new PrintWriter(stackTrace));		
		//	apperrorlogger objapperrorlogger=new apperrorlogger();
		//	objapperrorlogger.writeToFile(stackTrace.toString());

		}
}
public void deleteCache(Context context) {
    try {
        File dir = context.getCacheDir();
        deleteDir(dir);
    } catch (Exception e) {}
}

public  boolean deleteDir(File dir) {
    if (dir != null && dir.isDirectory()) {
        String[] children = dir.list();
        for (int i = 0; i < children.length; i++) {
            boolean success = deleteDir(new File(dir, children[i]));
            if (!success) {
                return false;
            }
        }
        return dir.delete();
    }
    else if(dir!= null && dir.isFile())
        return dir.delete();
    else {
        return false;
    }
}

	private clscmdexecutor instanexecutor=new clscmdexecutor();
class doping_and_exec_command extends AsyncTask<Void, Integer, Void> 
{
		@Override
		protected Void doInBackground(Void... params) {
			
			 //pinbcheckwake();
			 //fnrebooter();
			// var_IPTVchannellogger.writeToSDFile(TAG + "Running");
			//instanexecutor.exec_strechmode_command();
			Log.d(TAG,"doping_and_exec_command Running for 25 min" );
			return null;
		
		}

}
}