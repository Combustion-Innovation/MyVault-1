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



public class AddNotes extends LinearLayout {
	
	Context context;
	Communicator comm;
	ImageButton deleteButton;
	Button submitButton;
	EditText titleInput, notesInput;
	
	String title, notes;
	
	boolean isEdit = false;
	int id;
	
	public AddNotes(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		
	 
	}

	public AddNotes(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

	}
	
	public AddNotes(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
		init();
	}
	
	
	public void init() {
		
		LayoutInflater inflater = (LayoutInflater) context
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.add_notes, this, true);
		
		
		submitButton = (Button)findViewById(R.id.an_submit_button);
		
		titleInput = (EditText)findViewById(R.id.an_title_input);
		notesInput = (EditText)findViewById(R.id.an_notes_input);
		
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		titleInput.setTypeface(tf);
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
		
		title = titleInput.getText().toString();
		notes = notesInput.getText().toString();
		
		if(!isEdit) {
		
			
			//send to db
			if(checkInputs()) {
			DBOpenHelper db = new DBOpenHelper(context);
			db.addNote(title, notes);
			comm.changeToDetails();
			comm.flipMenu();
			}
			
		}
		else {
			DBOpenHelper db = new DBOpenHelper(context);
			db.updateNote(id, title, notes);
			comm.changeToDetails();
			comm.flipMenu();
		}
		
		
	}
	
	public boolean checkInputs() {
		
		if(title.length() < 1) {
			Toast.makeText(this.context, "Please enter title", Toast.LENGTH_LONG).show();
			return false;
		}
		
		return true;
	}
	
	public void setTitle(String string) {
		titleInput.setText(string);
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
