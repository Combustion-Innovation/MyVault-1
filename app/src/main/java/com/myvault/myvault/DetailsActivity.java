package com.myvault.myvault;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailsActivity extends Activity implements ViewContacts.Communicator, ViewBookmarks.Communicator, ViewPasswords.Communicator, AddAccounts.Communicator, AddBookmarks.Communicator, AddCars.Communicator, AddContacts.Communicator, AddCreditCard.Communicator, AddInsurance.Communicator, AddNotes.Communicator, AddPasswords.Communicator, AddReminders.Communicator, AddSocialSec.Communicator{
	
	Context context;
	
	Button deleteButton, editButton, backButton, cancelButton;
	TextView topText;
	LinearLayout btnLayout, viewButtonsLayout, btnLeft, btnMid, btnRight, cancelLayout;
	LinearLayout dataHolder;
	LinearLayout viewHolder, editHolder;
	
	int vaultType;
	int id;
	String[] vaultStrings = {"Accounts", "Bookmarks", "Cars", "Contacts", "Credit Cards", "Insurance", "Notes", "Passwords", "", "Reminders", "Social Security"};
	
	boolean isEdited = false;
	boolean isEditMode = false;
	boolean mShowingBack = false;
	boolean isValidPause = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(LayoutParams.FLAG_SECURE,
                LayoutParams.FLAG_SECURE);

		setContentView(R.layout.activity_view);
		
		context = this;
		
		
		
		vaultType = getIntent().getIntExtra("type", -1);
		id = getIntent().getIntExtra("id", -1);
		
		if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.details_buttons_container, new CardFrontFragment())
                    .commit();
        }
		
		
		
		
		btnLayout = (LinearLayout)findViewById(R.id.details_buttons_container);
		cancelLayout = (LinearLayout)findViewById(R.id.details_cancel_layout);
		viewButtonsLayout = (LinearLayout)findViewById(R.id.details_buttons_layout);
		
		
		
		dataHolder = (LinearLayout)findViewById(R.id.details_data_holder);
		viewHolder = (LinearLayout)findViewById(R.id.details_view_holder);
		editHolder = (LinearLayout)findViewById(R.id.details_edit_holder);
		
		topText = (TextView)findViewById(R.id.details_top_text);
		
		setLayout();
		
		
		
		setData();
		isValidPause = false;
		
		
	}
	
	public void setLayout() {
		
		
		topText.setText(vaultStrings[vaultType]);
		
		Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		topText.setTypeface(tf);
		
		
		
		
		
		
	}
	
	public void setData() {
		
		DBOpenHelper db = new DBOpenHelper(this);
		viewHolder.removeAllViews();
		
		
		switch(vaultType) {
		case 0:
			Account account = db.selectAccountById(id);
			ViewAccounts viewAccount = new ViewAccounts(this);
			AddAccounts addAccount = new AddAccounts(this);
			viewHolder.addView(viewAccount);
			editHolder.addView(addAccount);
			viewAccount.setBusiness(account.getBusiness());
			viewAccount.setAccount(account.getAccountnum());
			viewAccount.setPhone(account.getBusinessnum());;
			viewAccount.setNotes(account.getNotes());
			addAccount.setBusiness(account.getBusiness());
			addAccount.setAcctNum(account.getAccountnum());
			addAccount.setPhoneNum(account.getBusinessnum());
			addAccount.setNotes(account.getNotes());
			
			addAccount.setEdit(true);
			addAccount.setId(id);
			
			addAccount.setCommunicator(this);
			
			break;
		case 1:
			Bookmark bookmark = db.selectBookmarkById(id);
			ViewBookmarks viewBookmark = new ViewBookmarks(this);
			AddBookmarks addBookmark = new AddBookmarks(this);
			viewHolder.addView(viewBookmark);
			editHolder.addView(addBookmark);
			viewBookmark.setNickname(bookmark.getNickname());
			viewBookmark.setUrl(bookmark.getUrl());
			viewBookmark.setNotes(bookmark.getNotes());
			addBookmark.setNickname(bookmark.getNickname());
			addBookmark.setURL(bookmark.getUrl());
			addBookmark.setNotes(bookmark.getNotes());
			
			addBookmark.setEdit(true);
			addBookmark.setId(id);
			
			addBookmark.setCommunicator(this);
			viewBookmark.setCommunicator(this);
			
			break;
		case 2:
			Car car = db.selectCarById(id);
			ViewCars viewCar = new ViewCars(this);
			AddCars addCar = new AddCars(this);
			viewHolder.addView(viewCar);
			editHolder.addView(addCar);
			viewCar.setMake(car.getMake());
			viewCar.setModel(car.getModel());
			viewCar.setYear(car.getYear());
			viewCar.setPlate(car.getPlate());
			viewCar.setCompany(car.getCompany());
			viewCar.setPolicy(car.getPolicy());
			viewCar.setAgentName(car.getAgentname());
			viewCar.setAgentNum(car.getAgenttel());
			viewCar.setNotes(car.getNotes());
			addCar.setMake(car.getMake());
			addCar.setModel(car.getModel());
			addCar.setYear(car.getYear());
			addCar.setPlate(car.getPlate());
			addCar.setCompany(car.getCompany());
			addCar.setPolicy(car.getPolicy());
			addCar.setAgentName(car.getAgentname());
			addCar.setAgentNum(car.getAgenttel());
			addCar.setNotes(car.getNotes());
			
			addCar.setEdit(true);
			addCar.setId(id);
			
			addCar.setCommunicator(this);
			
			break;
		case 3:
			Contact contact = db.selectContactById(id);
			ViewContacts viewContact = new ViewContacts(this);
			AddContacts addContact = new AddContacts(this);
			viewHolder.addView(viewContact);
			editHolder.addView(addContact);
			
			viewContact.setName(contact.getName());
			viewContact.setNumber(contact.getNumber());
			viewContact.setNotes(contact.getNotes());
			addContact.setName(contact.getName());
			addContact.setNumber(contact.getNumber());
			addContact.setNotes(contact.getNotes());
			
			addContact.setEdit(true);
			addContact.setId(id);
			
			addContact.setCommunicator(this);
			viewContact.setCommunicator(this);
			
			break;
		case 4:
			CreditCard cc = db.selectCCById(id);
			ViewCreditCard viewCC = new ViewCreditCard(this);
			AddCreditCard addCC = new AddCreditCard(this);
			viewHolder.addView(viewCC);
			editHolder.addView(addCC);
			
			viewCC.setNickname(cc.getNickname());
			viewCC.setName(cc.getCardholder());
			viewCC.setNumber(cc.getNumber());
			viewCC.setExp(cc.getExpiration());
			viewCC.setType(cc.getBrand());
			viewCC.setCVC(cc.getCsv());
			addCC.setNickname(cc.getNickname());
			addCC.setName(cc.getCardholder());
			addCC.setNumber(cc.getNumber());
			addCC.setExp(cc.getExpiration());
			addCC.setType(cc.getBrand());
			addCC.setCVC(cc.getCsv());
			
			addCC.setEdit(true);
			addCC.setId(id);
			
			addCC.setCommunicator(this);
			
			break;
		case 5:
			Insurance insurance = db.selectInsuranceById(id);
			ViewInsurance viewInsurance = new ViewInsurance(this);
			AddInsurance addInsurance = new AddInsurance(this);
			viewHolder.addView(viewInsurance);
			editHolder.addView(addInsurance);
			
			viewInsurance.setProvider(insurance.getInsurancecompany());
			viewInsurance.setMemberName(insurance.getMember());
			viewInsurance.setMemberId(insurance.getMemid());
			viewInsurance.setGroup(insurance.getGroupnum());
			viewInsurance.setPlan(insurance.getCode());
			viewInsurance.setType(insurance.getType());
			viewInsurance.setNotes(insurance.getNotes());
			
			addInsurance.setProvider(insurance.getInsurancecompany());
			addInsurance.setMemberName(insurance.getMember());
			addInsurance.setMemberId(insurance.getMemid());
			addInsurance.setGroup(insurance.getGroupnum());
			addInsurance.setPlan(insurance.getCode());
			addInsurance.setType(insurance.getType());
			addInsurance.setNotes(insurance.getNotes());
			
			addInsurance.setEdit(true);
			addInsurance.setId(id);
			
			addInsurance.setCommunicator(this);
			
			
			break;
		case 6:
			Note note = db.selectNoteById(id);
			ViewNotes viewNote = new ViewNotes(this);
			AddNotes addNote = new AddNotes(this);
			viewHolder.addView(viewNote);
			editHolder.addView(addNote);
			
			viewNote.setTitle(note.getTitle());
			viewNote.setNotes(note.getNote());
			
			addNote.setTitle(note.getTitle());
			addNote.setNotes(note.getNote());
			
			addNote.setEdit(true);
			addNote.setId(id);
			
			addNote.setCommunicator(this);
			
			break;
		case 7:
			Password password = db.selectPasswordById(id);
			ViewPasswords viewPassword = new ViewPasswords(this);
			AddPasswords addPassword = new AddPasswords(this);
			viewHolder.addView(viewPassword);
			editHolder.addView(addPassword);
			
			viewPassword.setWebsite(password.getWebsite());
			viewPassword.setUsername(password.getUsername());
			viewPassword.setPassword(password.getPassword());
			viewPassword.setNotes(password.getNotes());
			
			addPassword.setWebsite(password.getWebsite());
			addPassword.setUsername(password.getUsername());
			addPassword.setPassword(password.getPassword());
			addPassword.setNotes(password.getNotes());
			
			addPassword.setEdit(true);
			addPassword.setId(id);
			
			addPassword.setCommunicator(this);
			viewPassword.setCommunicator(this);
			
			break;
		case 8:
			/*
			 * 
			 * 
			 */
			break;
		case 9:
			Reminder reminder = db.selectReminderById(id);
			ViewReminders viewReminder = new ViewReminders(this);
			AddReminders addReminder = new AddReminders(this);
			viewHolder.addView(viewReminder);
			editHolder.addView(addReminder);
			
			viewReminder.setTitle(reminder.getName());
			viewReminder.setAlias(reminder.getNickname());
			viewReminder.setDate(reminder.getAlertdate());
			viewReminder.setTime(reminder.getAlerttime());
			viewReminder.setLocation(reminder.getLocation());
			viewReminder.setNotes(reminder.getNotes());
			
			addReminder.setTitle(reminder.getName());
			addReminder.setAlias(reminder.getNickname());
			addReminder.setDate(reminder.getAlertdate());
			addReminder.setTime(reminder.getAlerttime());
			addReminder.setLocation(reminder.getLocation());
			addReminder.setNotes(reminder.getNotes());
			
			addReminder.setEdit(true);
			addReminder.setId(id);
			
			addReminder.setCommunicator(this);
			
			break;
		case 10:
			SocialSecurity ss = db.selectSSById(id);
			ViewSocialSec viewSS = new ViewSocialSec(this);
			AddSocialSec addSS = new AddSocialSec(this);
			viewHolder.addView(viewSS);
			editHolder.addView(addSS);
			
			viewSS.setName(ss.getName());
			viewSS.setNumber(ss.getSsnumber());
			viewSS.setNotes(ss.getNotes());
			
			addSS.setName(ss.getName());
			addSS.setNumber(ss.getSsnumber());
			addSS.setNotes(ss.getNotes());
			
			addSS.setEdit(true);
			addSS.setId(id);
			
			addSS.setCommunicator(this);
			
			
			break;
		}
	}
	
	
	
	public void goBack() {
		
		Intent i = new Intent();
		if(isEdited) {
			setResult(RESULT_OK, i);
			Log.d("result", "ok");
		}
		else {
			setResult(RESULT_CANCELED, i);
			Log.d("result", "cancel");
		}
		isValidPause = true;
		finish();
		
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_rght_out);   
		
	}
	
	public void editClicked() {
		if(!isEditMode) {
			//change to edit screen
			isEditMode = true;
			viewHolder.setVisibility(View.GONE);
			editHolder.setVisibility(View.VISIBLE);
			flipMenu();
		}
		else {
			isEditMode = false;
			viewHolder.setVisibility(View.VISIBLE);
			editHolder.setVisibility(View.GONE);
			flipMenu();
		}
	}
	
	public void deleteClicked() {
		//make a prompt to delete
		new AlertDialog.Builder(this)
			.setTitle("Delete Entry")
			.setMessage("Are you sure you want to delete this entry?")
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					deleteEntry();
					
					
				}
			})
			.setNegativeButton("No", null)
			.show();
		
	}
	
	public void cancelClicked() {
		//revert to view screen
		isEditMode = false;
		viewHolder.setVisibility(View.VISIBLE);
		editHolder.setVisibility(View.GONE);
		//editHolder.removeAllViews();
		flipMenu();
		
	}
	
	public void deleteEntry() {
		DBOpenHelper db = new DBOpenHelper(this);
		switch(vaultType) {
		case 0:
			
			db.deleteAccount(id);
			break;
		case 1:
			db.deleteBookmark(id);
			break;
		case 2:
			db.deleteCar(id);
			break;
		case 3:
			db.deleteContact(id);
			break;
		case 4:
			db.deleteCC(id);
			break;
		case 5:
			db.deleteInsurance(id);
			break;
		case 6:
			db.deleteNote(id);
			break;
		case 7:
			db.deletePassword(id);
			break;
		case 8:
			
			break;
		case 9:
			db.deleteReminder(id);
			break;
		case 10:
			db.deleteSS(id);
			break;
		}
		
		isEdited = true;
		goBack();
	}

	

	@Override
	public void onResult(ObjectHolder holder) {
		// TODO Auto-generated method stub
		isEdited = true;
		cancelClicked();
		setData();
	}

	@Override
	public void changeToDetails() {
		// TODO Auto-generated method stub
		View view = this.getCurrentFocus();
	    if (view != null) {
	        //InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
	        //inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	    }
		viewHolder.setVisibility(View.VISIBLE);
		editHolder.setVisibility(View.GONE);
		editHolder.removeAllViews();
		isEdited = true;
		setData();
		//flipMenu();
		
		
	}
	
	@SuppressLint("NewApi")
	public void flipMenu() {
		
		if (mShowingBack) {
	        getFragmentManager().popBackStack();
	        mShowingBack = false;
	        isEditMode = false;
	        return;
	    }

	    // Flip to the back.

	    mShowingBack = true;
	    isEditMode = true;

	    // Create and commit a new fragment transaction that adds the fragment for the back of
	    // the card, uses custom animations, and is part of the fragment manager's back stack.

	    getFragmentManager()
	            .beginTransaction()

	            // Replace the default fragment animations with animator resources representing
	            // rotations when switching to the back of the card, as well as animator
	            // resources representing rotations when flipping back to the front (e.g. when
	            // the system Back button is pressed).
	            .setCustomAnimations(
	                    R.anim.flip2in, R.anim.flip2out,
	                    R.anim.flip1in, R.anim.flip1out)

	            // Replace any fragments currently in the container view with a fragment
	            // representing the next page (indicated by the just-incremented currentPage
	            // variable).
	            .replace(R.id.details_buttons_container, new CardBackFragment())

	            // Add this transaction to the back stack, allowing users to press Back
	            // to get to the front of the card.
	            .addToBackStack(null)

	            // Commit the transaction.
	            .commit();
		
	}
	
	public class CardFrontFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	
        	View view = inflater.inflate(R.layout.details_toplayout1, container, false);
        	
        	makeFrontLayout(view);
    		
    		
        	return view;
        }
    }

    /**
     * A fragment representing the back of the card.
     */
    public class CardBackFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	View view = inflater.inflate(R.layout.details_toplayout2, container, false);
        	cancelButton = (Button)view.findViewById(R.id.details_cancel_button);
        	Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
        	cancelButton.setTypeface(tf);
        	cancelButton.setOnClickListener(new OnClickListener() {

    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				View view = getCurrentFocus();
    			    if (view != null) {
    			        //InputMethodManager inputManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
    			        //inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    			    }
    				//((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    				changeToDetails();
    				flipMenu();
    			}
    			
    		});
            return view;
        }
    }
    
    public void makeFrontLayout(View view) {
    	
    	
    	DisplayMetrics metrics = getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		
		btnLeft = (LinearLayout)view.findViewById(R.id.details_backbtn_layout);
		btnMid = (LinearLayout)view.findViewById(R.id.details_editbtn_layout);
		btnRight = (LinearLayout)view.findViewById(R.id.details_deletebtn_layout);
		
		deleteButton = (Button)view.findViewById(R.id.details_delete_button);
		editButton = (Button)view.findViewById(R.id.details_edit_button);
		backButton = (Button)view.findViewById(R.id.details_back_buttton);
		
		btnLeft.getLayoutParams().width = width/3;
		btnMid.getLayoutParams().width = width/3;
		btnRight.getLayoutParams().width = width/3;
		
		btnLeft.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goBack();
			}
		});
		
		btnMid.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editClicked();
			}
		});
		
		btnRight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				deleteClicked();
			}
		});
		
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goBack();
			}
		});
		
		editButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editClicked();
			}
			
		});
		
		deleteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				deleteClicked();
				
				//makeAddLayout();
			}
		});
		
		
    }
	
    @Override
    protected void onPause() {
    	super.onPause();
    	if(!isValidPause) {
	    	UserData userdata = new UserData(this);
			if(userdata.isLockSet()) {
		    	Intent i = new Intent(this, SleepLockActivity.class);
		    	startActivity(i);
			}
    	}
    	
    }
    
    @Override
    public void onBackPressed() {
    	goBack();
    }

	@Override
	public void setValidPause() {
		// TODO Auto-generated method stub
		isValidPause = true;
	}

	@Override
	public void setInvalidPause() {
		// TODO Auto-generated method stub
		isValidPause = false;
	}
	
	

}
