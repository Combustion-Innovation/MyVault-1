package com.myvault.myvault;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ViewCars extends LinearLayout {

	Context context;
	EditText makeInput, modelInput, yearInput, plateInput, companyInput, policyInput, agentNameInput, agentNumInput, notesInput;
	
	public ViewCars(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		
	 
	}

	public ViewCars(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

	}
	
	public ViewCars(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
		init();
	}
	
	public void init() {
		
		LayoutInflater inflater = (LayoutInflater) context
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.view_cars, this, true);
	    
	    makeInput = (EditText)findViewById(R.id.vc_make_input);
	    modelInput = (EditText)findViewById(R.id.vc_model_input);
	    yearInput = (EditText)findViewById(R.id.vc_year_input);
	    plateInput = (EditText)findViewById(R.id.vc_plate_input);
	    companyInput = (EditText)findViewById(R.id.vc_company_input);
	    policyInput = (EditText)findViewById(R.id.vc_policy_input);
	    agentNameInput = (EditText)findViewById(R.id.vc_agentname_input);
	    agentNumInput = (EditText)findViewById(R.id.vc_agentnum_input);
	    notesInput = (EditText)findViewById(R.id.vc_notes_input);
	    
	    Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		makeInput.setTypeface(tf);
		modelInput.setTypeface(tf);
		yearInput.setTypeface(tf);
		plateInput.setTypeface(tf);
		companyInput.setTypeface(tf);
		policyInput.setTypeface(tf);
		agentNameInput.setTypeface(tf);
		agentNumInput.setTypeface(tf);
		notesInput.setTypeface(tf);
		
	}
	
	public void setMake(String string) {
		makeInput.setText(string);
	}
	
	public void setModel(String string) {
		modelInput.setText(string);
	}
	
	public void setYear(String string) {
		yearInput.setText(string);
	}
	
	public void setPlate(String string) {
		plateInput.setText(string);
	}
	
	public void setCompany(String string) {
		companyInput.setText(string);
	}
	
	public void setPolicy(String string) {
		policyInput.setText(string);
	}
	
	public void setAgentName(String string) {
		agentNameInput.setText(string);
	}
	
	public void setAgentNum(String string) {
		agentNumInput.setText(string);
	}
	
	public void setNotes(String string) {
		notesInput.setText(string);
	}
}
