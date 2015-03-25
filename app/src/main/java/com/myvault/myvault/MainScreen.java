package com.myvault.myvault;


import com.myvault.myvault.util.SystemUiHider;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class MainScreen extends Activity implements MainScreenAdapter.Communicator {
	GridView gridView;
	ImageView logo;
	MainScreenAdapter adapter;
	Context context;
	ProgressDialog pd;
	
	
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
	
	private SQLiteDatabase db;
	private Handler handler;
	
	boolean isValidPause = true;
	boolean isSettingsPressed = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(LayoutParams.FLAG_SECURE,
                LayoutParams.FLAG_SECURE);

		setContentView(R.layout.main_screen);
		
		context = this;
		

		DBOpenHelper dbopen = new DBOpenHelper(this);
		db = dbopen.getWritableDatabase();
		
	 

		createGridView();
		
		UserData userData = new UserData(this);
		if(!userData.pushAdShown()) {
			Intent i = new Intent(this, PushAd.class);
			startActivityForResult(i, 5);
		}
		
		
		isValidPause = false;
		
	};
		
	
		
	public void createGridView(){
	     adapter  = new MainScreenAdapter(this);
	     adapter.setCommunicator(this);
	     
		 gridView= (GridView)findViewById(R.id.main_grid);
		 gridView.setAdapter(adapter);

		 gridView.setOnItemClickListener(new OnItemClickListener() {
			 public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				 Log.d("id", Long.toString(id));
	        	//parent.getChildAt(position).setAlpha(0.8f);
	        	if(position == 11)
	        	{
	        		//this is the settings view so we do somehting different
	        		if(gridView.isEnabled() == true) {
	        		gridView.setEnabled(false);
	        		clickLock();
	        		showSettings();
	        		}
	        		
	        	}
	        	else if(position == 8) {
	        		        		
	        		goToMedia();
	        		        			
	        	}
	        	else
	        	{
	        		//get the position and send it to the list view activitiy
	        		goToListView(position);
	        	}
	        	
	        	
	        }
		 });
		 gridView.setOnItemLongClickListener(null);
		 gridView.setOnItemSelectedListener(null);
		 
	}

		
	public void clickLock() {
		
		handler = new Handler();
		gridView.setEnabled(false);
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.d("unlocked", "true");
				gridView.setEnabled(true);
				
			}
			
		}, 1000);
		
		
	}

	
	
	
	
	//goes to the listview and will pass the position pressed :IE position for accounts would be 0
	public void goToListView(int position)
	{
		
		Intent i = new Intent(this,ListviewScreen.class);
		isValidPause = true;
		i.putExtra("type", position);
			this.startActivityForResult(i, 1);	
		    this.overridePendingTransition(R.anim.slide_right_in,
	               R.anim.slide_left_out);
		
	}
	
	
	//shows settings Activity
	public void showSettings()
	{
		Intent i = new Intent(this,SettingsScreen.class);
		isValidPause = true;
		this.startActivityForResult(i, 3);
		this.overridePendingTransition(0, 0);
	}
	
	public void goToMedia() {
		isValidPause = true;
		
		
		Intent i = new Intent(this, ViewMedia.class);
		
		this.startActivityForResult(i, 2);
		this.overridePendingTransition(R.anim.slide_right_in,
	               R.anim.slide_left_out);
		
	}
	
	
	
	@Override
    public void onPause() {
		super.onPause();
		if(!isValidPause) {
			UserData userdata = new UserData(this);
			
			if(userdata.isLockSet()) {
		    	Intent i = new Intent(this, SleepLockActivity.class);
		    	startActivityForResult(i, 4);
			}
		}
    }
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(requestCode == 1) {
			
			isValidPause = false;
			gridView.clearChoices();
			gridView.clearFocus();
			
		}
		if(requestCode == 3) {
			isValidPause = false;
			gridView.clearChoices();
			gridView.clearFocus();
			if(resultCode == RESULT_CANCELED) {
				boolean fullExit = data.getBooleanExtra("fullExit", false);
				if(fullExit) {
					isValidPause = true;
					finish();
				}
			}
		}
		if(requestCode == 4) {
			isValidPause = false;
			if(resultCode == RESULT_CANCELED) {
				boolean fullExit = false;
				if(data.getBooleanExtra("fullExit", false)) {
					fullExit = data.getBooleanExtra("fullExit", false);
				}
				
				if(fullExit) {
					isValidPause = true;
					finish();
				}
			}
		}
		if(requestCode == 5) {
			isValidPause = false;
			UserData userData = new UserData(this);
			userData.setPushAdShown();
		}
	}
	
	@Override
	public void onBackPressed() {
		isValidPause = true;
		Intent i = new Intent();
		setResult(RESULT_OK, i);
		finish();
	}


	@Override
	public void tileClicked(View view, int position) {
		// TODO Auto-generated method stub
		long id = gridView.getItemIdAtPosition(position);
		gridView.performItemClick(view, position, id);
		
	}
	

}
