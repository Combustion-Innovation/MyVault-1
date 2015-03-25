package com.myvault.myvault;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UnlockPinActivity extends Activity {
	
	InputAdapter adapter;
	GridView buttons;
	TextView topText;
	LinearLayout inputLayout;
	Button cancelBtn;
	
	String entry;
	UserData userData;
	TimerTask timerTask;
	
	Context context;
	
	
	long timerLength = 5 * 60 * 1000;
	long lockoutTime;
	
	int screenHeight;
	int dpi;
	int numTries = 0;
	
	boolean lockedOut = false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(LayoutParams.FLAG_SECURE,
                LayoutParams.FLAG_SECURE);

		setContentView(R.layout.lock_screen);
		
		context = this;
		
		userData = new UserData(this);
		
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		
		screenHeight = metrics.heightPixels;
		dpi = (int)metrics.density;
		
		int topPadding = 140 * dpi;
		int gridHeight = screenHeight - topPadding;
		
		entry = "";
		
		
		buttons = (GridView)findViewById(R.id.ls_gridview);
		inputLayout = (LinearLayout)findViewById(R.id.ls_input_layout);
		topText = (TextView)findViewById(R.id.ls_top_textview);
		cancelBtn = (Button)findViewById(R.id.ls_cancel_button);
		
		
		
		adapter = new InputAdapter(this);
		adapter.setHeight(gridHeight);
		buttons.setAdapter(adapter);
		
		Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		topText.setTypeface(tf);
		
		
		topText.setText("Enter Passcode");
		
		buttons.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(position == 9) {
					clearInput();
				}
				else if(position == 11) {
					deleteInput();
				}
				else {
					
					keyInput(position);
				}
				
			}
			
		});
		
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				setResult(RESULT_CANCELED, i);
				finish();
			}
			
		});
		
		
		if(!userData.isLocked()) {
			numTries = 0;
			
		}
		else {
			lockedOut = true;
			stillLocked();
		}
		
		
		
	}
	
	public void clearInput() {
		entry = "";
		makeInputString(entry);
		
	}
	
	public void deleteInput() {
		if(entry.length() > 0) {
			entry = entry.substring(0, entry.length()-1);
			makeInputString(entry);
		}
		
	}
	
	public void keyInput(int position) {
		
		if(position == 10) {
			position = -1;
		}
		
		
		if(entry.length() < 4) {
			entry += Integer.toString(position+1);
			makeInputString(entry);
			if(entry.length() == 4) {
				checkPin();
			}
		}
		
		
	}
	
	public void checkPin() {
		UserData userData = new UserData(this);
		String pin = userData.getPin();
		if(entry.equals(pin)) {
			userData.setLockSet(false);
			Intent i = new Intent();
			setResult(RESULT_OK, i);
			
			finish();
		}
		else {
			if(numTries < 4) {
				numTries++;
			}
			else {
				lockOut();
			}
			Toast.makeText(this, "Invalid Pin", Toast.LENGTH_LONG).show();
			Vibrator v = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);
			 // Vibrate for 500 milliseconds
			 v.vibrate(500);
			Animation wobble = AnimationUtils.loadAnimation(this, R.anim.wobble);
			buttons.startAnimation(wobble);
			clearInput();
			resetPage();
		}
	}
	
	
	
	public void lockOut() {
		lockedOut = true;
		Calendar now = Calendar.getInstance();
		lockoutTime = now.getTimeInMillis();
		userData.setLockoutStart(lockoutTime);
		userData.setLocked(true);
		
		buttons.setEnabled(false);
		cancelBtn.setEnabled(false);
		timerTask = new TimerTask();
		timerTask.execute("");
		
		
		
	}
	
	public void stillLocked() {
		Log.d("still", "locked");
		lockedOut = true;
		lockoutTime = userData.getLockoutStart();
		cancelBtn.setEnabled(false);
		buttons.setEnabled(false);
		timerTask = new TimerTask();
		timerTask.execute("");
	}
	
	public void resetPage() {
		entry = "";
		
		inputLayout.removeAllViews();
		
	}
	
	public void makeInputString(String string) {
		
		inputLayout.removeAllViews();
		
		for(int i=0; i<string.length(); i++) {
			ImageView dot = new ImageView(this);
			dot.setImageResource(R.drawable.passworddot);
			inputLayout.addView(dot);
				if(i > 0) {
				
					DisplayMetrics metrics = getResources().getDisplayMetrics();
					int dpi = (int)metrics.density;
					MarginLayoutParams params = (MarginLayoutParams)dot.getLayoutParams();
					params.leftMargin = 40 * dpi;
					dot.setLayoutParams(params);
					
					
					
				}
			
		}
		/*
		String entry = string;
		String inputString = "";
	
		switch(entry.length()) {
		case 0:
			inputString = "";
			break;
		case 1:
			inputString = "*";
			break;
		case 2:
			inputString = "*  *";
			break;
		case 3:
			inputString = "*  *  *";
			break;
		case 4:
			inputString = "*  *  *  *";
			break;
		}
		inputText.setText(inputString);
		*/
	}
	
	@Override
	public void onBackPressed() {
		if(!lockedOut) {
			Intent i = new Intent();
			setResult(RESULT_CANCELED, i);
			finish();
		}
		else {
			timerTask.cancel(true);
			//android.os.Process.killProcess(android.os.Process.myPid());
			Intent i = new Intent();
			i.putExtra("fullExit", true);
			setResult(RESULT_CANCELED, i);
			finish();
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		/*
		UserData userdata = new UserData(this);
		if(userdata.isLockSet()) {
	    	Intent i = new Intent(this, SleepLockActivity.class);
	    	startActivity(i);
		}
		*/
	}
	
	private class TimerTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Calendar time = Calendar.getInstance();
			long currentTime = time.getTimeInMillis();
			long currentTimer = 0;
			final TextView timerText = new TextView(context);
			Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
			timerText.setTypeface(tf);
			timerText.setTextSize(36f);
			
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					inputLayout.removeAllViews();
					
					inputLayout.addView(timerText);
					
				}
				
			});
			
			while(currentTime - lockoutTime < timerLength) {
				
				if(isCancelled()) {
					return null;
				}
				currentTimer = currentTime - lockoutTime;
				final String timerString = milliSecondsToTimer(timerLength - currentTimer);
				//input.setText(timerString);
				time = Calendar.getInstance();
				currentTime = time.getTimeInMillis();
			
				
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						timerText.setText(timerString);
					}
					
				});
				
				
				
				
				
			}
			
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					inputLayout.removeAllViews();
					buttons.setEnabled(true);
					cancelBtn.setEnabled(true);
					numTries = 0;
					userData.setLocked(false);
					userData.setLockoutStart(0);
					lockedOut = false;
				}
				
			});
			
			
			return null;
		}
	
	}
	
	public String milliSecondsToTimer(long milliseconds){
        String finalTimerString = "";
        String secondsString = "";
        String minutesString = "";
 
        // Convert total duration into time
           int hours = (int)( milliseconds / (1000*60*60));
           int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
           int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);
           // Add hours if there
          
               finalTimerString = "0" + hours + ":";
           
 
           // Prepending 0 to seconds if it is one digit
           if(seconds < 10){
               secondsString = "0" + seconds;
           }else{
               secondsString = "" + seconds;}
           minutesString = Integer.toString(minutes);
 
           finalTimerString = minutesString + ":" + secondsString;
 
        // return timer string
        return finalTimerString;
    }

}
