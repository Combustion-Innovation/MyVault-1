package com.myvault.myvault;

import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;



/**
 * This {@code IntentService} does the app's actual work.
 * {@code SampleAlarmReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class CheckExpService extends IntentService {
	
	public CheckExpService() {
        super("CheckExpService");
    }
	
	public static final long SECOND_MILLIS = 1000;
	public static final long MINUTE_MILLIS = 60 * 1000;
	public static final long HOUR_MILLIS = 60 * 60 * 1000;
	public static final long DAY_MILLIS = 24 * 60 * 60 * 1000;
	public static final long WEEK_MILLIS = 7 * 24 * 60 * 60 * 1000;
	public static final long MONTH30_MILLIS = 30 * DAY_MILLIS;
	    
    
    public static final String TAG = "Scheduling Demo";
    // An ID used to post the notification.
    public static final int NOTIFICATION_ID = 1;
    // The string the app searches for in the Google home page content. If the app finds 
    // the string, it indicates the presence of a doodle.  
    public static final String SEARCH_STRING = "doodle";
    // The Google home page URL from which the app fetches content.
    // You can find a list of other Google domains with possible doodles here:
    // http://en.wikipedia.org/wiki/List_of_Google_domains
    public static final String URL = "http://www.google.com";
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    
    private DBOpenHelper db;
    private Calendar cal;
    
    long currentMillis;

    @Override
    protected void onHandleIntent(Intent intent) {
    	
    	cal = Calendar.getInstance();
    	currentMillis = cal.getTimeInMillis();
    	
    	try {
    		db = new DBOpenHelper(getApplicationContext());
    		checkCCs();
        	checkReminders();
    	}
    	catch (SQLiteCantOpenDatabaseException e) {
    		AlarmReceiver.completeWakefulIntent(intent);
    		return;
    		
    		
    	}
    	
    	
    		
    	
        // Release the wake lock provided by the BroadcastReceiver.
        AlarmReceiver.completeWakefulIntent(intent);
        // END_INCLUDE(service_onhandle)
        
    }
    
    public void checkCCs() {
    	   	
    	db = new DBOpenHelper(getApplicationContext());
    	ArrayList<CreditCard> ccs = db.selectAllCCs();
    	for(int i=0; i<ccs.size(); i++) {
    		CreditCard cc = ccs.get(i);
    		long expMillis = cc.getExpMillis();
    		
    		long difference = expMillis - currentMillis;
    		
    		if(difference < MONTH30_MILLIS && difference > (MONTH30_MILLIS - MINUTE_MILLIS)) {
    		
    			//SEND EXPIRATION NOTIFICATION
    			String title = "Credit Card Expiration";
    			String msg = cc.getNickname() + " expires this month!";
    			sendNotification(title, msg);
    		}
    	   		
    	}
    	db.close();
    	db = null;
    	
    	
    }
    
    public void checkReminders() {
    	db = new DBOpenHelper(getApplicationContext());
    	ArrayList<Reminder> reminders = db.selectAllReminders();
    	for(int i=0; i<reminders.size(); i++) {
    		Reminder reminder = reminders.get(i);
    		long expMillis = reminder.getAlertMillis();
    		
    		
    		long difference = expMillis - currentMillis;
    		if(difference <= MINUTE_MILLIS && difference > (0 - MINUTE_MILLIS)) {
    			//SEND REMINDER NOTIFICATION
    			String title = "Reminder:";
    			String msg = reminder.getNickname();
    			sendNotification(title, msg);
    			db.deleteReminder(reminder.getId());
    		}
    		
    		Calendar cal = Calendar.getInstance();
    		cal.setTimeInMillis(expMillis);
    		
    		Log.d("expMillis", Long.toString(expMillis));
    		cal.setTimeInMillis(currentMillis);
    		
    		Log.d("currentMillis", Long.toString(currentMillis));
    		Log.d("difference", Long.toString(difference));
    		Log.d("minute", Long.toString(MINUTE_MILLIS));
    		
    	}
    	db.close();
    	db = null;
    	
    }
    
    // Post a notification indicating whether a doodle was found.
    @SuppressLint("NewApi")
	private void sendNotification(String title, String msg) {
        mNotificationManager = (NotificationManager)
               this.getSystemService(Context.NOTIFICATION_SERVICE);
    
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
            new Intent(this, Splashscreen.class), 0);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        
        Notification.Builder mBuilder =
                new Notification.Builder(this)
        .setSmallIcon(R.drawable.logo_mini)
        .setLargeIcon(icon)
        .setContentTitle(title)
        .setStyle(new Notification.BigTextStyle()
        .bigText(msg))
        .setContentText(msg);
        
        
        mBuilder.setContentIntent(contentIntent);
        mBuilder.setAutoCancel(true);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
 
//
// The methods below this line fetch content from the specified URL and return the
// content as a string.
//
    
    /** Given a URL string, initiate a fetch operation. */
    /*
    private String loadFromNetwork(String urlString) throws IOException {
        InputStream stream = null;
        String str ="";
      
        try {
            stream = downloadUrl(urlString);
            str = readIt(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }      
        }
        return str;
    }
	*/
    /**
     * Given a string representation of a URL, sets up a connection and gets
     * an input stream.
     * @param urlString A string representation of a URL.
     * @return An InputStream retrieved from a successful HttpURLConnection.
     * @throws IOException
     */
    /*
    private InputStream downloadUrl(String urlString) throws IOException {
    	
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Start the query
        conn.connect();
        InputStream stream = conn.getInputStream();
        return stream;
        
    }
    */
	
    /** 
     * Reads an InputStream and converts it to a String.
     * @param stream InputStream containing HTML from www.google.com.
     * @return String version of InputStream.
     * @throws IOException
     */
	/*
    private String readIt(InputStream stream) throws IOException {
        
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        for(String line = reader.readLine(); line != null; line = reader.readLine()) 
            builder.append(line);
        reader.close();
        return builder.toString();
        
    }
    */
    
}