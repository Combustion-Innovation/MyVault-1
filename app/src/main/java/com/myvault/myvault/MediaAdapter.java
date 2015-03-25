package com.myvault.myvault;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

public class MediaAdapter extends ArrayAdapter<Media> {
	
	Context context;
	
	ViewHolder holder;
	Media rowItem;
	
	public MediaAdapter(Context context, int resourceId, List<Media> items) {
	    super(context, resourceId, items);
	    this.context = context;
	    
	    
	}
	
	@SuppressLint("NewApi")
	public View getView(int position, View convertView, ViewGroup parent) {
		
		holder = null;
		rowItem = getItem(position);
		
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		
		if (convertView == null) {
			
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.media_tile, null);
			
			DisplayMetrics metrics = context.getResources().getDisplayMetrics();
			int screenWidth = metrics.widthPixels;
			int dpi = (int)metrics.density;
			
			holder.id = rowItem.getId();
			holder.type = rowItem.getType();
			holder.imageThumb = (ImageView)convertView.findViewById(R.id.mt_imageview);
			holder.playIcon = (ImageView)convertView.findViewById(R.id.mt_icon);
			
			
			if(rowItem.getType() == 2) {
				//Video
				
				String filepath = context.getApplicationContext().getFilesDir() + "/media/" + rowItem.getLocation();
				
				Bitmap thumb = ThumbnailUtils.createVideoThumbnail(filepath, MediaStore.Video.Thumbnails.MICRO_KIND);
								
				holder.imageThumb.setImageBitmap(thumb);//holder.imageThumb.setScaleType(ScaleType.FIT_CENTER);
				
				
			}
			
			else {
							
				String dir = context.getFilesDir() + "/media/";
				String filepath = dir + rowItem.getLocation();
							
				Bitmap img = BitmapFactory.decodeFile(filepath);
				
				Bitmap thumb = ThumbnailUtils.extractThumbnail(img, screenWidth/3 - 5*dpi, screenWidth/3 - 5*dpi);
				
				holder.imageThumb.setImageBitmap(thumb);
				holder.playIcon.setVisibility(View.GONE);
			}
						
			
			
			
			convertView.setTag(holder);
		} 
		else {
		    holder = (ViewHolder) convertView.getTag();
		}
		
		
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int screenWidth = metrics.widthPixels;
		int dpi = (int)metrics.density;
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(screenWidth/3, screenWidth/3);
    	convertView.setLayoutParams(new GridView.LayoutParams(params));
    	holder.imageThumb.setLayoutParams(new RelativeLayout.LayoutParams(screenWidth/3 - 2*dpi, screenWidth/3 - 2*dpi));
		
		
		
		return convertView;
	}
	
	public class ViewHolder {
		
		int id;
		int type;
		ImageView imageThumb;
		ImageView playIcon;
		VideoView videoThumb;
		
	}
	
	

}
