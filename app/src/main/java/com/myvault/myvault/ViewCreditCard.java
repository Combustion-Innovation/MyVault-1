package com.myvault.myvault;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ViewCreditCard extends LinearLayout {

	Context context;
	EditText nicknameInput, nameInput, numberInput, expInput, typeInput, cvcInput;
	
	public ViewCreditCard(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init();
	 
	}

	public ViewCreditCard(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}
	
	public ViewCreditCard(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
		init();
	}
	
	public void init() {
		
		LayoutInflater inflater = (LayoutInflater) context
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.view_creditcard, this, true);
	    
	    nicknameInput = (EditText)findViewById(R.id.vcc_nickname_input);
		nameInput = (EditText)findViewById(R.id.vcc_carholder_input);
		numberInput = (EditText)findViewById(R.id.vcc_number_input);
		expInput = (EditText)findViewById(R.id.vcc_exp_input);
		typeInput = (EditText)findViewById(R.id.vcc_type_input);
		cvcInput = (EditText)findViewById(R.id.vcc_cvc_input);
		
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		
		nicknameInput.setTypeface(tf);
		nameInput.setTypeface(tf);
		numberInput.setTypeface(tf);
		expInput.setTypeface(tf);
		typeInput.setTypeface(tf);
		cvcInput.setTypeface(tf);
		
		fixWidths();
	}
	
	public void fixWidths() {
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		
		expInput.setWidth(width/3);
		typeInput.setWidth(width/3);
		cvcInput.setWidth(width/3);
	}
	
	public void setNickname(String string) {
		nicknameInput.setText(string);
	}
	
	public void setName(String string) {
		nameInput.setText(string);
	}
	
	public void setNumber(String string) {
		numberInput.setText(string);
	}
	
	public void setExp(String string) {
		expInput.setText(string);
	}
	
	public void setType(String string) {
		typeInput.setText(string);
	}
	
	public void setCVC(String string) {
		cvcInput.setText(string);
	}
}
