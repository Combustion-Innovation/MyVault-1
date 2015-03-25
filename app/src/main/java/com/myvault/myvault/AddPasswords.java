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



public class AddPasswords extends LinearLayout {
	
	Context context;
	Communicator comm;
	ImageButton deleteButton;
	Button submitButton;
	EditText websiteInput, usernameInput, passwordInput, notesInput;
	
	String website, username, password, notes;
	
	boolean isEdit = false;
	int id;
	
	public AddPasswords(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		
	 
	}

	public AddPasswords(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

	}
	
	public AddPasswords(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
		init();
	}
	
	
	public void init() {
		
		LayoutInflater inflater = (LayoutInflater) context
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.add_passwords, this, true);
		
		
		submitButton = (Button)findViewById(R.id.ap_submit_button);
		
		websiteInput = (EditText)findViewById(R.id.ap_website_input);
		usernameInput = (EditText)findViewById(R.id.ap_username_input);
		passwordInput = (EditText)findViewById(R.id.ap_password_input);
		notesInput = (EditText)findViewById(R.id.ap_notes_input);
		
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		
		websiteInput.setTypeface(tf);
		usernameInput.setTypeface(tf);
		passwordInput.setTypeface(tf);
		notesInput.setTypeface(tf);
		submitButton.setTypeface(tf);
		
		
		websiteInput.setText("http://");

		
		
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
		
		website = websiteInput.getText().toString();
		username = usernameInput.getText().toString();
		password = passwordInput.getText().toString();
		notes = notesInput.getText().toString();
		
		if(!isEdit) {
			if(checkInputs()) {
				
				//send to db
				DBOpenHelper db = new DBOpenHelper(context);
				db.addPassword(website, username, password, notes);
				comm.changeToDetails();
				comm.flipMenu();
			}
		}
		else {
			if(checkInputs()) {
				DBOpenHelper db = new DBOpenHelper(context);
				db.updatePassword(id, website, username, password, notes);
				comm.changeToDetails();
				comm.flipMenu();
			}
		}
	}
	
	public boolean checkInputs() {
		
		if(website.length() < 8) {
			Toast.makeText(this.context, "Please enter website", Toast.LENGTH_LONG).show();
			return false;
		}
		
		
		return true;
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
