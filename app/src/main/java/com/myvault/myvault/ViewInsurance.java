package com.myvault.myvault;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ViewInsurance extends LinearLayout {

	Context context;
	EditText providerInput, memberNameInput, memberIdInput, groupInput, typeInput, planInput, notesInput;
	
	public ViewInsurance(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		
	 
	}

	public ViewInsurance(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

	}
	
	public ViewInsurance(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
		init();
	}
	
	public void init() {
		
		LayoutInflater inflater = (LayoutInflater) context
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.view_insurance, this, true);
	    
	    providerInput = (EditText)findViewById(R.id.vi_provider_input);
		memberNameInput = (EditText)findViewById(R.id.vi_membername_input);
		memberIdInput = (EditText)findViewById(R.id.vi_memberid_input);
		groupInput = (EditText)findViewById(R.id.vi_group_input);
		typeInput = (EditText)findViewById(R.id.vi_type_input);
		planInput = (EditText)findViewById(R.id.vi_plancodes_input);
		notesInput = (EditText)findViewById(R.id.vi_notes_input);
		
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		providerInput.setTypeface(tf);
		memberNameInput.setTypeface(tf);
		memberIdInput.setTypeface(tf);
		groupInput.setTypeface(tf);
		typeInput.setTypeface(tf);
		planInput.setTypeface(tf);
		notesInput.setTypeface(tf);
	}
	
	public void setProvider(String string) {
		providerInput.setText(string);
	}
	
	public void setMemberName(String string) {
		memberNameInput.setText(string);
	}
	
	public void setMemberId(String string) {
		memberIdInput.setText(string);
	}
	
	public void setGroup(String string) {
		groupInput.setText(string);
	}
	
	public void setType(String string) {
		typeInput.setText(string);
	}
	
	public void setPlan(String string) {
		planInput.setText(string);
	}
	
	public void setNotes(String string) {
		notesInput.setText(string);
	}
}
