package com.myvault.myvault;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SetPinActivity extends Activity {
	
	InputAdapter adapter;
	GridView gridView;
	TextView topText;
	LinearLayout inputLayout;
	Button cancelBtn;
	
	String firstEntry;
	String secondEntry;
	
	boolean firstDone = false;
	boolean secondDone = false;
	
	int screenHeight;
	int dpi;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setFlags(LayoutParams.FLAG_SECURE,
                LayoutParams.FLAG_SECURE);
		
		setContentView(R.layout.lock_screen);
		
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		
		screenHeight = metrics.heightPixels;
		dpi = (int)metrics.density;
		
		int topPadding = 140 * dpi;//titleLayout.getHeight() + topLayout.getHeight() + inputLayout.getHeight();
		int gridHeight = screenHeight - topPadding;
		
		firstEntry = "";
		secondEntry = "";
		
		gridView = (GridView)findViewById(R.id.ls_gridview);
		inputLayout = (LinearLayout)findViewById(R.id.ls_input_layout);
		topText = (TextView)findViewById(R.id.ls_top_textview);
		cancelBtn = (Button)findViewById(R.id.ls_cancel_button);
		
		adapter = new InputAdapter(this);
		adapter.setHeight(gridHeight);
		gridView.setAdapter(adapter);
		
		Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		topText.setTypeface(tf);
		
		topText.setText("Enter Passcode");
		
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

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
		
		
		
		
		
		
	}
	
	public void clearInput() {
		if(secondEntry.length() > 0) {
			secondEntry = "";
			makeInputString(secondEntry);
		}
		else if(firstEntry.length() > 0) {
			firstEntry = "";
			makeInputString(firstEntry);
			
		}
		
	}
	
	public void deleteInput() {
		if(secondEntry.length() > 0) {
			secondEntry = secondEntry.substring(0, secondEntry.length() - 1);
			makeInputString(secondEntry);
		}
		else if(firstEntry.length() > 0) {
			firstEntry = firstEntry.substring(0, firstEntry.length() - 1);
			makeInputString(firstEntry);
		}
		
	}
	
	public void keyInput(int position) {
		
		if(position == 10) {
			position = -1;
		}
		if(!secondDone && firstDone) {
			if(secondEntry.length() < 4) {
				secondEntry += Integer.toString(position + 1);
				makeInputString(secondEntry);
				if(secondEntry.length() == 4) {
					secondDone = true;
					checkEntries();
				}
			}
		}
		else if(!firstDone) {
			if(firstEntry.length() < 4) {
				firstEntry += Integer.toString(position+1);
				makeInputString(firstEntry);
				if(firstEntry.length() == 4) {
					firstDone = true;
					Toast.makeText(this, "Re-enter Passcode", Toast.LENGTH_LONG).show();
					topText.setText("Re-Enter Passcode");
				}
			}
		}
		
	}
	
	public void checkEntries() {
		if(firstEntry.equals(secondEntry)) {
			Toast.makeText(this, "Lock Set", Toast.LENGTH_LONG).show();
			setLock();
		}
		else {
			Toast.makeText(this, "Passcode mismatch, try again", Toast.LENGTH_LONG).show();
			resetPage();
		}
	}
	
	public void setLock() {
		
		UserData userData = new UserData(this);
		userData.setLockSet(true);
		userData.setPin(firstEntry);
		
		Intent i = new Intent();
		setResult(RESULT_OK, i);
		finish();
		
	}
	
	public void resetPage() {
		firstEntry = "";
		secondEntry = "";
		
		topText.setText("Enter Passcode");
		firstDone = false;
		secondDone = false;
		makeInputString(firstEntry);
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
		Intent i = new Intent();
		setResult(RESULT_CANCELED, i);
		finish();
	}

}
