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
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class LockScreenActivity extends Activity {
	
	public LinearLayout titleLayout, topLayout, inputLayout, fullLayout;
	public TextView topText;
	public GridView buttons;
	public InputAdapter inputAdapter;
	
	
	int topPadding, gridHeight, screenHeight, dpi;
	
	int numTries;
	long lockoutTime;
	long timerLength = 5 * 60 * 1000;
	
	String entry;
	String pin;
	String inputString;
	
	UserData userData;
	TimerTask timerTask = null;
	
	Context context;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(LayoutParams.FLAG_SECURE,
                LayoutParams.FLAG_SECURE);

		setContentView(R.layout.lock_screen);
		
		context = this;
		
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		
		screenHeight = metrics.heightPixels;
		dpi = (int)metrics.density;
		
		userData = new UserData(this);
		
		
		titleLayout = (LinearLayout)findViewById(R.id.ls_top_layout);
		topLayout = (LinearLayout)findViewById(R.id.ls_spacer_layout);
		inputLayout = (LinearLayout)findViewById(R.id.ls_input_layout);
		fullLayout = (LinearLayout)findViewById(R.id.lockscreen_layout);
		
		
		
		topText = (TextView)findViewById(R.id.ls_top_textview);
		
		topText.setText("Enter Passcode");
		
		topPadding = 140 * dpi;
		gridHeight = screenHeight - topPadding;
		
		
		buttons = (GridView)findViewById(R.id.ls_gridview);
		inputAdapter = new InputAdapter(this);
		inputAdapter.setHeight(gridHeight);
		
		topLayout.removeAllViews();
		
		buttons.setAdapter(inputAdapter);
		
		Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		
		topText.setTypeface(tf);
		
		
		
		
		pin = userData.getPin();
		inputString = "";
		entry = "";
		
		
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
		
		if(!userData.isLocked()) {
			numTries = 0;
			
		}
		else {
			stillLocked();
		}
		
		
	}
	
	public void keyInput(int position) {
		if(position == 10) {
			position = -1;
		}
		if(entry.length() < 4) {
			entry += Integer.toString(position+1);
			makeInputString();
			if(entry.length() == 4) {
				checkPin();
			}
		}
		
	}
	
	public void clearInput() {
		entry = "";
		makeInputString();
	}
	
	public void deleteInput() {
		
		if(entry.length() > 0) {
			entry = entry.substring(0, entry.length()-1);
			makeInputString();
		}
		
	}
	
	public void checkPin() {
		
		if(entry.equals(pin)) {
			unlockVault();
		}
		else {
			if(numTries < 4) {
				numTries++;
			}
			else {
				lockOut();
			}
			Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
			 // Vibrate for 500 milliseconds
			 v.vibrate(500);
			Animation wobble = AnimationUtils.loadAnimation(this, R.anim.wobble);
			fullLayout.startAnimation(wobble);
			clearInput();
		}
		
			
		
		
	}

	public void makeInputString() {
		
		inputLayout.removeAllViews();
		
		for(int i=0; i<entry.length(); i++) {
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
		
	}
	
	public void unlockVault() {
		Intent i = new Intent(this, MainScreen.class);
		startActivity(i);
		finish();
	}
	
	public void stillLocked() {
		
		lockoutTime = userData.getLockoutStart();
		buttons.setEnabled(false);
		
		timerTask = new TimerTask();
		timerTask.execute("");
	}
	
	public void lockOut() {
		
		Calendar now = Calendar.getInstance();
		lockoutTime = now.getTimeInMillis();
		userData.setLockoutStart(lockoutTime);
		userData.setLocked(true);
		
		buttons.setEnabled(false);
		timerTask = new TimerTask();
		timerTask.execute("");
		
		
		
	}
	
	public boolean checkIfLockedOut() {
		lockoutTime = userData.getLockoutStart();
		Calendar now = Calendar.getInstance();
		
		long currentTime = now.getTimeInMillis();
		if(currentTime - lockoutTime < timerLength) {
			return false;
		}
		else {
			return true;
		}
		
		
	}
	
	public void unlockButtons() {
		timerTask.cancel(true);
		buttons.setEnabled(true);
		
	}
	
	@Override
	public void onBackPressed() {
		
		if(timerTask != null) {
			timerTask.cancel(true);
		}
		
		finish();
		
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
					numTries = 0;
					userData.setLocked(false);
					userData.setLockoutStart(0);
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
