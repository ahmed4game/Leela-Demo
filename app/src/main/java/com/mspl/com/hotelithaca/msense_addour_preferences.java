package com.mspl.com.hotelithaca;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import msense.homepages.ExceptionHandler;
import msense.homepages.MyApplication;


public class msense_addour_preferences extends Activity {
    /** Called when the activity is first created. */
	
    protected SharedPreferences tv_test_preference;    
    private EditText var_edt_cmd_name,var_edt_cmd_value;

    //Oncreate method start    
   
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        
         requestWindowFeature(Window.FEATURE_NO_TITLE);
	     getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	     WindowManager.LayoutParams.FLAG_FULLSCREEN);
	     
	     setContentView(R.layout.add_our_preferences);
	     
	     StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	     StrictMode.setThreadPolicy(policy);
	    
        // Get the app's shared preferences  
        try {  
        	  tv_test_preference= PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
   	 
        } catch (NullPointerException npe) {  
          //write your code here  
       	// startActivity(new Intent(this, HomeActivity.class));
        }  
       
        Button btn0_view = (Button)findViewById(R.id.btn_view);
        btn0_view.setOnClickListener(mviewListener);
	    Button btn1_save = (Button)findViewById(R.id.btn_saveip);
	    btn1_save.setOnClickListener(msaveListener);
	    Button btn2_stn = (Button)findViewById(R.id.btn_stn);
	    btn2_stn.setOnClickListener(mstnListener);
	    Button btn3_bck = (Button)findViewById(R.id.btn_back);
	    btn3_bck.setOnClickListener(mbckListener);
	    Button btn4_savech = (Button)findViewById(R.id.btn_savech);
	    btn4_savech.setOnClickListener(msavechListener);
	    
	    var_edt_cmd_name   = (EditText)findViewById(R.id.edt_cmd_name);
	    var_edt_cmd_value   = (EditText)findViewById(R.id.edt_cmd_value);
    
        
    }//End Oncreate method   
    
    private OnClickListener mviewListener = new OnClickListener() {
        public void onClick(View v) {
        	action_view();
        }
    };
    private OnClickListener msaveListener = new OnClickListener() {
        public void onClick(View v) {        	
        	 action_save();
        }
    };
    private OnClickListener mstnListener = new OnClickListener() {
        public void onClick(View v) {
        	 startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
        }
    };
    private OnClickListener mbckListener = new OnClickListener() {
        public void onClick(View v) {
        	finish();
        }
    };
    private OnClickListener msavechListener = new OnClickListener() {
        public void onClick(View v) {         
       	  //new add_channel_db_task().execute();
        	//action_submit_savedata();
            lauchanotherapp("com.droidlogic.mboxlauncher","com.droidlogic.mboxlauncher.Launcher");
        }
    };
    
    public void action_view() 
	{
	 
	 	String str_tv_command_name=var_edt_cmd_name.getText().toString();
		
  		String str_show = tv_test_preference.getString(str_tv_command_name, null);
  		Toast.makeText(this, "data  "+str_show, Toast.LENGTH_SHORT).show();
    }
    
    private void action_save() 
	{
		SharedPreferences.Editor tv_command_editor_test = tv_test_preference.edit(); 
		
	 	String str_tv_command_name=var_edt_cmd_name.getText().toString();
	 	String form_value=var_edt_cmd_value.getText().toString();	     	  
   	
   		tv_command_editor_test.putString(str_tv_command_name, form_value);
   		tv_command_editor_test.commit(); 
    }
 /*   
    static {
        System.loadLibrary("hello-jni");
    }
  */
 public void lauchanotherapp(String pkgname,String argclasname) {
     // Toast.makeText(Iptvhomescreen.this, "pkg received:   " + pkgname, Toast.LENGTH_LONG).show();
     final Intent intent = new Intent();
     //ComponentName cName = new ComponentName("package_name","package_name.class_name");
     ComponentName cName = new ComponentName(pkgname,argclasname);

     intent.setComponent(cName);
     intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
     startActivity(intent);

 }
          
}//End Preferencesdemo class