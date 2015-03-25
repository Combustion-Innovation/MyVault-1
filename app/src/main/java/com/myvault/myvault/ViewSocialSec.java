package com.myvault.myvault;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ViewSocialSec extends LinearLayout {

	Context context;
	EditText nameInput, numberInput, notesInput;
	
	
	public ViewSocialSec(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		
	 
	}

	public ViewSocialSec(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

	}
	
	public ViewSocialSec(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
		init();
	}
	
	public void init() {
		
		LayoutInflater inflater = (LayoutInflater) context
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.view_socialsec, this, true);
	    
	    nameInput = (EditText)findViewById(R.id.vss_name_input);
		numberInput = (EditText)findViewById(R.id.vss_number_input);
		notesInput = (EditText)findViewById(R.id.vss_notes_input);
		
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		
		nameInput.setTypeface(tf);
		numberInput.setTypeface(tf);
		notesInput.setTypeface(tf);
	}
	
	public void setName(String string) {
		nameInput.setText(string);
	}
	
	public void setNumber(String string) {
		numberInput.setText(string);
	}
	
	public void setNotes(String string) {
		notesInput.setText(string);
	}
}
