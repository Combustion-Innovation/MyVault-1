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



public class AddBookmarks extends LinearLayout {
	
	Context context;
	Communicator comm;
	ImageButton deleteButton;
	Button submitButton;
	EditText nicknameInput, urlInput, notesInput;
	
	String nickname, url, notes;
	
	boolean isEdit = false;
	int id;
	

	public AddBookmarks(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		
	 
	}

	public AddBookmarks(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

	}
	
	public AddBookmarks(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
		init();
	}
	
	
	public void init() {
		
		LayoutInflater inflater = (LayoutInflater) context
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.add_bookmarks, this, true);
		
		
		submitButton = (Button)findViewById(R.id.ab_submit_button);
		
		nicknameInput = (EditText)findViewById(R.id.ab_nickname_input);
		urlInput = (EditText)findViewById(R.id.ab_url_input);
		notesInput = (EditText)findViewById(R.id.ab_notes_input);
		
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		nicknameInput.setTypeface(tf);
		urlInput.setTypeface(tf);
		notesInput.setTypeface(tf);
		submitButton.setTypeface(tf);
		
		
		urlInput.setText("http://");
		
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
		
		nickname = nicknameInput.getText().toString();
		url = urlInput.getText().toString();
		notes = notesInput.getText().toString();
		
		if(!isEdit) {
			if(checkInputs()) {
				
				//send to db
				DBOpenHelper db = new DBOpenHelper(context);
				db.addBookmark(url, nickname, notes);
				comm.changeToDetails();
				comm.flipMenu();
			}
		}
		else {
			if(checkInputs()) {
				DBOpenHelper db = new DBOpenHelper(context);
				db.updateBookmark(id, url, nickname, notes);
			}
		}
		
	}
	
	public boolean checkInputs() {
		
		if(nickname.length() < 1) {
			Toast.makeText(this.context, "Please enter a nickname", Toast.LENGTH_LONG).show();
			return false;
		}
		if(url.length() < 8) {
			Toast.makeText(this.context, "Please enter URL", Toast.LENGTH_LONG).show();
			return false;
			
		}
		
		
		return true;
	}
	
	public void setNickname(String string) {
		nicknameInput.setText(string);
	}
	
	public void setURL(String string) {
		urlInput.setText(string);
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
