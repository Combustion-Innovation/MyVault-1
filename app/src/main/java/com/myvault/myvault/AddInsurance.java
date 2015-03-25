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



public class AddInsurance extends LinearLayout {
	
	Context context;
	Communicator comm;
	ImageButton deleteButton;
	Button submitButton;
	EditText providerInput, memberNameInput, memberIdInput, groupInput, typeInput, planInput, notesInput;
	
	String provider, memberName, memberId, group, type, plan, notes;
	
	boolean isEdit = false;
	int id;
	
	public AddInsurance(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		
	 
	}

	public AddInsurance(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

	}
	
	public AddInsurance(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
		init();
	}
	
	
	public void init() {
		
		LayoutInflater inflater = (LayoutInflater) context
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.add_insurance, this, true);
		
		
		submitButton = (Button)findViewById(R.id.ai_submit_button);
		
		providerInput = (EditText)findViewById(R.id.ai_provider_input);
		memberNameInput = (EditText)findViewById(R.id.ai_membername_input);
		memberIdInput = (EditText)findViewById(R.id.ai_memberid_input);
		groupInput = (EditText)findViewById(R.id.ai_group_input);
		typeInput = (EditText)findViewById(R.id.ai_type_input);
		planInput = (EditText)findViewById(R.id.ai_plancodes_input);
		notesInput = (EditText)findViewById(R.id.ai_notes_input);
		
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		providerInput.setTypeface(tf);
		memberNameInput.setTypeface(tf);
		memberIdInput.setTypeface(tf);
		groupInput.setTypeface(tf);
		typeInput.setTypeface(tf);
		planInput.setTypeface(tf);
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
		
		provider = providerInput.getText().toString();
		memberName = memberNameInput.getText().toString();
		memberId = memberIdInput.getText().toString();
		group = groupInput.getText().toString();
		type = typeInput.getText().toString();
		plan = planInput.getText().toString();
		notes = notesInput.getText().toString();
		
		if(!isEdit) {
			if(checkInputs()) {
				
				//send to db
				DBOpenHelper db = new DBOpenHelper(context);
				db.addInsurance(provider, memberName, memberId, group, type, plan, notes);
				comm.changeToDetails();
				comm.flipMenu();
				
			}
		}
		else {
			DBOpenHelper db = new DBOpenHelper(context);
			db.updateInsurance(id, provider, memberName, memberId, group, type, plan, notes);
			comm.changeToDetails();
			comm.flipMenu();
		}
		
		
		
	}
	
	public boolean checkInputs() {
		
		if(provider.length() < 1) {
			Toast.makeText(this.context, "Please enter provider", Toast.LENGTH_LONG).show();
			return false;
		}
		
		
		return true;
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
