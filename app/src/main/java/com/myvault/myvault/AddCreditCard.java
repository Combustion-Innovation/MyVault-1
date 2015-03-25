package com.myvault.myvault;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;



public class AddCreditCard extends LinearLayout {
	
	Context context;
	Communicator comm;
	ImageButton deleteButton;
	Button submitButton;
	EditText nicknameInput, nameInput, numberInput, expInput, typeInput, cvcInput;
	
	String nickname, name, number, exp, type, cvc;
	
	Calendar expDate;
	
	boolean isEdit = false;
	int id;
	
	
	
	public AddCreditCard(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		
	 
	}

	public AddCreditCard(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

	}
	
	public AddCreditCard(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
		init();
	}
	
	
	public void init() {
		
		LayoutInflater inflater = (LayoutInflater) context
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View view = inflater.inflate(R.layout.add_creditcard, this, true);
	    
	    expDate = Calendar.getInstance();
	    expDate.clear();
		
		
		submitButton = (Button)view.findViewById(R.id.acc_submit_button);
		
		nicknameInput = (EditText)view.findViewById(R.id.acc_nickname_input);
		nameInput = (EditText)view.findViewById(R.id.acc_carholder_input);
		numberInput = (EditText)view.findViewById(R.id.acc_number_input);
		expInput = (EditText)view.findViewById(R.id.acc_exp_input);
		typeInput = (EditText)view.findViewById(R.id.acc_type_input);
		cvcInput = (EditText)view.findViewById(R.id.acc_cvc_input);
		
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		
		nicknameInput.setTypeface(tf);
		nameInput.setTypeface(tf);
		numberInput.setTypeface(tf);
		expInput.setTypeface(tf);
		typeInput.setTypeface(tf);
		cvcInput.setTypeface(tf);
		submitButton.setTypeface(tf);
		
		
		
		expInput.setFocusable(false);
		expInput.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				makeDatePicker();
			}
			
		});
		
		
		submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				submitBtnClick();
			}
		});
		
		
		
		
	}
	
	public void fixLayout() {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		
		expInput.setWidth(width/3);
		typeInput.setWidth(width/3);
		cvcInput.setWidth(width/3);
	}
	
	public void makeDatePicker() {
		
		Calendar time = Calendar.getInstance();
		
		
		DatePickerDialog datePickerDialog = new DatePickerDialog(context, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				String monthString = "";
				if (monthOfYear + 1 < 10) {
					monthString += "0";
				}
				monthString += Integer.toString(monthOfYear + 1);
				expInput.setText(monthString + "/" + Integer.toString(year - 2000));
				
				expDate.set(Calendar.YEAR, year);
				expDate.set(Calendar.MONTH, monthOfYear);
				expDate.set(Calendar.DAY_OF_MONTH, 1);
				Log.d("expdate", String.valueOf(expDate.getTimeInMillis()));
				
			}
			
		}, time.get(Calendar.YEAR), time.get(Calendar.MONTH), 1);
		((ViewGroup) datePickerDialog.getDatePicker()).findViewById(getResources().getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
		datePickerDialog.show();
		
		
	}
	
	public void deleteBtnClick() {
		
	}
	
	public void submitBtnClick() {
		
		nickname = nicknameInput.getText().toString();
		name = nameInput.getText().toString();
		number = numberInput.getText().toString();
		exp = expInput.getText().toString();
		type = typeInput.getText().toString();
		cvc = cvcInput.getText().toString();
		long expMillis = expDate.getTimeInMillis();
		
		if(!isEdit) {
			if(checkInputs()) {
				
				//send to db
				DBOpenHelper db = new DBOpenHelper(context);
				
				db.addCC(nickname, name, type, number, exp, cvc, expMillis);
				comm.changeToDetails();
				comm.flipMenu();
			}
			
		}
		else {
			DBOpenHelper db = new DBOpenHelper(context);
			db.updateCC(id, nickname, name, type, number, exp, cvc, expMillis);
			comm.changeToDetails();
			comm.flipMenu();
		}
		
	}
	
	public boolean checkInputs() {
		
		if(nickname.length() < 1) {
			Toast.makeText(this.context, "Please enter card nickname", Toast.LENGTH_LONG).show();
			return false;
		}
		if(number.length() < 1) {
			Toast.makeText(this.context, "Please enter card number", Toast.LENGTH_LONG).show();
			return false;
		}
		if(exp.length() < 1) {
			Toast.makeText(this.context, "Please enter card expiration date", Toast.LENGTH_LONG).show();
			return false;
		}
		if(type.length() < 1) {
			Toast.makeText(this.context, "Please enter card type", Toast.LENGTH_LONG).show();
			return false;
		}
		if(cvc.length() < 1) {
			Toast.makeText(this.context, "Please enter CVC number (on back)", Toast.LENGTH_LONG).show();
			return false;
		}
		
		
		return true;
	}
	
	public void setNickname(String string) {
		nicknameInput.setText(string);
	}
	
	public void setName(String string) {
		nameInput.setText(string);
	}
	
	public void setNumber(String string) {
		numberInput.setText(string);
	}
	
	public void setExp(String string) {
		expInput.setText(string);
	}
	
	public void setType(String string) {
		typeInput.setText(string);
	}
	
	public void setCVC(String string) {
		cvcInput.setText(string);
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
