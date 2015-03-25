package com.myvault.myvault;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ViewPasswords extends LinearLayout {

	Context context;
	EditText websiteInput, usernameInput, passwordInput, notesInput;
	Button submitBtn;
	Communicator comm;
	
	public ViewPasswords(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		
	 
	}

	public ViewPasswords(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

	}
	
	public ViewPasswords(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
		init();
	}
	
	public void init() {
		
		LayoutInflater inflater = (LayoutInflater) context
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.view_passwords, this, true);
	    
	    websiteInput = (EditText)findViewById(R.id.vp_website_input);
		usernameInput = (EditText)findViewById(R.id.vp_username_input);
		passwordInput = (EditText)findViewById(R.id.vp_password_input);
		notesInput = (EditText)findViewById(R.id.vp_notes_input);
		submitBtn = (Button)findViewById(R.id.vp_submit_button);
		
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		
		websiteInput.setTypeface(tf);
		usernameInput.setTypeface(tf);
		passwordInput.setTypeface(tf);
		notesInput.setTypeface(tf);
		submitBtn.setTypeface(tf);
		
		submitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goToSite();
			}
			
		});
	}
	
	public void setWebsite(String string) {
		websiteInput.setText(string);
	}
	
	public void setUsername(String string) {
		usernameInput.setText(string);
	}
	
	public void setPassword(String string) {
		passwordInput.setText(string);
	}
	
	public void setNotes(String string) {
		notesInput.setText(string);
	}
	
	public void goToSite() {
		
		Uri uri = Uri.parse(websiteInput.getText().toString());
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		comm.setValidPause();
		context.startActivity(intent);
		
	}
	
	public void setCommunicator(Communicator comm) {
		this.comm = comm;
	}
	
	public interface Communicator {
		public void setValidPause();
		public void setInvalidPause();
	}
}
