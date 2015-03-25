package com.myvault.myvault;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ViewNotes extends LinearLayout {

	Context context;
	EditText titleInput, notesInput;
	
	public ViewNotes(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		
	 
	}

	public ViewNotes(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

	}
	
	public ViewNotes(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
		init();
	}
	
	public void init() {
		
		LayoutInflater inflater = (LayoutInflater) context
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.view_notes, this, true);
	    
	    titleInput = (EditText)findViewById(R.id.vn_title_input);
		notesInput = (EditText)findViewById(R.id.vn_notes_input);
		
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		titleInput.setTypeface(tf);
		notesInput.setTypeface(tf);
	}
	
	public void setTitle(String string) {
		titleInput.setText(string);
	}
	
	public void setNotes(String string) {
		notesInput.setText(string);
	}
}
