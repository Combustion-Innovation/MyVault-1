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

public class ViewContacts extends LinearLayout {

	Context context;
	EditText nameInput, numberInput, notesInput;
	Button submitBtn;
	Communicator comm;
	
	public ViewContacts(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		
	 
	}

	public ViewContacts(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

	}
	
	public ViewContacts(Context context) {
		super(context);
		
		this.context = context;
		// TODO Auto-generated constructor stub
		init();
	}
	
	public void init() {
		
		LayoutInflater inflater = (LayoutInflater) context
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.view_contacts, this, true);
	    
	    nameInput = (EditText)findViewById(R.id.vcon_name_input);
		numberInput = (EditText)findViewById(R.id.vcon_phone_input);
		notesInput = (EditText)findViewById(R.id.vcon_notes_input);
		submitBtn = (Button)findViewById(R.id.vcon_submit_button);
		
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		
		nameInput.setTypeface(tf);
		numberInput.setTypeface(tf);
		notesInput.setTypeface(tf);
		submitBtn.setTypeface(tf);
		
		submitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callContact();
			}
			
		});
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
	
	public void callContact() {
		
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numberInput.getText().toString()));
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
