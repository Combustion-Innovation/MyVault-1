package com.myvault.myvault;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class ViewVideoActivity extends Activity {

	Context context;
	Media video;
	
	VideoView videoView;
	Button backBtn, deleteBtn, playBtn, shareBtn;
	ImageView playIcon;
	
	String fileName;
	String fileLoc;
	String dir;
	
	int id;
	
	File tempFile;
	
	Uri fileUri;
	
	
	
	boolean isValidPause = true;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(LayoutParams.FLAG_SECURE,
                LayoutParams.FLAG_SECURE);

		setContentView(R.layout.activity_view_video);
		
		context = this;
		dir = getApplicationContext().getFilesDir() + "/media/";
		video = (Media)getIntent().getSerializableExtra("media");
		
		fileName = video.getLocation();
		id = video.getId();
		
		fileUri = Uri.parse(dir + fileName);
		fileLoc = dir + fileName;
		
		videoView = (VideoView)findViewById(R.id.avv_video_view);
		backBtn = (Button)findViewById(R.id.avv_back_button);
		deleteBtn = (Button)findViewById(R.id.avv_delete_button);
		playBtn = (Button)findViewById(R.id.avv_play_button);
		shareBtn = (Button)findViewById(R.id.avv_share_button);
		
		backBtn.bringToFront();
		deleteBtn.bringToFront();
		
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		backBtn.setTypeface(tf);
		deleteBtn.setTypeface(tf);
		
		
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				leave();
			}
		});
		
		deleteBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				deleteClicked();
			}
		});
		
		
		
		
		
		
		videoView.setVideoPath(dir + fileName);
		
		
		playBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				hideButtons();
				MediaController mediaController = new MediaController(context);
				mediaController.setAnchorView(videoView);
				videoView.setMediaController(mediaController);
				
				
				videoView.start();
			}
		});
		
		videoView.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				showButtons();
			}
		});
		
		shareBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				shareVideo();
			}
			
		});
		
		isValidPause = false;
		
		
	}
		
	public void leave() {
		Intent i = new Intent();
		setResult(RESULT_CANCELED, i);
		isValidPause = true;
		finish();
	}
	
	
	
	public void hideButtons() {
		backBtn.setVisibility(View.GONE);
		playBtn.setVisibility(View.GONE);
		deleteBtn.setVisibility(View.GONE);
		shareBtn.setVisibility(View.GONE);
	}
	
	public void showButtons() {
		backBtn.setVisibility(View.VISIBLE);
		playBtn.setVisibility(View.VISIBLE);
		deleteBtn.setVisibility(View.VISIBLE);
		shareBtn.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void onBackPressed() {
		leave();
	}
	
	public void deleteClicked() {
		//make a prompt to delete
		new AlertDialog.Builder(this)
			.setTitle("Delete Video")
			.setMessage("Are you sure you want to delete this video?")
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
	
	public void deleteEntry() {
		
		DBOpenHelper db = new DBOpenHelper(this);
		db.deleteMedia(id);
		db.close();
		isValidPause = true;
		Intent i = new Intent();
		setResult(RESULT_OK, i);
		finish();
	}
	
	public void shareVideo() {
		
		
		
		String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyVault/";
		File dir = new File(file_path);
		dir.mkdirs();
		
		tempFile = new File(dir, "tempFile.mp4");
			
		Intent i = new Intent();
		i.setAction(Intent.ACTION_SEND);
		File origFile = new File(fileLoc);
		
		
		try {
			if(copy(origFile, tempFile)) {
				i.setType("video/*");
				i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(tempFile));
				i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		isValidPause = true;
		
		
		
		startActivityForResult(Intent.createChooser(i, "Share video to..."), 1);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		isValidPause = false;
		tempFile.delete();
	}
	
	
	
	@Override
    protected void onPause() {
		super.onPause();
		Log.d("PAUSED", "PAUSED");
		
		if(!isValidPause) {
			UserData userdata = new UserData(this);
			if(userdata.isLockSet()) {
		    	Intent i = new Intent(this, SleepLockActivity.class);
		    	startActivity(i);
			}
		}
    	
    }
	
	
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		isValidPause = true;
		super.onConfigurationChanged(newConfig);
		
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
	
	
	
	
	
	
}
