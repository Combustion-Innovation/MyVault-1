package com.myvault.myvault;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ViewMedia extends Activity {
	
	LinearLayout btnLayoutL, btnLayoutM, btnLayoutR, topLayout, noListHolder, dialogLayout;
	Button addButton, editButton, backButton, formBackButton, dialogCancelBtn, dialogGalleryBtn, dialogCameraBtn, dialogVideoBtn;
	TextView topText, noListText;
	GridView gridView;
	ImageView dimBackground;
	
	MediaAdapter adapter;
	Uri fileUri;
	String lastFileName;
	Handler threadHandler = null;
	Context context;
	Handler uiHandler = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {
	    	
	    	setGridView();
	    	pd.dismiss();
	      adapter.notifyDataSetChanged();         
	    }
	  };
	
	ArrayList<Media> mediaList;
	
	ProgressDialog pd;
	
	int dialogHeight;
	
	String dirString;
	File dir;
	boolean isEditMode = false;
	
	boolean isValidPause = true;
	
	boolean dialogOpen = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		ActionBar bar = getActionBar();
		
		bar.hide();
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
		
		
		setContentView(R.layout.view_media);
		context = this;
		
		
		pd = new ProgressDialog(this, R.style.MyTheme);
		//pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
		
		pd.show();
		
		dirString = Environment.getExternalStorageDirectory() + "/myvault/media/";
		dir = new File(dirString);
		
		btnLayoutL = (LinearLayout)findViewById(R.id.vm_backbtn_layout);
		btnLayoutM = (LinearLayout)findViewById(R.id.vm_editbtn_layout);
		btnLayoutR = (LinearLayout)findViewById(R.id.vm_addbtn_layout);
		topLayout = (LinearLayout)findViewById(R.id.vm_buttons_layout);
		
		dialogLayout = (LinearLayout)findViewById(R.id.vm_dialog_layout);
		
		dialogCancelBtn = (Button)findViewById(R.id.vm_cancel_button);
		dialogGalleryBtn = (Button)findViewById(R.id.vm_gallery_button);
		dialogCameraBtn = (Button)findViewById(R.id.vm_camera_button);
		dialogVideoBtn = (Button)findViewById(R.id.vm_video_button);
		
		addButton = (Button)findViewById(R.id.vm_add_button);
		editButton = (Button)findViewById(R.id.vm_edit_button);
		backButton = (Button)findViewById(R.id.vm_back_buttton);
		
		gridView = (GridView)findViewById(R.id.vm_gridview);
		
		noListHolder = (LinearLayout)findViewById(R.id.vm_nolist_holder);
		
		topText = (TextView)findViewById(R.id.vm_title_textview);
		noListText = (TextView)findViewById(R.id.vm_nolist_textview);
		
		dimBackground = (ImageView)findViewById(R.id.vm_dim_background);
		
		setLayout();
		
		
		isValidPause = false;
		
		makeThread();
		
		
		
	}
	
	public void makeThread() {
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Looper.prepare();
				
				setGridView();
								
				Looper.loop();
				
				
			}
		};
		t.start();
		
		
		
	}
	
	@Override
	public void onStart() {
		super.onStart();

	}
	
	public void makeLayout() {
		setContentView(R.layout.view_media);
		
		Log.d("pastsetcontent", "");
		
		dirString = Environment.getExternalStorageDirectory() + "/myvault/media/";
		dir = new File(dirString);
		
		btnLayoutL = (LinearLayout)findViewById(R.id.vm_backbtn_layout);
		btnLayoutM = (LinearLayout)findViewById(R.id.vm_editbtn_layout);
		btnLayoutR = (LinearLayout)findViewById(R.id.vm_addbtn_layout);
		topLayout = (LinearLayout)findViewById(R.id.vm_buttons_layout);
		
		dialogLayout = (LinearLayout)findViewById(R.id.vm_dialog_layout);
		
		dialogCancelBtn = (Button)findViewById(R.id.vm_cancel_button);
		dialogGalleryBtn = (Button)findViewById(R.id.vm_gallery_button);
		dialogCameraBtn = (Button)findViewById(R.id.vm_camera_button);
		dialogVideoBtn = (Button)findViewById(R.id.vm_video_button);
		
		addButton = (Button)findViewById(R.id.vm_add_button);
		editButton = (Button)findViewById(R.id.vm_edit_button);
		backButton = (Button)findViewById(R.id.vm_back_buttton);
		
		gridView = (GridView)findViewById(R.id.vm_gridview);
		
		noListHolder = (LinearLayout)findViewById(R.id.vm_nolist_holder);
		
		topText = (TextView)findViewById(R.id.vm_title_textview);
		noListText = (TextView)findViewById(R.id.vm_nolist_textview);
	}
	
	public void setGridView() {
		
		DBOpenHelper db = new DBOpenHelper(this);
		mediaList = db.selectAllMedia();
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(mediaList.size() < 1) {
					//show nolist screen
					noListHolder.setVisibility(View.VISIBLE);
					
				}
				else {
					noListHolder.setVisibility(View.GONE);
					gridView.setVisibility(View.VISIBLE);
					
				}
			}
		});
		
		
		adapter = new MediaAdapter(this, R.layout.media_tile, mediaList);
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				gridView.setAdapter(adapter);
			}
			
		});
		
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				viewItem(position);
				
			}
			
		});
		gridView.setOnItemLongClickListener(new OnItemLongClickListener() {
			
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				makeGalleryDialog(position);
				gridView.setEnabled(false);
				
				return false;
			}
		});
		
		adapter.notifyDataSetChanged();
		pd.dismiss();
		
	}
	
	public void setLayout() {
		
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int screenWidth = metrics.widthPixels;
		
		btnLayoutL.setLayoutParams(new LinearLayout.LayoutParams(screenWidth/3, LayoutParams.WRAP_CONTENT));
		btnLayoutM.setLayoutParams(new LinearLayout.LayoutParams(screenWidth/3, LayoutParams.WRAP_CONTENT));
		btnLayoutR.setLayoutParams(new LinearLayout.LayoutParams(screenWidth/3, LayoutParams.WRAP_CONTENT));
		
		
		
		dialogLayout.setVisibility(View.GONE);
		dimBackground.setVisibility(View.GONE);
		Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		Typeface tf2 = Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeue-UltraLight-2.ttf");
		topText.setTypeface(tf);
		noListText.setTypeface(tf);
		dialogVideoBtn.setTypeface(tf2);
		dialogCancelBtn.setTypeface(tf2);
		dialogCameraBtn.setTypeface(tf2);
		dialogGalleryBtn.setTypeface(tf2);
		
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				leaveActivity();
			}
		});
		
		editButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		addButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editClicked();
				
				//makeAddLayout();
			}
		});
		
		btnLayoutL.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				leaveActivity();
			}
		});
		
		btnLayoutM.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		btnLayoutR.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editClicked();
				
				//makeAddLayout();
			}
		});
		
		dialogCancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				removeDialog();
			}
			
		});
		
		dialogGalleryBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				galleryClicked();
			}
		});
		
		dialogCameraBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cameraClicked();
			}
			
		});
		
		dialogVideoBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				videoClicked();
			}
			
			
		});
		
	}
	
	public void makeGalleryDialog(int position) {
		gridView.setEnabled(true);
		final int pos = position;
		
		new AlertDialog.Builder(this)
			.setTitle("Send To Gallery")
			.setMessage("Are you sure you want to send this back to the gallery?\nItem will no longer be secured")
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					restoreToGallery(pos);
					
					
				}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					gridView.setEnabled(true);
				}
			})
			.show();
		
	}
	
	public void restoreToGallery(int position) {
		
		
		
		File orig = new File(getFilesDir() + "/media/" + mediaList.get(position).getLocation());
		File destPath = new File(Environment.getExternalStorageDirectory() + "/MyVault/");
		if(!destPath.exists()) {
			destPath.mkdirs();
		}
		File dest = new File(destPath, mediaList.get(position).getLocation());
		try {
			if(copy(orig, dest)) {
				Toast.makeText(this, "Copied to public directory", Toast.LENGTH_LONG).show();
				gridView.setEnabled(true);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Tell the media scanner about the new file so that it is
	    // immediately available to the user.
	    MediaScannerConnection.scanFile(this,
	            new String[] { dest.toString() }, null,
	            new MediaScannerConnection.OnScanCompletedListener() {
	        public void onScanCompleted(String path, Uri uri) {
	            Log.i("ExternalStorage", "Scanned " + path + ":");
	            Log.i("ExternalStorage", "-> uri=" + uri);
	        }
	    });
		
	}
	
	public void viewItem(int position) {

		Media current = mediaList.get(position);
		isValidPause = true;
		if(current.getType() == 2) {
			Intent i = new Intent(this, ViewVideoActivity.class);
			i.putExtra("media", current);
			startActivityForResult(i, 5);
			
		}
		else {
			Intent i = new Intent(this, ViewImageActivity.class);
			i.putExtra("media", current);
			startActivityForResult(i, 4);
		}
	}
	
	public void addClicked() {
		
	}
	
	public void editClicked() {
		
		if(!isEditMode) {
			isEditMode = true;
			btnLayoutR.setBackgroundColor(Color.parseColor("#cccccc"));
			
			makeDialog();
		}
		
	}
	
	public void galleryClicked() {
		
		isValidPause = true;
		Intent mediaChooser = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		//comma-separated MIME types
		mediaChooser.setType("image/*, video/*");
		mediaChooser.putExtra(mediaChooser.EXTRA_MIME_TYPES,"*/");
		mediaChooser.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		
		startActivityForResult(mediaChooser, 1);
		
	}
	
	public void cameraClicked() {
		
		isValidPause = true;
		Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		
		File image = null;
		File directory = new File(getApplicationContext().getFilesDir() + "/media/");
		if(!directory.exists()) {
			directory.mkdirs();
		}
		
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		lastFileName = timeStamp;
		
		
		image = new File(Environment.getExternalStorageDirectory() + "/myvault/media/" + lastFileName + ".jpg");
		lastFileName += ".jpg";
		fileUri = Uri.fromFile(image);
		Log.d("fileURI",fileUri.toString());
		
		
		
		i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));		
		//i.putExtra(MediaStore.EXTRA_OUTPUT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, 2);
	}
	
	public void videoClicked() {
		
		isValidPause = true;
		Intent i = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		File video = null;
		File directory = new File(Environment.getExternalStorageDirectory() + "/myvault/media/");
		if(!directory.exists()) {
			directory.mkdirs();
		}		
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		
		lastFileName = timeStamp + ".mp4";
		Log.d("filename", lastFileName);
		
		video = new File(directory + lastFileName);
		fileUri = Uri.fromFile(video);
		i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);	
		startActivityForResult(i, 3);
	}
	
	public void makeDialog() {
		
		dialogLayout.setVisibility(View.VISIBLE);
		dimBackground.setVisibility(View.VISIBLE);
		dimBackground.bringToFront();
		dialogLayout.bringToFront();
		Log.d("dialogHeight", Integer.toString(dialogLayout.getHeight()));
		animDialogUp();
		//gridView.setAlpha(0.3f);
		
		gridView.setEnabled(false);
		
		//topLayout.setAlpha(0.3f);
		dialogOpen = true;
		
	}
	
	public void animDialogUp() {
		
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int screenHeight = metrics.heightPixels;
		int dpi = (int)metrics.density;
		int dialogHeight = 175 * dpi;
		Log.d("dialogHeight", Integer.toString(dialogHeight));
		
		
		ValueAnimator upAnim = ValueAnimator.ofInt(screenHeight, screenHeight - dialogHeight);
		upAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				// TODO Auto-generated method stub
				int val = (Integer) animation.getAnimatedValue();
				/*
				LayoutParams params = dialogLayout.getLayoutParams();
				params.height = val;
				dialogLayout.setLayoutParams(params);
				*/
				dialogLayout.setY(val);
			}
			
		});
		upAnim.setDuration(300);
		upAnim.start();
		
	}
	
