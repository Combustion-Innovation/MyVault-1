package com.myvault.myvault;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddActivity extends Activity {
	
	LinearLayout layoutHolder, inputsLayout;
	TextView titleText;
	
	LayoutInflater mInflater;
	
	int vaultType;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_add);
		mInflater = (LayoutInflater)getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		
		vaultType = getIntent().getExtras().getInt("type");		
		
		layoutHolder = (LinearLayout)findViewById(R.id.add_inputs_holder);
		titleText = (TextView)findViewById(R.id.add_title_textview);
		
		Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		titleText.setTypeface(tf);
		
		
		makeLayout();
		
			
	}
	
	public void makeLayout() {
		
		switch(vaultType) {
		case 0:
			AddAccounts addAccounts = new AddAccounts(this);
			layoutHolder.addView(addAccounts);
			break;
		case 1:
			AddBookmarks addBookmarks = new AddBookmarks(this);
			layoutHolder.addView(addBookmarks);
			break;
		case 2:
			AddCars addCars = new AddCars(this);
			layoutHolder.addView(addCars);
			break;
		case 3:
			AddContacts addContacts = new AddContacts(this);
			layoutHolder.addView(addContacts);
			break;
		case 4:
			AddCreditCard addCC = new AddCreditCard(this);
			layoutHolder.addView(addCC);
			break;
		case 5:
			AddInsurance addInsurance = new AddInsurance(this);
			layoutHolder.addView(addInsurance);
			break;
		case 6:
			AddNotes addNotes = new AddNotes(this);
			layoutHolder.addView(addNotes);
			break;
		case 7:
			AddPasswords addPasswords = new AddPasswords(this);
			layoutHolder.addView(addPasswords);
			break;
		case 8:
			
			break;
		case 9:
			AddReminders addReminders = new AddReminders(this);
			layoutHolder.addView(addReminders);
			break;
		case 10:
			AddSocialSec addSS = new AddSocialSec(this);
			layoutHolder.addView(addSS);
			break;
		
			
		}
		
		
	}
	
	
	
}
