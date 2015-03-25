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



public class AddCars extends LinearLayout {
	
	Context context;
	Communicator comm;
	ImageButton deleteButton;
	Button submitButton;
	EditText makeInput, modelInput, yearInput, plateInput, companyInput, policyInput, agentNameInput, agentNumInput, notesInput;
	String make, model, year, plate, company, policy, agentName, agentNum, notes;
	
	boolean isEdit = false;
	int id;
	
	public AddCars(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		
	 
	}

	public AddCars(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

	}
	
	public AddCars(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
		init();
	}
	
	
	public void init() {
		
		LayoutInflater inflater = (LayoutInflater) context
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.add_cars, this, true);
		
		
		submitButton = (Button)findViewById(R.id.ac_submit_button);
		
		makeInput = (EditText)findViewById(R.id.ac_make_input);
		modelInput = (EditText)findViewById(R.id.ac_model_input);
		yearInput = (EditText)findViewById(R.id.ac_year_input);
		plateInput = (EditText)findViewById(R.id.ac_plate_input);
		companyInput = (EditText)findViewById(R.id.ac_company_input);
		policyInput = (EditText)findViewById(R.id.ac_policy_input);
		agentNameInput = (EditText)findViewById(R.id.ac_agentname_input);
		agentNumInput = (EditText)findViewById(R.id.ac_agentnum_input);
		notesInput = (EditText)findViewById(R.id.ac_notes_input);
		
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
		
		make = makeInput.getText().toString();
		model = modelInput.getText().toString();
		year = yearInput.getText().toString();
		plate = plateInput.getText().toString();
		company = companyInput.getText().toString();
		policy = policyInput.getText().toString();
		agentName = agentNameInput.getText().toString();
		agentNum = agentNumInput.getText().toString();
		notes = notesInput.getText().toString();
		
		
		if(!isEdit) {
			if(checkInputs()) {
				
				//send to db
				DBOpenHelper db = new DBOpenHelper(context);
				db.addCar(make, model, year, plate, company, policy, agentName, agentNum, notes);
				comm.changeToDetails();
				comm.flipMenu();
			}
		}
		else {
			DBOpenHelper db = new DBOpenHelper(context);
			db.updateCar(id, make, model, plate, year, company, policy, agentName, agentNum, notes);
			comm.changeToDetails();
			comm.flipMenu();
		}
		
		
	}
	
	public boolean checkInputs() {
		
		if(make.length() < 1) {
			Toast.makeText(this.context, "Please enter car make", Toast.LENGTH_LONG).show();
			return false;
		}
		
		
		
		return true;
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
		public void flipMenu();
		public void changeToDetails();
		
	}
}