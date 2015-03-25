package com.myvault.myvault;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ViewAccounts extends LinearLayout {

	Context context;
	EditText businessText, accountText, phoneText, notesText;
	
	
	public ViewAccounts(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		
	 
	}

	public ViewAccounts(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

	}
	
	public ViewAccounts(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
		init();
	}
	
	public void init() {
		
		LayoutInflater inflater = (LayoutInflater) context
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.view_accounts, this, true);
	    
	    businessText = (EditText)findViewById(R.id.va_business_input);
	    accountText = (EditText)findViewById(R.id.va_account_input);
	    phoneText = (EditText)findViewById(R.id.va_phone_input);
	    notesText = (EditText)findViewById(R.id.va_notes_input);
	    
	    Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
	    businessText.setTypeface(tf);
	    accountText.setTypeface(tf);
	    phoneText.setTypeface(tf);
	    notesText.setTypeface(tf);
	    
	}
	
	public void setBusiness(String string) {
		businessText.setText(string);
	}
	
	public void setAccount(String string) {
		accountText.setText(string);
	}
	
	public void setPhone(String string) {
		phoneText.setText(string);
	}
	
	public void setNotes(String string) {
		notesText.setText(string);
	}
}
