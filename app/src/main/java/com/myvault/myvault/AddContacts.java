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



public class AddContacts extends LinearLayout {
	
	Context context;
	Communicator comm;
	ImageButton deleteButton;
	Button submitButton;
	EditText nameInput, numberInput, notesInput;
	
	String name, number, notes;
	
	boolean isEdit = false;
	int id;
	
	public AddContacts(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		
	 
	}

	public AddContacts(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

	}
	
	public AddContacts(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
		init();
	}
	
	
	public void init() {
		
		LayoutInflater inflater = (LayoutInflater) context
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.add_contacts, this, true);
		
		
		submitButton = (Button)findViewById(R.id.acon_submit_button);
		
		nameInput = (EditText)findViewById(R.id.acon_name_input);
		numberInput = (EditText)findViewById(R.id.acon_phone_input);
		notesInput = (EditText)findViewById(R.id.acon_notes_input);
		
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		
		nameInput.setTypeface(tf);
		numberInput.setTypeface(tf);
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
		
		name = nameInput.getText().toString();
		number = numberInput.getText().toString();
		notes = notesInput.getText().toString();
		
		if(!isEdit) {
			if(checkInputs()) {
				
				//send to db
				DBOpenHelper db = new DBOpenHelper(context);
				db.addContact(name, number, notes);
				comm.changeToDetails();
				comm.flipMenu();
			}
			
		
			
		}
		else {
			if(checkInputs()) {
				DBOpenHelper db = new DBOpenHelper(context);
				db.updateContact(id, name, number, notes);
				comm.changeToDetails();
				comm.flipMenu();
			}
		}
		
	}
	
	public boolean checkInputs() {
		
		if(name.length() < 1) {
			Toast.makeText(this.context, "Please enter name", Toast.LENGTH_LONG).show();
			return false;
		}
		if(number.length() < 1) {
			Toast.makeText(this.context, "Please enter phone number", Toast.LENGTH_LONG).show();
			return false;
		}
		
		
		return true;
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
