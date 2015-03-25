package com.myvault.myvault;

import java.util.ArrayList;

import com.myvault.myvault.util.SystemUiHider;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class SettingsScreen extends Activity {
		int height;
		int width;
	
	

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
	
	ArrayList<Button> buttons;
	ArrayList<LinearLayout> scrollItems;
	ArrayList<TextView> numbers;
	ArrayList<TextView> numberTexts;
	
	Button buttonApps, buttonTerms, buttonPrivacy, buttonFeedback;
	Button toggle;
	ScrollView termsScroller, privacyScroller;
	SpinningCog spinningCog;
	TextView passcodeText, exitText, topText;
	TextView termsText, privacyText;
	TextView accounts, bookmarks, cars, contacts, ccs, insurance, notes, passwords, media, reminders, ss;
	TextView accountsText, bookmarksText, carsText, contactsText, ccsText, insuranceText, notesText, passwordsText, mediaText, remindersText, ssText;
	LinearLayout accountHolder, bookmarkHolder, carHolder, contactHolder, ccHolder, insuranceHolder, notesHolder, passwordHolder, mediaHolder, reminderHolder, ssHolder;
	RelativeLayout exitHolder;
	LinearLayout passcodeHolder, spacerHolder, buttonsHolder, countersHolder, titleHolder;
	
	UserData userData;
	
	boolean isValidPause = true;
	boolean isButtonPressed = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

		setContentView(R.layout.settings_screen);
		
		
		
		context = this;
		
		userData = new UserData(this);
		
		
		
		buttons = new ArrayList<Button>();
		scrollItems = new ArrayList<LinearLayout>();
		numbers = new ArrayList<TextView>();
		numberTexts = new ArrayList<TextView>();
		
		buttonApps = (Button)findViewById(R.id.settings_apps_button);
		buttonTerms = (Button)findViewById(R.id.settings_terms_button);
		buttonPrivacy = (Button)findViewById(R.id.settings_privacy_button);
		buttonFeedback = (Button)findViewById(R.id.settings_feedback_button);
		spinningCog = (SpinningCog)findViewById(R.id.settings_exit_button);
		
		passcodeText = (TextView)findViewById(R.id.settings_passcode_text);
		exitText = (TextView)findViewById(R.id.settings_exit_text);
		topText = (TextView)findViewById(R.id.settings_top_textview);
		
		
		accounts = (TextView)findViewById(R.id.settings_accounts_number);
		bookmarks = (TextView)findViewById(R.id.settings_bookmarks_number);
		cars = (TextView)findViewById(R.id.settings_cars_number);
		contacts = (TextView)findViewById(R.id.settings_contacts_number);
		ccs = (TextView)findViewById(R.id.settings_credit_number);
		insurance = (TextView)findViewById(R.id.settings_insurance_number);
		notes = (TextView)findViewById(R.id.settings_notes_number);
		passwords = (TextView)findViewById(R.id.settings_password_number);
		media = (TextView)findViewById(R.id.settings_media_number);
		reminders = (TextView)findViewById(R.id.settings_reminders_number);
		ss = (TextView)findViewById(R.id.settings_ss_number);
		
		numbers.add(accounts);
		numbers.add(bookmarks);
		numbers.add(cars);
		numbers.add(contacts);
		numbers.add(ccs);
		numbers.add(insurance);
		numbers.add(notes);
		numbers.add(passwords);
		numbers.add(media);
		numbers.add(reminders);
		numbers.add(ss);
		
		accountsText = (TextView)findViewById(R.id.settings_accounts_text);
		bookmarksText = (TextView)findViewById(R.id.settings_bookmarks_text);
		carsText = (TextView)findViewById(R.id.settings_cars_text);
		contactsText = (TextView)findViewById(R.id.settings_contacts_text);
		ccsText = (TextView)findViewById(R.id.settings_credit_text);
		insuranceText = (TextView)findViewById(R.id.settings_insurance_text);
		notesText = (TextView)findViewById(R.id.settings_notes_text);
		passwordsText = (TextView)findViewById(R.id.settings_password_text);
		mediaText = (TextView)findViewById(R.id.settings_media_text);
		remindersText = (TextView)findViewById(R.id.settings_reminders_text);
		ssText = (TextView)findViewById(R.id.settings_ss_text);
		
		numberTexts.add(accountsText);
		numberTexts.add(bookmarksText);
		numberTexts.add(carsText);
		numberTexts.add(contactsText);
		numberTexts.add(ccsText);
		numberTexts.add(insuranceText);
		numberTexts.add(notesText);
		numberTexts.add(passwordsText);
		numberTexts.add(mediaText);
		numberTexts.add(remindersText);
		numberTexts.add(ssText);
		
		
		accountHolder = (LinearLayout)findViewById(R.id.settings_accounts_holder);
		bookmarkHolder = (LinearLayout)findViewById(R.id.settings_bookmarks_holder);
		carHolder = (LinearLayout)findViewById(R.id.settings_cars_holder);
		contactHolder = (LinearLayout)findViewById(R.id.settings_contacts_holder);
		ccHolder = (LinearLayout)findViewById(R.id.settings_credit_holder);
		insuranceHolder = (LinearLayout)findViewById(R.id.settings_insurance_holder);
		notesHolder = (LinearLayout)findViewById(R.id.settings_notes_holder);
		passwordHolder = (LinearLayout)findViewById(R.id.settings_password_holder);
		mediaHolder = (LinearLayout)findViewById(R.id.settings_media_holder);
		reminderHolder = (LinearLayout)findViewById(R.id.settings_reminders_holder);
		ssHolder = (LinearLayout)findViewById(R.id.settings_ss_holder);
		
		scrollItems.add(accountHolder);
		scrollItems.add(bookmarkHolder);
		scrollItems.add(carHolder);
		scrollItems.add(contactHolder);
		scrollItems.add(ccHolder);
		scrollItems.add(insuranceHolder);
		scrollItems.add(notesHolder);
		scrollItems.add(passwordHolder);
		scrollItems.add(mediaHolder);
		scrollItems.add(reminderHolder);
		scrollItems.add(ssHolder);
		
		
		toggle = (Button)findViewById(R.id.settings_passcode_toggle);
		privacyText = (TextView)findViewById(R.id.settings_privacy_text);
		privacyScroller = (ScrollView)findViewById(R.id.settings_privacy_scroller);
		termsText = (TextView)findViewById(R.id.settings_terms_text);
		termsScroller = (ScrollView)findViewById(R.id.settings_terms_scroller);
		topText = (TextView)findViewById(R.id.settings_top_textview);
		
		exitHolder = (RelativeLayout)findViewById(R.id.settings_exit_layout);
		passcodeHolder = (LinearLayout)findViewById(R.id.settings_passcode_layout);
		spacerHolder = (LinearLayout)findViewById(R.id.settings_spacer_layout);
		buttonsHolder = (LinearLayout)findViewById(R.id.settings_buttons_layout);
		countersHolder = (LinearLayout)findViewById(R.id.settings_listview_layout);
		titleHolder = (LinearLayout)findViewById(R.id.settings_top_layout);
		
		buttons.add(buttonApps);
		buttons.add(buttonTerms);
		buttons.add(buttonPrivacy);
		buttons.add(buttonFeedback);
        
		animatePageOpen();
	   
	 

		setLayout();
		getData();
		
		
		
		toggle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isValidPause = true;
				if(toggle.getText().equals("ON")) {
					Intent i = new Intent(context, UnlockPinActivity.class);
					startActivityForResult(i, 2);
				}
				else {
					Intent i = new Intent(context, SetPinActivity.class);
					startActivityForResult(i, 1);
				}
				
			}
		});
		
		spinningCog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				leaveSettings();
				
			}
			
		});
		
		exitHolder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				leaveSettings();
				
			}
			
		});
		
		buttonApps.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goToApps();
			}
			
		});
		
		buttonTerms.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				privacyScroller.setVisibility(View.GONE);
				termsScroller.setVisibility(View.VISIBLE);
				
			}
			
		});
		
		buttonPrivacy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				privacyScroller.setVisibility(View.VISIBLE);
				termsScroller.setVisibility(View.GONE);
			}
			
		});
		
		buttonFeedback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goToEmail();
			}
			
		});
		
		
		isValidPause = false;
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
	}
	
	public void setLayout() {
		
		DisplayMetrics metrics = this.getResources().getDisplayMetrics();
		Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		Typeface tf2 = Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeue-UltraLight-2.ttf");
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        int dpi = (int)metrics.density;
        
        toggle.setTypeface(tf);
        exitText.setTypeface(tf);
        passcodeText.setTypeface(tf);
        termsText.setTypeface(tf);
        privacyText.setTypeface(tf);
        topText.setTypeface(tf);
        
        termsText.setText(Html.fromHtml(termsString));
        privacyText.setText(Html.fromHtml(privacyString));
		
        for(int i=0; i<buttons.size(); i++) {
        	buttons.get(i).setLayoutParams(new LinearLayout.LayoutParams(width/4 - 5*dpi, width/4 - 5*dpi));
        	buttons.get(i).setTypeface(tf);
        }
        
        for(int i=0; i<scrollItems.size(); i++) {
        	scrollItems.get(i).setLayoutParams(new LinearLayout.LayoutParams(width/3, LayoutParams.MATCH_PARENT));
        	numbers.get(i).setTypeface(tf2);
        	numberTexts.get(i).setTypeface(tf);
        	
        }
        
        if(userData.isLockSet()) {
        	toggle.setText("ON");
        	toggle.setTextColor(Color.parseColor("#d25e0a"));
        }
        else {
        	toggle.setText("OFF");
        	toggle.setTextColor(Color.WHITE);
        }
        
        termsScroller.setVisibility(View.GONE);
        privacyScroller.setVisibility(View.GONE);
        
        exitHolder.getLayoutParams().height = (height - 35 * dpi)/4; 
        
	}
	
	public void leaveSettings() {
		if(!isButtonPressed) {
			isButtonPressed = true;
			isValidPause = true;
			animatePageClose();
		}
	}
	
	@Override
	public void onBackPressed() {
		leaveSettings();
	}
	
	public void getData() {
		
		DBOpenHelper db = new DBOpenHelper(this);
		ArrayList<?> items = new ArrayList();
		items = db.selectAllAccounts();
		accounts.setText(Integer.toString(items.size()));
		items = db.selectAllBookmarks();
		bookmarks.setText(Integer.toString(items.size()));
		items = db.selectAllCars();
		cars.setText(Integer.toString(items.size()));
		items = db.selectAllCCs();
		ccs.setText(Integer.toString(items.size()));
		items = db.selectAllContacts();
		contacts.setText(Integer.toString(items.size()));
		items = db.selectAllInsurance();
		insurance.setText(Integer.toString(items.size()));
		items = db.selectAllMedia();
		media.setText(Integer.toString(items.size()));
		items = db.selectAllNotes();
		notes.setText(Integer.toString(items.size()));
		items = db.selectAllPasswords();
		passwords.setText(Integer.toString(items.size()));
		items = db.selectAllReminders();
		reminders.setText(Integer.toString(items.size()));
		items = db.selectAllSS();
		ss.setText(Integer.toString(items.size()));
		
		
	}
	
	public void goToEmail() {
		
		isValidPause = true;
		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","concierge@combustioninnovation.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Dear myVault Team");
                    startActivityForResult(Intent.createChooser(emailIntent, "Email myVault"), 3);
		
	}
	
	public void goToApps() {
		isValidPause = true;
		Uri uri = Uri.parse("https://play.google.com/store/apps/developer?id=Combustion");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivityForResult(intent, 4);
	}
	
	public void animatePageOpen() {
		exitHolder.setEnabled(false);
		isButtonPressed = true;
		
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		final int screenWidth = metrics.widthPixels;
		titleHolder.setAlpha(0);
		countersHolder.setAlpha(0);
		buttonsHolder.setAlpha(0);
		spacerHolder.setAlpha(0);
		passcodeHolder.setAlpha(0);
		
		
		
		final AnimatorSet layoutSet = new AnimatorSet();
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(titleHolder, "alpha", 0, 1);		
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(countersHolder, "alpha", 0, 1);
		ObjectAnimator anim3 = ObjectAnimator.ofFloat(buttonsHolder, "alpha", 0, 1);
		ObjectAnimator anim4 = ObjectAnimator.ofFloat(spacerHolder, "alpha", 0, 1);
		ObjectAnimator anim5 = ObjectAnimator.ofFloat(passcodeHolder, "alpha", 0, 1);
		
		anim1.setDuration(300);
		anim2.setDuration(300);
		anim3.setDuration(300);
		anim4.setDuration(300);
		anim5.setDuration(300);
		
		
		layoutSet.playTogether(anim1, anim2, anim3, anim4, anim5);
		
		exitText.setText("Settings");
		
		
		new Handler().post(new Runnable() {
			
			@Override
			public void run() {
				
				
				ValueAnimator exitAnim = ValueAnimator.ofInt(screenWidth/3, screenWidth);
				exitAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
					
					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						// TODO Auto-generated method stub
						int val = (Integer) animation.getAnimatedValue();
						LayoutParams params = exitHolder.getLayoutParams();
						params.width = val;
						exitHolder.setLayoutParams(params);
										
					}
				});
				exitAnim.setDuration(300);
				exitAnim.start();
							
				exitAnim.addListener(new AnimatorListenerAdapter() {
					
					@Override
					public void onAnimationEnd(Animator animation) {
						
						layoutSet.start();
						exitText.setText("Exit");
						spinningCog.startSpinning(context);
						isButtonPressed = false;
						exitHolder.setEnabled(true);
					}
				});
					
			}
		});
		
		
		
		
		
		ObjectAnimator exitAnim = ObjectAnimator.ofFloat(exitHolder, "width", screenWidth/3, screenWidth);
		exitAnim.setDuration(500);
		exitAnim.start();
		
		
	}
	
	public void animatePageClose() {
		
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		final int screenWidth = metrics.widthPixels;
		
		final AnimatorSet layoutSet = new AnimatorSet();
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(titleHolder, "alpha", 1, 0);		
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(countersHolder, "alpha", 1, 0);
		ObjectAnimator anim3 = ObjectAnimator.ofFloat(buttonsHolder, "alpha", 1, 0);
		ObjectAnimator anim4 = ObjectAnimator.ofFloat(spacerHolder, "alpha", 1, 0);
		ObjectAnimator anim5 = ObjectAnimator.ofFloat(passcodeHolder, "alpha", 1, 0);
		
		anim1.setDuration(300);
		anim2.setDuration(300);
		anim3.setDuration(300);
		anim4.setDuration(300);
		anim5.setDuration(300);
		
		spinningCog.accelerateSpinning(context);
		layoutSet.playTogether(anim1, anim2, anim3, anim4, anim5);
		
		
		new Handler().post(new Runnable() {
			
			@Override
			public void run() {
				
				
				
				layoutSet.start();
				ValueAnimator exitAnim = ValueAnimator.ofInt(screenWidth, screenWidth/3);
				exitAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
					
					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						// TODO Auto-generated method stub
						int val = (Integer) animation.getAnimatedValue();
						LayoutParams params = exitHolder.getLayoutParams();
						params.width = val;
						exitHolder.setLayoutParams(params);
						
						
						
						
					}
				});
				exitAnim.setDuration(500);
				exitAnim.start();
				exitAnim.addListener(new AnimatorListenerAdapter() {
					
					@Override
					public void onAnimationEnd(Animator animation) {
						Intent i = new Intent();
						setResult(RESULT_OK, i);
						finish();
						isButtonPressed = false;
						overridePendingTransition(0, 0);
						
					}
				});
				
				
			}
		});
		
		
		
	}
	
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//Lock Set Return
		if(requestCode == 1) {
			if(resultCode == RESULT_OK) {
				toggle.setText("ON");
				toggle.setTextColor(Color.parseColor("#d25e0a"));
			}
			else if(resultCode == RESULT_CANCELED) {
				toggle.setText("OFF");
				toggle.setTextColor(Color.WHITE);
			}
		}
		if(requestCode == 2) {
			//Unlock pin return
			if(resultCode == RESULT_OK) {
				toggle.setText("OFF");
				toggle.setTextColor(Color.WHITE);
			}
			else if(resultCode == RESULT_CANCELED) {
				toggle.setText("ON");
				toggle.setTextColor(Color.parseColor("#d25e0a"));
			}
			boolean fullExit = data.getBooleanExtra("fullExit", false);
			if(fullExit) {
				Intent i = new Intent();
				i.putExtra("fullExit", true);
				setResult(RESULT_CANCELED, i);
				finish();
			}
		}
		isValidPause = false;
	}
	
	@Override
    protected void onPause() {
		super.onPause();
		spinningCog.stopSpinning();
		if(!isValidPause) {
			UserData userdata = new UserData(this);
			if(userdata.isLockSet()) {
		    	Intent i = new Intent(this, SleepLockActivity.class);
		    	startActivity(i);
			}
		}
    	
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		spinningCog.startSpinning(this);
	}
	
	final String termsString = "<h2>COMBUSTION INNOVATION GROUP</h2>" +
			"<h2>PRIVACY POLICY/TERMS</h2>" +
			"<h3>Welcome to Combustion Innovation Group, Inc. (the \"App\").</h3>" +
			"<p >We understand that privacy online is important to users of our App, especially when conducting business.  This statement governs our privacy policies with respect to those users of the App (\"Visitors\") who visit without transacting business and Visitors who register to transact business on the App and make use of the various services offered by Combustion Innovation Group, Inc. (collectively, \"Services\") (\"Authorized Customers\").</p>" +
			"<h3>\"Personally Identifiable Information\"</h3>" +
			"<p >Refers to any information that identifies or can be used to identify, contact, or locate the person to whom such information pertains, including, but not limited to, name, address, phone number, fax number, email address, financial profiles, social security number, and credit card information. Personally Identifiable Information does not include information that is collected anonymously (that is, without identification of the individual user) or demographic information not connected to an identified individual.</p>" +
			"<h3>What Personally Identifiable Information is collected?</h3>" +
			"<p >For the Vault, we collect no user data. All user data is stored locally and never is broadcasted over the internet.</p>" +
			"<h3>How does the App use Personally Identifiable Information?</h3>" +
			"<p > My Vault collects no user data.</p>" +
			"<h3>With whom may the information may be shared?</h3>" +
			"<p >No user data will be collected or used from My Vault</p>" +
			"<h3>How is Data stored on My Vault?</h3>" +
			"<p>Data is stored in a secure Local Database. The information stored in My Vault is private and the user has the ability to password protect My Vault. If My Vault is password protected, the user will be prompted to enter their password every time they leave the app/minimize the app and return to My Vault. We are not responsible for data stolen if My Vault is not properly password protected.</p>" +
			"<h3>What happens if the Privacy Policy Changes?</h3>" +
			"<p >We will let our Visitors and Authorized Customers know about changes to our privacy policy by posting such changes on the App. However, if we are changing our privacy policy in a manner that might cause disclosure of Personally Identifiable Information that a Visitor or Authorized Customer has previously requested not be disclosed, we will contact such Visitor or Authorized Customer to allow such Visitor or Authorized Customer to prevent such disclosure.</p>";



	final String privacyString = "<h2>We value your privacy.</h2>" + 
			"<p>No Information is shared with other companies.</p>" +
			"<p>You are the only person who has access to your information on My Vault unless you share it with others.</p>" +
			"<p>My Vault is password protected.</p>" + 
			"<p>Inforamtion like pictures are base64 encoded.</p>" +
			"<p>If you forget your password there is no way for us to get it back for you. If you enter the wrong password five times in a row you will be locked out of My Vault for 5 minutes.</p>" +
			"<p>If your password is on, you will be prompted to enter your password every time is opened and everytime you minimize your app.</p>";



	
	

}
