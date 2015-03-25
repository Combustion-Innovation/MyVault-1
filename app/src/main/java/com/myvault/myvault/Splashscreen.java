package com.myvault.myvault;

import java.io.File;

import com.myvault.myvault.util.SystemUiHider;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager.LayoutParams;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class Splashscreen extends Activity {
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;
	
	
	
	private UserData userData;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(LayoutParams.FLAG_SECURE,
                LayoutParams.FLAG_SECURE);
		
		setContentView(R.layout.splashactivity);
		
		AlarmReceiver alarm = new AlarmReceiver();
		alarm.setAlarm(this);
		
		userData = new UserData(this);
		
		File securePath = new File(getFilesDir() + "/media/");
		File publicPath = new File(Environment.getExternalStorageDirectory() + "/MyVault/media/");
		
		if(!securePath.exists()) {
			securePath.mkdirs();
		}
		if(!publicPath.exists()) {
			publicPath.mkdirs(); 
		}
		
		Log.d("firsttime", Boolean.toString(userData.isNotFirstTime()));
		
		if(userData.isNotFirstTime()) {
			if(!userData.isLockSet()) {
        		Intent i = new Intent(Splashscreen.this, MainScreen.class);
        		startActivityForResult(i, 1);
    		}
    		else {
    			Intent i = new Intent(Splashscreen.this, LockScreenActivity.class);
    			startActivityForResult(i, 1);
    		}
		}
		else {
		

		
		  new Handler().postDelayed(new Runnable() {
			  
	            /*
	             * Showing splash screen with a timer. This will be useful when you
	             * want to show case your app logo / company
	             */
	 
	            @Override
	            public void run() {
	                // This method will be executed once the timer is over
	                // Start your app main activity
	            		userData.setNotFirstTime();
	            		if(!userData.isLockSet()) {
		            		Intent i = new Intent(Splashscreen.this, MainScreen.class);
		            		startActivityForResult(i, 1);
	            		}
	            		else {
	            			Intent i = new Intent(Splashscreen.this, LockScreenActivity.class);
	            			startActivityForResult(i, 1);
	            		}
	            		
	                finish();
	            }
	        }, 3000);
		
		
		};
		finish();
		// Set up the user interaction to manually show or hide the system UI.
	}

		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.


	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(requestCode == 1) {
			if(resultCode == RESULT_OK) {
				finish();
			}
		}
	}

}
