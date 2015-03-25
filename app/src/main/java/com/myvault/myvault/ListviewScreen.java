package com.myvault.myvault;

import java.util.ArrayList;

import com.myvault.myvault.util.SystemUiHider;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class ListviewScreen extends Activity implements ListviewAdapter.Communicator, AddAccounts.Communicator, AddBookmarks.Communicator, AddCars.Communicator, AddContacts.Communicator, AddCreditCard.Communicator, AddInsurance.Communicator, AddNotes.Communicator, AddPasswords.Communicator, AddReminders.Communicator, AddSocialSec.Communicator {
	
	
	
	
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;
	
	Context context;
	
	DBOpenHelper db;
	LayoutInflater mInflater;
	
	Class<?> targetActivity;
	Class<?> targetViewActivity;
	
	LinearLayout btnLayoutL, btnLayoutM, btnLayoutR, addFormHolder, listHolder, topLayout, formTopLayout, noListHolder, dataHolder, topContainer, formBackLayout;
	TextView titleText, noListText;
	RelativeLayout listContainer;
	Button addButton, editButton, backButton, formBackButton, deleteBackButton;
	View formView;
	ListView listView;
	ListviewAdapter adapter;
	
	ArrayList<ListData> listData;
	ArrayList<?> queryData;
	ArrayList<ArrayList<?>> listTypes;
	
	InputMethodManager imm;
	
	
	
	int vaultType;
	
	String title;
	
	boolean isDeleteMode = false;
	boolean isAddMode = false;
	boolean mShowingBack = false;
	boolean isValidPause = true;
	boolean backPressed;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

		setContentView(R.layout.listview_screen);
		context = this;
		backPressed = false;
		vaultType = getIntent().getExtras().getInt("type");
		
		imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
		
				
		listView = (ListView)findViewById(R.id.list_list);
		topContainer = (LinearLayout)findViewById(R.id.list_top_layout);
		
		//topContainer.removeAllViews();
		
		if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.list_top_layout, new CardFrontFragment())
                    .commit();
        }
		
				
		addFormHolder = (LinearLayout)findViewById(R.id.list_addform_holder);
		topLayout = (LinearLayout)findViewById(R.id.list_buttons_layout);
		formTopLayout = (LinearLayout)findViewById(R.id.list_addform_toplayout);
		noListHolder = (LinearLayout)findViewById(R.id.list_nolist_holder);
		listContainer = (RelativeLayout)findViewById(R.id.list_list_holder);
		titleText = (TextView)findViewById(R.id.list_title_textview);
		noListText = (TextView)findViewById(R.id.list_nolist_text);
		dataHolder = (LinearLayout)findViewById(R.id.list_list_layout);
		
		
		
		getDataFromDB();
		
		setLayout();
		isValidPause = false;
		
	}
		


	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
	}
	
	@Override
	public void onBackPressed() {
		
		backPressed = true;
		Log.d("backpressed", Boolean.toString(backPressed));
	    super.onBackPressed();
	    
	    leaveActivity();
	}
	
	
	
	public void leaveActivity(){
			finish();
			isValidPause = true;
		  overridePendingTransition(R.anim.slide_left_in, R.anim.slide_rght_out);   
	}
	
	public void setLayout() {
		
		listContainer.setVisibility(View.VISIBLE);
		noListHolder.setVisibility(View.GONE);
		
		listView.setVisibility(View.VISIBLE);
		
		Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		titleText.setTypeface(tf);
		noListText.setTypeface(tf);
		
		titleText.setText(title);
				
		listView.setBackgroundResource(R.drawable.vaultbg);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
					goToDetails(position);
				
				
			}

			
		});
			
		
		if(queryData.size() < 1) {
			listContainer.setVisibility(View.GONE);
			addFormHolder.setVisibility(View.GONE);
			noListHolder.setVisibility(View.VISIBLE);
			
			
		}
		
		
		
	}
	
	public void goToDetails(int position) {
		
		int id = -1;
		
		switch(vaultType) {
		case 0:
			//Account
			Account account = (Account) queryData.get(position);
			id = account.getId();
						
			break;
		case 1:
			//Bookmark
			Bookmark bookmark = (Bookmark)queryData.get(position);
			id = bookmark.getId();
			
			break;
		case 2:
			//Car
			Car car = (Car)queryData.get(position);
			id = car.getId();
			
			break;
		case 3:
			//Contact
			Contact contact = (Contact)queryData.get(position);
			id = contact.getId();
			
			break;
		case 4:
			//CC
			CreditCard cc = (CreditCard)queryData.get(position);
			id = cc.getId();
			
			break;
		case 5:
			//Insurance
			Insurance insurance = (Insurance)queryData.get(position);
			id = insurance.getId();
			
			break;
		case 6:
			//Note
			Note note = (Note)queryData.get(position);
			id = note.getId();
			
			break;
		case 7:
			//Password
			Password password = (Password)queryData.get(position);
			id = password.getId();
			
			break;
		case 8:
			//Media
			
			break;
		case 9:
			//Reminder
			Reminder reminder = (Reminder)queryData.get(position);
			id = reminder.getId();
			
			break;
		case 10:
			//SS
			SocialSecurity ss = (SocialSecurity)queryData.get(position);
			id = ss.getId();
			
			break;
			
		
		}
		
		Intent i = new Intent(this, DetailsActivity.class);
		
		i.putExtra("type", vaultType);
		i.putExtra("id", id);
		isValidPause = true;
		startActivityForResult(i, 2);
		this.overridePendingTransition(R.anim.slide_right_in,
	               R.anim.slide_left_out);
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		//Return from Add activity
		if(requestCode == 1) {
			if(resultCode == RESULT_OK) {
				getDataFromDB();
				adapter.notifyDataSetChanged();
			}
			
		}
		
		//Return from Edit activity
		if(requestCode == 2) {
			if(resultCode == RESULT_OK) {
				setLayout();
				getDataFromDB();
				if(queryData.size() < 1) {
					flipMenu();
					flipDeleteMenu();
				
				}
				adapter.notifyDataSetChanged();
			}
			
		}
		isValidPause = false;
	}
	
	@Override
	public void deleteItem(int position) {
		// TODO Auto-generated method stub
		
		int id;
		
		switch(vaultType) {
		case 0:
			//Account
			Account account = (Account) queryData.get(position);
			id = account.getId();
			db.deleteAccount(id);
			
			
			break;
		case 1:
			//Bookmark
			Bookmark bookmark = (Bookmark)queryData.get(position);
			id = bookmark.getId();
			db.deleteBookmark(id);
			
			break;
		case 2:
			//Car
			Car car = (Car)queryData.get(position);
			id = car.getId();
			db.deleteCar(id);
			
			break;
		case 3:
			//Contact
			Contact contact = (Contact)queryData.get(position);
			id = contact.getId();
			db.deleteContact(id);
			
			break;
		case 4:
			//CC
			CreditCard cc = (CreditCard)queryData.get(position);
			id = cc.getId();
			db.deleteCC(id);
			
			break;
		case 5:
			//Insurance
			Insurance insurance = (Insurance)queryData.get(position);
			id = insurance.getId();
			db.deleteInsurance(id);
			
			break;
		case 6:
			//Notes
			
			Note note = (Note)queryData.get(position);
			id = note.getId();
			
			db.deleteNote(id);
						
			break;
		case 7:
			//Password
			Password password = (Password)queryData.get(position);
			id = password.getId();
			db.deletePassword(id);
			
			break;
		case 8:
			//Media
			
			break;
		case 9:
			//Reminder
			Reminder reminder = (Reminder)queryData.get(position);
			id = reminder.getId();
			db.deleteReminder(id);
			
			break;
		case 10:
			//SS
			SocialSecurity ss = (SocialSecurity)queryData.get(position);
			id = ss.getId();
			db.deleteSS(id);
			
			break;
		}
		
		queryData.remove(position);
		listData.remove(position);
		
		
		
		adapter.notifyDataSetChanged();
		
		if(queryData.size() < 1) {
			adapter.showNumber();
			flipDeleteMenu();
			
		}
	}
	
	@SuppressLint("NewApi")
	public void getDataFromDB() {
			
		listData = new ArrayList<ListData>();
		
		db = new DBOpenHelper(this);
				
		switch(vaultType) {
		case 0:
			//Accounts
			
			ArrayList<Account> accountquery = db.selectAllAccounts();
			for(int i=0; i<accountquery.size(); i++) {
				int id = accountquery.get(i).getId();
				String top = accountquery.get(i).getBusiness();
				String mid = accountquery.get(i).getAccountnum();
				String btm = accountquery.get(i).getBusinessnum();
				
				ListData data = new ListData(id, top, mid, btm);
				listData.add(data);
				
				
			}
			
			queryData = accountquery;
			targetActivity = AddActivity.class;
			title = "Accounts";
			
			break;
		case 1:
			//Bookmarks
			ArrayList<Bookmark> bookmarkquery = db.selectAllBookmarks();
			for(int i=0; i<bookmarkquery.size(); i++) {
				int id = bookmarkquery.get(i).getId();
				String top = bookmarkquery.get(i).getNickname();
				String mid = bookmarkquery.get(i).getUrl();
				String btm = bookmarkquery.get(i).getNotes();
				
				ListData data = new ListData(id, top, mid, btm);
				listData.add(data);
				
			}
			
			queryData = bookmarkquery;
			targetActivity = AddActivity.class;
			title = "Bookmarks";
			break;
		case 2:
			//Cars
			ArrayList<Car> carquery = db.selectAllCars();
			for(int i=0; i<carquery.size(); i++) {
				int id = carquery.get(i).getId();
				String top = carquery.get(i).getMake();
				String mid = carquery.get(i).getModel();
				String btm = carquery.get(i).getPlate();
				
				ListData data = new ListData(id, top, mid, btm);
				listData.add(data);
			}
			
			queryData = carquery;
			targetActivity = AddActivity.class;
			title = "Cars";
			
			break;
		case 3:
			//Contacts
			ArrayList<Contact> contactquery = db.selectAllContacts();
			for(int i=0; i<contactquery.size(); i++) {
				int id = contactquery.get(i).getId();
				String top = contactquery.get(i).getName();
				String mid = contactquery.get(i).getNumber();
				String btm = contactquery.get(i).getNotes();
				
				ListData data = new ListData(id, top, mid, btm);
				listData.add(data);
			}
			
			queryData = contactquery;
			targetActivity = AddActivity.class;
			title = "Contacts";
			
			break;
		case 4:
			//CCs
			ArrayList<CreditCard> ccquery = db.selectAllCCs();
			for(int i=0; i<ccquery.size(); i++) {
				int id = ccquery.get(i).getId();
				String top = ccquery.get(i).getBrand();
				String mid = ccquery.get(i).getNumber();
				String btm = ccquery.get(i).getExpiration() + "    " + ccquery.get(i).getCsv();
				
				ListData data = new ListData(id, top, mid, btm);
				listData.add(data);
			}
			
			queryData = ccquery;
			targetActivity = AddActivity.class;
			title = "Credit Cards";
			
			break;
		case 5:
			//Insurance
			ArrayList<Insurance> insquery = db.selectAllInsurance();
			for(int i=0; i<insquery.size(); i++) {
				int id = insquery.get(i).getId();
				String top = insquery.get(i).getInsurancecompany();
				String mid = insquery.get(i).getType();
				String btm = insquery.get(i).getMemid();
				
				ListData data = new ListData(id, top, mid, btm);
				listData.add(data);
			}
			
			queryData = insquery;
			targetActivity = AddActivity.class;
			title = "Insurance";
			
			break;
		case 6:
			//Notes
			ArrayList<Note> notequery = db.selectAllNotes();
			for(int i=0; i<notequery.size(); i++) {
				int id = notequery.get(i).getId();
				String top = notequery.get(i).getTitle();
				String mid = notequery.get(i).getNote();
				String btm = "";
				
				ListData data = new ListData(id, top, mid, btm);
				listData.add(data);
			}
			
			queryData = notequery;
			targetActivity = AddActivity.class;
			title = "Notes";
			
			Log.d("querydata", Integer.toString(queryData.size()));
			
			break;
		case 7:
			//Passwords
			ArrayList<Password> pwquery = db.selectAllPasswords();
			if(pwquery != null) {
			for(int i=0; i<pwquery.size(); i++) {
				int id = pwquery.get(i).getId();
				String top = pwquery.get(i).getWebsite();
				String mid = pwquery.get(i).getUsername();
				String btm = pwquery.get(i).getPassword();
				
				ListData data = new ListData(id, top, mid, btm);
				listData.add(data);
			}
			}
			
			queryData = pwquery;
			targetActivity = AddActivity.class;
			title = "Passwords";
			
			break;
		case 8:
			//Media
			
			
			break;
		case 9:
			//Reminders
			ArrayList<Reminder> remquery = db.selectAllReminders();
			for(int i=0; i<remquery.size(); i++) {
				int id = remquery.get(i).getId();
				String top = remquery.get(i).getNickname();
				String mid = remquery.get(i).getAlertdate() + "  " + remquery.get(i).getAlerttime();
				String btm = remquery.get(i).getLocation();
				
				ListData data = new ListData(id, top, mid, btm);
				listData.add(data);
			}
			
			queryData = remquery;
			targetActivity = AddActivity.class;
			title = "Reminders";
			
			break;
		case 10:
			//SS
			ArrayList<SocialSecurity> ssquery = db.selectAllSS();
			for(int i=0; i<ssquery.size(); i++) {
				int id = ssquery.get(i).getId();
				String top = ssquery.get(i).getName();
				String mid = ssquery.get(i).getSsnumber();
				String btm = ssquery.get(i).getNotes();
				
				ListData data = new ListData(id, top, mid, btm);
				listData.add(data);
			}
			
			queryData = ssquery;
			targetActivity = AddActivity.class;
			title = "Social Security";
			
			break;
			
		
		}
		
		if(queryData.size() == 0) {
			listContainer.setVisibility(View.GONE);
			addFormHolder.setVisibility(View.GONE);
			noListHolder.setVisibility(View.VISIBLE);
			
			
		}
		else {
			listContainer.setVisibility(View.VISIBLE);
			addFormHolder.setVisibility(View.GONE);
			noListHolder.setVisibility(View.GONE);
			
		}
		
		adapter = new ListviewAdapter(this, R.layout.list_item, listData);
		adapter.setCommunicator(this);
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
	}
	
	@SuppressLint("NewApi")
	public void changeToDetails() {
		
		
		getDataFromDB();
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
					goToDetails(position);
				
				
			}

			
		});
		
		
		animAddDown();
		
		addFormHolder.removeAllViews();
		
		Rect r = new Rect();
		View activityRootView = findViewById(R.id.listview_screen_layout);
		activityRootView.getWindowVisibleDisplayFrame(r);
		int heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top);
		
       
        Log.d("size", Integer.toString(heightDiff));
        if (heightDiff > 100) {
            //Make changes for Keyboard not visible
        	imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);	
        	
        } else {
            //Make changes for keyboard visible
        	
        }
				
	}
	
	
	
	public void makeAddLayout() {
		
		mInflater =  (LayoutInflater)getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			
		
		switch(vaultType) {
		case 0:
			AddAccounts addAccount = new AddAccounts(this);
			addFormHolder.addView(addAccount);
			addAccount.setCommunicator(this);
			
			
			break;
		case 1:
			AddBookmarks addBookmarks = new AddBookmarks(this);
			addFormHolder.addView(addBookmarks);
			addBookmarks.setCommunicator(this);
			
			break;
		case 2:
			AddCars addCars = new AddCars(this);
			addFormHolder.addView(addCars);
			addCars.setCommunicator(this);
			
			break;
		case 3:
			AddContacts addContacts = new AddContacts(this);
			addFormHolder.addView(addContacts);
			addContacts.setCommunicator(this);
			
			break;
		case 4:
			AddCreditCard addCC = new AddCreditCard(this);
			addFormHolder.addView(addCC);
			addCC.fixLayout();
			addCC.setCommunicator(this);
			
			break;
		case 5:
			AddInsurance addInsurance = new AddInsurance(this);
			addFormHolder.addView(addInsurance);
			addInsurance.setCommunicator(this);
			break;
		case 6:
			AddNotes addNotes = new AddNotes(this);
			addFormHolder.addView(addNotes);
			addNotes.setCommunicator(this);
			
			break;
		case 7:
			AddPasswords addPasswords = new AddPasswords(this);
			addFormHolder.addView(addPasswords);
			addPasswords.setCommunicator(this);
			
			break;
		case 8:
			
			break;
		case 9:
			AddReminders addReminders = new AddReminders(this);
			addFormHolder.addView(addReminders);
			addReminders.setCommunicator(this);
			break;
		case 10:
			AddSocialSec addSS = new AddSocialSec(this);
			addFormHolder.addView(addSS);
			addSS.setCommunicator(this);
			
			addSS.setFocus();
			
			break;
		}
			
		animAddUp();
		flipMenu();
		backPressed = false;
		
		imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
				
	}
	
	
	
	
	@Override
	public void onResult(ObjectHolder holder) {
		// TODO Auto-generated method stub
		
		getDataFromDB();
		flipMenu();
		addFormHolder.removeAllViews();
		addFormHolder.setVisibility(View.GONE);
		formTopLayout.setVisibility(View.GONE);
		listContainer.setVisibility(View.VISIBLE);
		topLayout.setVisibility(View.VISIBLE);
		
	}
	
	public void animAddUp() {
		
		noListHolder.setVisibility(View.GONE);
		listContainer.setVisibility(View.GONE);
		addFormHolder.setVisibility(View.VISIBLE);
				
	}
	
	public void animAddDown() {
		
		
		noListHolder.setVisibility(View.GONE);
		addFormHolder.setVisibility(View.GONE);
		listContainer.setVisibility(View.VISIBLE);
		
		
	}
	
	@SuppressLint("NewApi")
	public void flipMenu() {
		
		if (mShowingBack) {
	        getFragmentManager().popBackStack();
	        mShowingBack = false;
	        return;
	    }

	    // Flip to the back.

	    mShowingBack = true;

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
	            .replace(R.id.list_top_layout, new CardBackFragment())

	            // Add this transaction to the back stack, allowing users to press Back
	            // to get to the front of the card.
	            .addToBackStack(null)

	            // Commit the transaction.
	            .commit();
		
	}
	
	@SuppressLint("NewApi")
	public void flipDeleteMenu() {
		
		if (mShowingBack) {
	        getFragmentManager().popBackStack();
	        mShowingBack = false;
	        return;
	    }

	    // Flip to the back.

	    mShowingBack = true;

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
	            .replace(R.id.list_top_layout, new CardBackDeleteFragment())

	            // Add this transaction to the back stack, allowing users to press Back
	            // to get to the front of the card.
	            .addToBackStack(null)

	            // Commit the transaction.
	            .commit();
		
	}
	
	
	
	public class CardFrontFragment extends Fragment {
		
        public CardFrontFragment() {
        	
        }
		@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	
        	View view = inflater.inflate(R.layout.listview_toplayout1, container, false);
        	
        	makeFrontLayout(view);
    		
    		
        	return view;
        }
    }

    /**
     * A fragment representing the back of the card.
     */
    public class CardBackFragment extends Fragment {
        public CardBackFragment() {
        
        }
      
		@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	View view = inflater.inflate(R.layout.listview_toplayout2, container, false);
        	formBackButton = (Button)view.findViewById(R.id.list_addform_backbtn);
        	formBackLayout = (LinearLayout)view.findViewById(R.id.list_addform_toplayout);
        	formBackButton.setOnClickListener(new OnClickListener() {

    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				flipMenu();
    				changeToDetails();
    			}
    			
    		});
        	formBackLayout.setOnClickListener(new OnClickListener() {

    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				
    				
    				flipMenu();
    				changeToDetails();
    			}
    			
    		});
            return view;
        }
    }
    
    public class CardBackDeleteFragment extends Fragment {
        public CardBackDeleteFragment() {
        	
        }
    	@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	View view = inflater.inflate(R.layout.listview_toplayout3, container, false);
        	formBackButton = (Button)view.findViewById(R.id.list_deleteform_backbutton);
        	Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
        	formBackButton.setTypeface(tf);
        	formBackButton.setOnClickListener(new OnClickListener() {

    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				flipDeleteMenu();
    				adapter.showNumber();
    			}
    			
    		});
            return view;
        }
    }
    
    
    
    
    
    public void makeFrontLayout(View view) {
    	
    	DisplayMetrics metrics = getResources().getDisplayMetrics();
    	int screenWidth = metrics.widthPixels;
    	
    	btnLayoutL = (LinearLayout)view.findViewById(R.id.list_backbtn_layout);
		btnLayoutM = (LinearLayout)view.findViewById(R.id.list_editbtn_layout);
		btnLayoutR = (LinearLayout)view.findViewById(R.id.list_addbtn_layout);
		addButton = (Button)view.findViewById(R.id.list_add_button);
		editButton = (Button)view.findViewById(R.id.list_edit_button);
		backButton = (Button)view.findViewById(R.id.list_back_buttton);
		btnLayoutL.setLayoutParams(new LinearLayout.LayoutParams(screenWidth/3, LayoutParams.WRAP_CONTENT));
		btnLayoutM.setLayoutParams(new LinearLayout.LayoutParams(screenWidth/3, LayoutParams.WRAP_CONTENT));
		btnLayoutR.setLayoutParams(new LinearLayout.LayoutParams(screenWidth/3, LayoutParams.WRAP_CONTENT));
    	
    	backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				leaveActivity();
			}
		});
    	
    	if(queryData.size() > 0) {
		
			editButton.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					adapter.showDelete();
					isDeleteMode = true;
					flipDeleteMenu();
						
				}
					
				
			});
			
			btnLayoutM.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					adapter.showDelete();
					isDeleteMode = true;
					flipDeleteMenu();
						
				}	
				
				
			});
    	}
    	else {
    		btnLayoutM.setBackgroundColor(Color.parseColor("#cccccc"));
    	}
		
		addButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
								
				makeAddLayout();
			}
		});
		
		btnLayoutL.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				leaveActivity();
			}
		});
		
		
		
		btnLayoutR.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				makeAddLayout();
								
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
	public void disableListClick() {
		// TODO Auto-generated method stub
		//listView.setOnItemClickListener(null);
		listView.setEnabled(false);
		//listView.setActivated(false);
		//listView.setClickable(false);
		
	}


	@Override
	public void enableListClick() {
		// TODO Auto-generated method stub
		listView.setEnabled(true);
		/*
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
					goToDetails(position);
				
				
			}

			
		});
		*/
		
		
	}
	
	
	

}