public void animDialogDown() {
		
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int screenHeight = metrics.heightPixels;
		int dpi = (int)metrics.density;
		int dialogHeight = 175 * dpi;
		Log.d("dialogHeight", Integer.toString(dialogHeight));
		
		
		ValueAnimator downAnim = ValueAnimator.ofInt(screenHeight - dialogHeight, screenHeight);
		downAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				// TODO Auto-generated method stub
				int val = (Integer) animation.getAnimatedValue();
				/*
				LayoutParams params = dialogLayout.getLayoutParams();
				params.height = val;
				dialogLayout.setLayoutParams(params);
				*/
				dialogLayout.setY(val);
			}
			
		});
		downAnim.setDuration(300);
		downAnim.start();
		downAnim.addListener(new AnimatorListenerAdapter() {
			
			@Override
			public void onAnimationEnd(Animator animation) {
				
				dialogLayout.setVisibility(View.GONE);
				dimBackground.setVisibility(View.GONE);
				
			}
			
			
		});
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		String[] validImageTypes = {"jpg", "JPG", "jpeg", "JPEG", "bmp", "BMP", "gif", "GIF", "png", "PNG"};
		String[] validVideoTypes = {"mp4", "mkv", "avi", "m4a", "3gp", "aac", "MP4", "MKV", "AVI", "M4A", "3GP", "AAC"};
		
		if(requestCode == 1) {
			//Gallery return
			if(resultCode == RESULT_OK) {
				
				ArrayList<String> allowedVideoTypes = new ArrayList<String>();
				for(int i=0; i<validVideoTypes.length; i++) {
					allowedVideoTypes.add(validVideoTypes[i]);
				}
				ArrayList<String> allowedImageTypes = new ArrayList<String>();
				for(int i=0; i<validImageTypes.length; i++) {
					allowedImageTypes.add(validImageTypes[i]);
				}
				
				String destpath = getFilesDir() + "/media/";
	            File directory = new File(destpath);
	            if(!directory.exists()) {
	            	directory.mkdirs();
	            }
				
				
				Uri selectedImage = data.getData();
				
				Log.d("selectedImage", selectedImage.toString());
				String picturePath = getPath(selectedImage);
			    
	            Log.d("picturepath", picturePath);
	            
	            int dotpt = picturePath.indexOf(".");
	            String extension = picturePath.substring(dotpt + 1);
	            while(extension.contains(".")) {
	            	dotpt = extension.indexOf(".");
	            	extension = extension.substring(dotpt + 1);
	            }
	            Log.d("extension", extension);
	            int type = 0;
	            
	            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	            String filename = "";
	            
	            if(allowedVideoTypes.contains(extension)) {
	            	//process video
	            	type = 2;
	            	filename = timeStamp + ".mp4";
	            	File orig = new File(picturePath);
	 	            File dest = new File(destpath + filename);
	 	            try {
	 					if(copy(orig, dest)) {
	 						Toast.makeText(this, "Saved to myVault", Toast.LENGTH_LONG).show();
	 						DBOpenHelper db = new DBOpenHelper(this);
	 			            db.addMedia(filename, type);
	 			            db.close();
	 					}
	 				} catch (IOException e) {
	 					// TODO Auto-generated catch block
	 					e.printStackTrace();
	 				}
	 	                        	
	            }
	            else if(allowedImageTypes.contains(extension)) {
	            	//process image
	            	type = 1;
	            	if(extension.equals("jpg") || extension.equals("JPG") || extension.equals("bmp") || extension.equals("BMP") || extension.equals("jpeg") || extension.equals("JPEG")) {
	            		filename = processImage(picturePath);
	            	}
	            	else {
	            		
	                    filename = timeStamp + "." + extension;
	                    File orig = new File(picturePath);
		 	            File dest = new File(destpath + filename);
		 	            try {
		 					copy(orig, dest);
		 					Bitmap bitmap = BitmapFactory.decodeFile(dest.getPath());
		 					
		 						
		 				} catch (IOException e) {
		 					// TODO Auto-generated catch block
		 					e.printStackTrace();
		 				}
	            	}
	            	Log.d("img filename", destpath + filename);
	            	DBOpenHelper db = new DBOpenHelper(this);
		            db.addMedia(filename, type);
		            db.close();
		                       	
	            }
	            else {
	            	//error
	            	Toast.makeText(context, "Invalid File Type", Toast.LENGTH_LONG).show();
	            }
	            	       
	            removeDialog();
	            
	            setGridView();
	                       
			}
			
			
		}
		else if(requestCode == 2) {
			//Camera return
			if(resultCode == RESULT_OK) {
				               
                Uri uri = fileUri;
				                              
                String picturePath = uri.getPath();
                Log.d("picturePath", picturePath);
                
                String processedImage = processImage(picturePath);
                Log.d("processedImage", processedImage);
                
                File file1 = new File(getFilesDir() + "/media/" + processedImage);
                File file2 = new File(getFilesDir() + "/media/" + lastFileName);
                try {
					if(copy(file1, file2)) {
						file1.delete();
						
						DBOpenHelper db = new DBOpenHelper(this);
						
						db.addMedia(lastFileName, 1);
						
						removeDialog();
						
						
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
                setGridView();
			}
			
			
		}
		else if(requestCode == 3) {
			//Video return
			if(resultCode == RESULT_OK) {
				Uri videoUri = fileUri;
				String picturePath = videoUri.getPath();
                File file1 = new File(picturePath);
                File file2 = new File(getFilesDir() + "/media/" + lastFileName);
                try {
					if(copy(file1, file2)) {
						file1.delete();
						
						DBOpenHelper db = new DBOpenHelper(this);
						
						db.addMedia(lastFileName, 2);
						
						removeDialog();
						
						setGridView();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
				
			}
		}
		else if(requestCode == 4) {
			//Image View Return
			if(resultCode == RESULT_OK) {
				setGridView();
			}
		}
		else if(requestCode == 5) {
			//Video View Return
			if(resultCode == RESULT_OK) {
				setGridView();
			}
		}
		isValidPause = false;
	}
	
	
	@SuppressLint("NewApi")
	private String getPath(Uri uri) {
	    if( uri == null ) {
	        return null;
	    }

	    String[] projection = { MediaStore.Images.Media.DATA };
	    Log.d("build", Integer.toString(Build.VERSION.SDK_INT));
	    Cursor cursor;
	    if(Build.VERSION.SDK_INT >=22)
	    {
	        // Will return "image:x*"
	        String wholeID = DocumentsContract.getDocumentId(uri);
	        // Split at colon, use second item in the array
	        String id = wholeID.split(":")[1];
	        // where id is equal to             
	        String sel = MediaStore.Images.Media._ID + "=?";

	        cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 
	                                      projection, sel, new String[]{ id }, null);
	    }
	    else
	    {
	        cursor = getContentResolver().query(uri, projection, null, null, null);
	    }
	    String path = null;
	    try
	    {
	        int column_index = cursor
	        .getColumnIndex(MediaStore.Images.Media.DATA);
	        cursor.moveToFirst();
	        path = cursor.getString(column_index).toString();
	        cursor.close();
	    }
	    catch(NullPointerException e) {

	    }
	    return path;
	}
	
	
	public String processImage(String picturePath) {
		
	
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmapImage = BitmapFactory.decodeFile(picturePath);
        String path = getFilesDir() + "/media/";
        
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = "TMP_" + timeStamp + ".jpg";
        //lastFileName = filename;
        File file =new File(path + filename);
           
        
        ExifInterface exif = null;
		try {
			exif = new ExifInterface(picturePath);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        Log.d("orientation", Integer.toString(orientation));
        
       Bitmap bmRotated = rotateBitmap(bitmapImage, orientation);
       
        
        bmRotated.compress(Bitmap.CompressFormat.JPEG, 60, stream);
        
       
        
        byte[] byteArray = stream.toByteArray();
        try {
        	FileOutputStream out = new FileOutputStream(file);
			out.write(byteArray);
			//MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
			out.flush();
            out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return filename;
	}
	
	
	
	public void removeDialog() {
		
		animDialogDown();
		
		gridView.setEnabled(true);
		
		//gridView.setAlpha(1f);
		//topLayout.setAlpha(1f);
		
		btnLayoutR.setBackgroundColor(Color.parseColor("#e0782d"));
		isEditMode = false;
		dialogOpen = false;
	}
	
	public void leaveActivity() {
		isValidPause = true;
		finish();
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_rght_out);   
		
	}
	
	public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

	    Matrix matrix = new Matrix();
		switch (orientation) {
		    case ExifInterface.ORIENTATION_NORMAL:
		        return bitmap;
		    case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
		        matrix.setScale(-1, 1);
		        break;
		    case ExifInterface.ORIENTATION_ROTATE_180:
		        matrix.setRotate(180);
		        break;
		    case ExifInterface.ORIENTATION_FLIP_VERTICAL:
		        matrix.setRotate(180);
		        matrix.postScale(-1, 1);
		        break;
		    case ExifInterface.ORIENTATION_TRANSPOSE:
		        matrix.setRotate(90);
		        matrix.postScale(-1, 1);
		        break;
		   case ExifInterface.ORIENTATION_ROTATE_90:
		       matrix.setRotate(90);
		       Log.d("rotated", "true");
			   //matrix.setRotate(360);
			   
		       break;
		   case ExifInterface.ORIENTATION_TRANSVERSE:
		       matrix.setRotate(-90);
		       matrix.postScale(-1, 1);
		       break;
		   case ExifInterface.ORIENTATION_ROTATE_270:
		       matrix.setRotate(-90);
		       break;
		   default:
		       return bitmap;
		}
		try {
		    Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		    bitmap.recycle();
		    return bmRotated;
		}
		catch (OutOfMemoryError e) {
		    e.printStackTrace();
		    return null;
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
	
	@Override
	public void onBackPressed() {
		if(dialogOpen) {
			removeDialog();
		}
		else {
			leaveActivity();
		}
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

}
