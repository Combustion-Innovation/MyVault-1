package com.myvault.myvault;



import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;



public class AddReminders extends LinearLayout {
	
	Context context;
	Communicator comm;
	ImageButton deleteButton;
	Button submitButton;
	EditText titleInput, aliasInput, dateInput, timeInput, locationInput, notesInput;
	
	String title, alias, date, time, location, notes;
	
	int alarmDateMillis, alarmTimeMillis;
	long alarmMillis;
	Calendar alarmDate = null;
	Calendar alarmTime = null;
	Calendar alarmCal = null;
	
	
	boolean isEdit = false;
	int id;
	
	public AddReminders(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		
	 
	}

	public AddReminders(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

	}
	
	public AddReminders(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
		init();
	}
	
	
	public void init() {
		
		LayoutInflater inflater = (LayoutInflater) context
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.add_reminders, this, true);
		
		submitButton = (Button)findViewById(R.id.ar_submit_button);
		
		titleInput = (EditText)findViewById(R.id.ar_title_input);
		aliasInput = (EditText)findViewById(R.id.ar_alias_input);
		dateInput = (EditText)findViewById(R.id.ar_date_input);
		timeInput = (EditText)findViewById(R.id.ar_time_input);
		locationInput = (EditText)findViewById(R.id.ar_location_input);
		notesInput = (EditText)findViewById(R.id.ar_notes_input);
		
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		
		titleInput.setTypeface(tf);
		aliasInput.setTypeface(tf);
		dateInput.setTypeface(tf);
		timeInput.setTypeface(tf);
		locationInput.setTypeface(tf);
		notesInput.setTypeface(tf);
		submitButton.setTypeface(tf);
		
		alarmDate = Calendar.getInstance();
		alarmTime = Calendar.getInstance();
		alarmCal = Calendar.getInstance();
		alarmDate.clear();
		alarmTime.clear();
		alarmCal.clear();
		
		
		dateInput.setFocusable(false);
		dateInput.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				makeDatePicker();
			}
			
		});
		
		timeInput.setFocusable(false);
		timeInput.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				makeTimePicker();
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
	
	public void makeDatePicker() {
		
		Calendar time = Calendar.getInstance();
		
		
		
		
		DatePickerDialog datePickerDialog = new DatePickerDialog(context, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				
				String monthString = "";
				
				monthString += Integer.toString(monthOfYear + 1);
				String dayString = "";
				if(dayOfMonth < 10) {
					dayString += "0";
				}
				dayString += Integer.toString(dayOfMonth);
				
				dateInput.setText(monthString + "/" + dayString + "/" + Integer.toString(year - 2000));
				alarmDate.clear();
				alarmDate.set(year, monthOfYear, dayOfMonth);
				
				
			}
			
		}, time.get(Calendar.YEAR), time.get(Calendar.MONTH), time.get(Calendar.DAY_OF_MONTH));
		
		datePickerDialog.show();
		
		
	}
	
	public void makeTimePicker() {
		
		Calendar cal = Calendar.getInstance();
		
		Log.d("time", cal.toString());
		Log.d("hour", Integer.toString(cal.get(Calendar.HOUR_OF_DAY)));
		Log.d("min", Integer.toString(cal.get((Calendar.MINUTE))));
		
		
		
		TimePickerDialog timePickerDialog = new TimePickerDialog(context, new OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub
				//int hour = hourOfDay;
				
				String ampm = "am";
				String min = "";
				int hour = hourOfDay;
				Log.d("hour", Integer.toString(hourOfDay));
				if(hourOfDay == 0) {
					hour = 12;
					ampm = "am";
				}
				if(hour > 12) {
					hour -= 12;
					ampm = "pm";
				}
				if(minute < 10) {
					min = "0" + Integer.toString(minute);
				}
				else {
					min = Integer.toString(minute);
				}
				
				
				
				String string = Integer.toString(hour) + ":" + min + " " + ampm;
				timeInput.setText(string);
				alarmTime.clear();
				alarmTime.set(Calendar.MINUTE, minute);
				alarmTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
				
				
			}
			
		}, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);
		timePickerDialog.show();
		
		
	}
	
	public void deleteBtnClick() {
		
	}
	
	public void submitBtnClick() {
		
		title = titleInput.getText().toString();
		alias = aliasInput.getText().toString();
		date = dateInput.getText().toString();
		time = timeInput.getText().toString();
		location = locationInput.getText().toString();
		notes = notesInput.getText().toString();
		Calendar thisAlarm = Calendar.getInstance();
		thisAlarm.clear();
		thisAlarm.set(alarmDate.get(Calendar.YEAR), alarmDate.get(Calendar.MONTH), alarmDate.get(Calendar.DAY_OF_MONTH), alarmTime.get(Calendar.HOUR_OF_DAY), alarmTime.get(Calendar.MINUTE));
		alarmMillis = thisAlarm.getTimeInMillis();
		
		if(!isEdit) {
			if(checkInputs()) {
				
				//send to db
				DBOpenHelper db = new DBOpenHelper(context);
				db.addReminder(alias, title, date, time, location, notes, alarmMillis);
				comm.changeToDetails();
				comm.flipMenu();
				
				
			}
		}
		else {
			DBOpenHelper db = new DBOpenHelper(context);
			db.updateReminder(id, alias, title, date, time, location, notes, alarmMillis);
			comm.changeToDetails();
			comm.flipMenu();
		}
		
	}
	
	public boolean checkInputs() {
		
		if(title.length() < 1) {
			Toast.makeText(this.context, "Please enter a title", Toast.LENGTH_LONG).show();
			return false;
		}
		if(alias.length() < 1) {
			Toast.makeText(this.context, "Please enter an alias", Toast.LENGTH_LONG).show();
			return false;
		}
		if(date.length() < 1) {
			Toast.makeText(this.context, "Please enter a date", Toast.LENGTH_LONG).show();
			return false;
		}
		if(time.length() < 1) {
			Toast.makeText(this.context, "Please enter a time", Toast.LENGTH_LONG).show();
			return false;
		}
		if(location.length() < 1) {
			Toast.makeText(this.context, "Please enter a location", Toast.LENGTH_LONG).show();
			return false;
		}
		
		return true;
	}
	
	public void setTitle(String string) {
		titleInput.setText(string);
	}
	
	public void setAlias(String string) {
		aliasInput.setText(string);
	}
	
	public void setDate(String string) {
		dateInput.setText(string);
	}
	
	public void setTime(String string) {
		timeInput.setText(string);
	}
	
	public void setLocation(String string) {
		locationInput.setText(string);
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
