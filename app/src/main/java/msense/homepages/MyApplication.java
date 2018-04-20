package msense.homepages;


import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import com.mspl.com.hotelithaca.Cls_logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;



public class MyApplication extends Application{

    private static Context context;
    private Cls_logger objCls_logger=new Cls_logger();
    
    // uncaught exception handler variable
    private UncaughtExceptionHandler defaultUEH;

    // handler listener
    private UncaughtExceptionHandler _unCaughtExceptionHandler =
        new UncaughtExceptionHandler() {
            
            public void uncaughtException(Thread thread, Throwable exception) {

            	/*StringWriter stackTrace = new StringWriter();
        		exception.printStackTrace(new PrintWriter(stackTrace));		
        		apperrorlogger objapperrorlogger=new apperrorlogger();        		
        		objapperrorlogger.writeToFile(stackTrace.toString());
        		//new sendservertask().execute();
                // here I do logging of exception to a db
          /*      PendingIntent myActivity = PendingIntent.getActivity(getAppContext(),551, new Intent(getAppContext(), Iptvhomescreen.class),
                    PendingIntent.FLAG_ONE_SHOT);

                AlarmManager alarmManager;
                alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,2000, myActivity );*/
            	Log.d("kaeman",exception.toString());

                StringWriter stackTrace = new StringWriter();
                exception.printStackTrace(new PrintWriter(stackTrace));
                objCls_logger.new SendPostRequest().execute(stackTrace.toString());
        		 
        		SystemClock.sleep(2000);
        		System.exit(0);

                // re-throw critical exception further to the os (important)
                //defaultUEH.uncaughtException(thread, exception);

            /*    Intent mStartActivity = new Intent(context, HomeActivity.class);
                int mPendingIntentId = 123456;
                PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager mgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                System.exit(0); */

            }
        };

    public MyApplication() {
        defaultUEH = Thread.getDefaultUncaughtExceptionHandler();

        // setup handler for uncaught exception 
        Thread.setDefaultUncaughtExceptionHandler(_unCaughtExceptionHandler);
    }

    public void onCreate(){
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
    
  
	
	private void sendToServer(){	   
	 /*   try
	    {	  	
	    	String STBIP=Utils.getIPAddress(true);
	    	String urlString = "http://192.168.2.28/projects/tv/cmsadmin/stbexception.php?stbip="+STBIP;
	  	   //String urlString ="http://10.10.28.2/iptvgettime/stbexception.php?stbip="+STBIP;
	        HttpClient client = new DefaultHttpClient();
	        HttpPost post = new HttpPost(urlString);
	        client.execute(post);	 
	        Log.d("kaeman", "application sendToServer ");  
	    }
	    catch (Exception e) {
            e.printStackTrace();
        }*/
	}
	private class sendservertask extends AsyncTask<Void, Integer, Void> 
	 {
			@Override
			protected Void doInBackground(Void... params) {
				
				sendToServer();
				
				return null;

			
			}

	 }

}

