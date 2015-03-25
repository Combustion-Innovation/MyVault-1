package com.myvault.myvault;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;



public class AddAccounts extends LinearLayout {
	
	Context context;
	Communicator comm;
	ImageButton deleteButton;
	Button submitButton;
	EditText businessInput, acctNumInput, phoneNumInput, notesInput;
	
	String business, acctNum, phoneNum, notes;
	
	boolean isEdit = false;
	int id;
	
	public AddAccounts(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		
	 
	}

	public AddAccounts(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

	}
	
	public AddAccounts(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
		init();
	}

	
	public void init() {
		
		LayoutInflater inflater = (LayoutInflater) context
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.add_accounts, this, true);
		
		
		submitButton = (Button)findViewById(R.id.aa_submit_button);
		
		businessInput = (EditText)findViewById(R.id.aa_business_input);
		acctNumInput = (EditText)findViewById(R.id.aa_account_input);
		phoneNumInput = (EditText)findViewById(R.id.aa_phone_input);
		notesInput = (EditText)findViewById(R.id.aa_notes_input);
		
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		businessInput.setTypeface(tf);
		acctNumInput.setTypeface(tf);
		phoneNumInput.setTypeface(tf);
		notesInput.setTypeface(tf);
		submitButton.setTypeface(tf);
		
		submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				submitBtnClick();
			}
		});
		
		
	}
	
	public void deleteBtnClick() {
		
	}
	
	public void submitBtnClick() {
		
		business = businessInput.getText().toString();
		acctNum = acctNumInput.getText().toString();
		phoneNum = phoneNumInput.getText().toString();
		notes = notesInput.getText().toString();
		
		if(!isEdit) {
			if(checkInputs()) {
				
				//submit to db
				DBOpenHelper db = new DBOpenHelper(context);
				db.addAccount(business, acctNum, phoneNum, notes);
				comm.changeToDetails();
				comm.flipMenu();
			}
		}
		else {
			DBOpenHelper db = new DBOpenHelper(context);
			db.updateAccount(this.id, business, acctNum, phoneNum, notes);
			comm.changeToDetails();
			comm.flipMenu();
		}
		
	}
	
	public boolean checkInputs() {
		
		if(business.length() < 1) {
			Toast.makeText(context, "Please enter business name", Toast.LENGTH_LONG).show();
			return false;
		}
		
		
		
		return true;
		
	}
	
	public void setBusiness(String string) {
		businessInput.setText(string);
	}
	
	public void setAcctNum(String string) {
		acctNumInput.setText(string);
	}
	
	public void setPhoneNum(String string) {
		phoneNumInput.setText(string);
	}
	
	public void setNotes(String string) {
		notesInput.setText(string);
	}
	
	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCommunicator(Communicator comm) {
		this.comm = comm;
	}
		
	
	public interface Communicator {
		
		public void onResult(ObjectHolder holder);
		public void changeToDetails();
		public void flipMenu();
		
	}

}
