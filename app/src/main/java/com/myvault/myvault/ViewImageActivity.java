package com.myvault.myvault;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.myvault.myvault.PhotoViewAttacher.OnViewTapListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ViewImageActivity extends Activity {
	
	Context context;
	
	ImageView image;
	Button backBtn, deleteBtn, shareBtn;
	RelativeLayout topLayout;
	
	Media thisImage;
	String dir;
	String fileUrl;
	int id;
	
	PhotoViewAttacher mAttacher;
	TouchImageView touchImageView;
		
	File imageFile;
	Drawable imageDrawable;
	//dProgressDialog pd;
	
	Uri fileUri;

	boolean buttonsShowing = true;
	boolean isValidPause = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(LayoutParams.FLAG_SECURE,
                LayoutParams.FLAG_SECURE);

		setContentView(R.layout.activity_view_image);
		
		context = this;
		mAttacher = null;
		dir = getApplicationContext().getFilesDir() + "/media/";
		
		thisImage = (Media)getIntent().getSerializableExtra("media");
		
		image = (ImageView)findViewById(R.id.avi_imageview);
		backBtn = (Button)findViewById(R.id.avi_button_done);
		deleteBtn = (Button)findViewById(R.id.avi_button_delete);
		shareBtn = (Button)findViewById(R.id.avi_share_button);
		
		topLayout = (RelativeLayout)findViewById(R.id.activity_view_image_layout);
		
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		backBtn.setTypeface(tf);
		deleteBtn.setTypeface(tf);
		
		
		
		id = thisImage.getId();
		fileUrl = dir + thisImage.getLocation();
		fileUri = Uri.parse(fileUrl);
		
		Bitmap origBitmap = BitmapFactory.decodeFile(fileUrl);
		
		final Bitmap bitmap = Bitmap.createScaledBitmap(origBitmap, 3 * (origBitmap.getWidth()/4), 3 * (origBitmap.getHeight()/4), false);
		
		
		image.setImageBitmap(bitmap);
		
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isValidPause = true;
				Intent i = new Intent();
				setResult(RESULT_CANCELED, i);
				finish();
			}
			
		});
		
		deleteBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				deleteClicked();
			}
			
			
		});
		
		image.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(buttonsShowing) {
					hideButtons();
					buttonsShowing = false;
										
					mAttacher = new PhotoViewAttacher(image);
					mAttacher.setOnViewTapListener(new OnViewTapListener() {
						
						@Override
						public void onViewTap(View view, float x, float y) {
							// TODO Auto-generated method stub
							showButtons();
							buttonsShowing = true;
							mAttacher.cleanup();
							mAttacher = null;
						}
					});
					
					
					
				}
				else {
					showButtons();
					buttonsShowing = true;
				}
			}
			
		});
		
		shareBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				shareImage();
			}
			
		});
		
		isValidPause = false;
			
	}
	
	
	
	@Override
	public void onBackPressed() {
		Intent i = new Intent();
		setResult(RESULT_CANCELED, i);
		isValidPause = true;
		finish();
	}
		
	public void showButtons() {
		backBtn.setVisibility(View.VISIBLE);
		deleteBtn.setVisibility(View.VISIBLE);
		shareBtn.setVisibility(View.VISIBLE);
		backBtn.bringToFront();
		deleteBtn.bringToFront();
	}
	
	public void hideButtons() {
		backBtn.setVisibility(View.GONE);
		deleteBtn.setVisibility(View.GONE);
		shareBtn.setVisibility(View.GONE);
	}
	
	public void deleteClicked() {
		//make a prompt to delete
		new AlertDialog.Builder(this)
			.setTitle("Delete Image")
			.setMessage("Are you sure you want to delete this image?")
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
	
	public void shareImage() {
		
		
		ProgDialog progDialog = new ProgDialog();
		progDialog.execute("");
		
		String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyVault/";
		File dir = new File(file_path);
		dir.mkdirs();
		
		imageFile = new File(dir, "shared_image.jpg");
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(imageFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Bitmap blankBitmap = BitmapFactory.decodeFile(fileUrl);
		blankBitmap.compress(CompressFormat.JPEG, 100, fout);
		try {
			fout.flush();
			fout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Intent i = new Intent();
		i.setAction(Intent.ACTION_SEND);
		
		i.setType("image/*");
		i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
		
		i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imageFile));
				
		isValidPause = true;
		progDialog.cancel(true);
		
		startActivityForResult(Intent.createChooser(i, "Share image to..."), 1);
		
		
		
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		isValidPause = false;
		imageFile.delete();
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
	public void onConfigurationChanged(Configuration newConfig) {
		isValidPause = true;
		super.onConfigurationChanged(newConfig);
		
	}
	
	private class ProgDialog extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			Looper.prepare();
			ProgressDialog pd = new ProgressDialog(context);
			pd.show();
			
			
			
			while(!this.isCancelled()) {
				
			}
			pd.dismiss();
			return null;
		
		}
		
	}

}
