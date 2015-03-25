package com.myvault.myvault;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ViewReminders extends LinearLayout {

	Context context;
	EditText titleInput, aliasInput, dateInput, timeInput, locationInput, notesInput;
	
	public ViewReminders(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		
	 
	}

	public ViewReminders(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

	}
	
	public ViewReminders(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
		init();
	}
	
	public void init() {
		
		LayoutInflater inflater = (LayoutInflater) context
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.view_reminders, this, true);
	    
	    titleInput = (EditText)findViewById(R.id.vr_title_input);
		aliasInput = (EditText)findViewById(R.id.vr_alias_input);
		dateInput = (EditText)findViewById(R.id.vr_date_input);
		timeInput = (EditText)findViewById(R.id.vr_time_input);
		locationInput = (EditText)findViewById(R.id.vr_location_input);
		notesInput = (EditText)findViewById(R.id.vr_notes_input);
		
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		
		titleInput.setTypeface(tf);
		aliasInput.setTypeface(tf);
		dateInput.setTypeface(tf);
		timeInput.setTypeface(tf);
		locationInput.setTypeface(tf);
		notesInput.setTypeface(tf);
	}
	
	public void setTitle(String string) {
		titleInput.setText(string);
	}
	
	public void setAlias(String string) {
		aliasInput.setText(string);
	}
	
	public void setDate(String string) {
		dateInput.setText(string);
	}
	
	public void setTime(String string) {
		timeInput.setText(string);
	}
	
	public void setLocation(String string) {
		locationInput.setText(string);
	}
	
	public void setNotes(String string) {
		notesInput.setText(string);
	}
}
