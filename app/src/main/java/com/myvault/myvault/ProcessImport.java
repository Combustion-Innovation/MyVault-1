package com.myvault.myvault;

/*
 * A class for processing files shared from
 * an outside app, and saving a database entry
 * if successful.
 * 
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class ProcessImport extends Activity {
	
	
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get intent, action and MIME type
	    Intent intent = getIntent();
	    String action = intent.getAction();
	    String type = intent.getType();

	    if (Intent.ACTION_SEND.equals(action) && type != null) {
	        if(type.startsWith("image/")) {
	        	handleSendImage(intent);
	        	
	        }
	        else if(type.startsWith("video/")) {
	        	handleSendVideo(intent);
	        }
	    } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
	        if (type.startsWith("image/")) {
	            handleSendMultipleImages(intent); // Handle multiple images being sent
	        }
	    } else {
	        // Handle other intents, such as being started from the home screen
	    }
		finish();
		
	}
	
	void handleSendText(Intent intent) {
	    String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
	    if (sharedText != null) {
	        // Update UI to reflect text being shared
	    }
	}

	void handleSendImage(Intent intent) {
	    Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
	    if (imageUri != null) {
	        // Update UI to reflect image being shared
	    	Log.d("Sharing image", "true");
	    	String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            
            int type = 1;
            String filename = timeStamp + ".jpg";
        	File orig = new File(imageUri.getPath());
        	Log.d("imageUri", imageUri.toString());
            File dest = new File(getApplicationContext().getFilesDir() + "/media/" + filename);
            try {
				if(copy(orig, dest)) {
					
					createDBEntry(filename, type);
					
					Toast.makeText(this, "Saved to myVault", Toast.LENGTH_LONG).show();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    }
	}
	
	void handleSendVideo(Intent intent) {
	    Uri videoUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
	 
	    if (videoUri != null) {
	        // Update UI to reflect image being shared
	    	String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filename = "";
            int type = 2;
            filename = timeStamp + ".mp4";
        	File orig = new File(videoUri.toString());
            File dest = new File(getApplicationContext().getFilesDir() + "/media/" + filename);
            try {
				if(copy(orig, dest)) {
					
					createDBEntry(filename, type);
					
					Toast.makeText(this, "Saved to myVault", Toast.LENGTH_LONG).show();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}

	void handleSendMultipleImages(Intent intent) {
	    ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
	    if (imageUris != null) {
	        // Update UI to reflect multiple images being shared
	    }
	}
	
	public boolean copy(File src, File dst) throws IOException {
	    FileInputStream inStream = new FileInputStream(src);
	    FileOutputStream outStream = new FileOutputStream(dst);
	    FileChannel inChannel = inStream.getChannel();
	    FileChannel outChannel = outStream.getChannel();
	    inChannel.transferTo(0, inChannel.size(), outChannel);
	    inStream.close();
	    outStream.close();
	    return true;
	}
	
	public void createDBEntry(String filename, int type) {
		
		DBOpenHelper db = new DBOpenHelper(this);
		db.addMedia(filename, type);
	}
	

}
