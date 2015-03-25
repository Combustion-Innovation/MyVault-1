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

public class ViewBookmarks extends LinearLayout {

	Context context;
	EditText nicknameText, urlText, notesText;
	Button submitBtn;
	Communicator comm;
	
	public ViewBookmarks(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		
	 
	}

	public ViewBookmarks(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

	}
	
	public ViewBookmarks(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
		init();
	}
	
	public void init() {
		
		LayoutInflater inflater = (LayoutInflater) context
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.view_bookmarks, this, true);
	    
	    nicknameText = (EditText)findViewById(R.id.vb_nickname_input);
	    urlText = (EditText)findViewById(R.id.vb_url_input);
	    notesText = (EditText)findViewById(R.id.vb_notes_input);
	    submitBtn = (Button)findViewById(R.id.vb_submit_button);
	    
	    Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
	    nicknameText.setTypeface(tf);
	    urlText.setTypeface(tf);
	    notesText.setTypeface(tf);
	    submitBtn.setTypeface(tf);
	    
	    submitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				visitSite();
			}
	    	
	    });
	    
	}
	
	public void setNickname(String string) {
		nicknameText.setText(string);
	}
	
	public void setUrl(String string) {
		urlText.setText(string);
	}
	
	public void setNotes(String string) {
		notesText.setText(string);
	}
	
	public void visitSite() {
		
		Uri uri = Uri.parse(urlText.getText().toString());
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
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
