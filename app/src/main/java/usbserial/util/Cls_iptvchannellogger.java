package usbserial.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Cls_iptvchannellogger {
	
	private static final String TAG = "IPTVchannellogger";
	
	/** Method to write ascii text characters to file on SD card. Note that you must add a 
	   WRITE_EXTERNAL_STORAGE permission to the manifest file or this method will throw
	   a FileNotFound Exception because you won't have write permission. */

	public void writeToSDFile(String str_channelname){

	    // Find the root of the external storage.
	    // See http://developer.android.com/guide/topics/data/data-  storage.html#filesExternal

/*	    File root = android.os.Environment.getExternalStorageDirectory(); 
	    Log.i(TAG,"\nExternal file system root: "+root);  */
	    
	    String extStore = System.getenv("EXTERNAL_STORAGE");
	    File root = new File(extStore);
	/*    Log.i(TAG,"\nExternal file : "+f_exts);
	    
	    String secStore = System.getenv("SECONDARY_STORAGE");
	    File f_secs = new File(secStore);
	    
	    Log.i(TAG,"\nSecondary root  : "+f_secs);   */

	    // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder
	    String str_today_date = new SimpleDateFormat("ddMMyyyy").format(new Date());
	    
	    DateFormat var1_dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    Date var1_date = new Date(); 
	    //Date var1_date = myfn_getTime(); 
	    String str_today_currenttime= var1_dateFormat.format(var1_date);
	    
	    File dir = new File (root.getAbsolutePath() + "/IPTVchannellogger");
	    dir.mkdirs();
	    File file = new File(dir, str_today_date + ".txt");

	    try {
	        FileOutputStream f = new FileOutputStream(file,true);
	        PrintWriter pw = new PrintWriter(f);
	        pw.println(str_channelname + "  " + str_today_currenttime);	       
	        pw.flush();
	        pw.close();
	        f.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	        Log.i(TAG, "******* File not found. Did you" +" add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");

	    } catch (IOException e) {
	        e.printStackTrace();
	    }   
	    Log.i(TAG,"\n\nFile written to "+file);
	}
	
	public void write_channel_notavl(Context cntx, String str_ch_msg){

	    // Find the root of the external storage.
	    // See http://developer.android.com/guide/topics/data/data-  storage.html#filesExternal

	    File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);//Environment.getExternalStorageDirectory();
	/*	File[] fs = cntx.getExternalFilesDirs(null);
		String extPath = "";
		// at index 0 you have the internal storage and at index 1 the real external...
		if (fs != null && fs.length >= 2)
		{
			extPath = fs[1].getAbsolutePath();
			Log.e("SD Path",fs[1].getAbsolutePath());
		} */
	     Log.i(TAG,"\nExternal file system root: "+root);

	    // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder	 
	    String str_today_date = new SimpleDateFormat("ddMMyyyy").format(new Date());
	    
	    DateFormat var_dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    Date var_date = new Date(); 
	    String str_today_currenttime= var_dateFormat.format(var_date);
	    
	    File dir = new File (root.getAbsolutePath() + "/IPTVchannellogger");
	    dir.mkdirs();
	    File file = new File(dir, str_today_date + "nvl.txt");

	    try {
	        FileOutputStream f = new FileOutputStream(file,true);
	        PrintWriter pw = new PrintWriter(f);
	        pw.println(str_ch_msg + "  " + str_today_currenttime);	       
	        pw.flush();
	        pw.close();
	        f.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	        Log.i(TAG, "******* File not found. Did you" +
	                " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }   
	    Log.i(TAG,"\n\nFile written to "+file);
	}
	
	/** Method to check whether external media available and writable. This is adapted from
	   http://developer.android.com/guide/topics/data/data-storage.html#filesExternal */

	 private void checkExternalMedia(){
	      boolean mExternalStorageAvailable = false;
	    boolean mExternalStorageWriteable = false;
	    String state = Environment.getExternalStorageState();

	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        // Can read and write the media
	        mExternalStorageAvailable = mExternalStorageWriteable = true;
	    } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        // Can only read the media
	        mExternalStorageAvailable = true;
	        mExternalStorageWriteable = false;
	    } else {
	        // Can't read or write
	        mExternalStorageAvailable = mExternalStorageWriteable = false;
	    }   
	    Log.i(TAG,"\n\nExternal Media: readable="
	            +mExternalStorageAvailable+" writable="+mExternalStorageWriteable);
	}

	// public String myfn_getTime() {
	/* public Date myfn_getTime() {
			try{
			    //Make the Http connection so we can retrieve the time
			    HttpClient httpclient = new DefaultHttpClient();
			    // I am using yahoos api to get the time
			    HttpResponse response = httpclient.execute(new
			    HttpGet("http://developer.yahooapis.com/TimeService/V1/getTime?appid=YahooDemo"));
			    StatusLine statusLine = response.getStatusLine();
			    if(statusLine.getStatusCode() == HttpStatus.SC_OK){
			        ByteArrayOutputStream out = new ByteArrayOutputStream();
			        response.getEntity().writeTo(out);
			        out.close();
			        // The response is an xml file and i have stored it in a string
			        String responseString = out.toString();
			        Log.d("Response", responseString);
			        //We have to parse the xml file using any parser, but since i have to 
			        //take just one value i have deviced a shortcut to retrieve it
			        int x = responseString.indexOf("<Timestamp>");
			        int y = responseString.indexOf("</Timestamp>");
			        //I am using the x + "<Timestamp>" because x alone gives only the start value
			        Log.d("Response", responseString.substring(x + "<Timestamp>".length(),y) );
			        String timestamp =  responseString.substring(x + "<Timestamp>".length(),y);
			        // The time returned is in UNIX format so i need to multiply it by 1000 to use it
			        Date d = new Date(Long.parseLong(timestamp) * 1000);
			        Log.d("Response", d.toString() );
			        return d ;
			      //  return d.toString() ;
			    } else{
			        //Closes the connection.
			        response.getEntity().getContent().close();
			        throw new IOException(statusLine.getReasonPhrase());
			    }
			}catch (ClientProtocolException e) {
			Log.d("Response", e.getMessage());
			}catch (IOException e) {
			Log.d("Response", e.getMessage());
			}
			return null;
			}*/
	 
	
	 

}
