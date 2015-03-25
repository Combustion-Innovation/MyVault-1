package com.myvault.myvault;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListviewAdapter extends ArrayAdapter<ListData> {

	Context context;
	LayoutInflater mInflater;
	Communicator comm;
	ListData rowItem;
	
	ArrayList<View> items;
	
	ViewHolder holder;
	
	int position;
	
	boolean isDelete = false;
	

	
	
	public ListviewAdapter(Context context, int resource, List<ListData> object) {
		super(context, resource, object);
		// TODO Auto-generated constructor stub
		
		this.context = context;
		
		mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		items = new ArrayList<View>();
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {

		holder = null;
		this.position = position;
		
		rowItem = getItem(position);
   	 
       if (convertView == null) {  
    	   
    	   convertView = mInflater.inflate(R.layout.list_item, parent, false);
    	   holder = new ViewHolder();
    	   
    	   holder.topText = (TextView)convertView.findViewById(R.id.li_datatext_top);
    	   holder.midText = (TextView)convertView.findViewById(R.id.li_datatext_mid);
    	   holder.bottomText = (TextView)convertView.findViewById(R.id.li_datatext_btm);
    	   holder.numberText = (TextView)convertView.findViewById(R.id.li_number_text);
    	   holder.deleteLayout = (LinearLayout)convertView.findViewById(R.id.li_deletebtn_layout);
    	   holder.numberLayout = (LinearLayout)convertView.findViewById(R.id.li_number_layout);
    	   holder.deleteBtn = (Button)convertView.findViewById(R.id.li_delete_button);
    	   
    	   
    	   convertView.setTag(holder);
         
       } 
       else {
      
    	   holder = (ViewHolder)convertView.getTag();
       }
      
       holder.topText.setText(rowItem.getTopString());
       holder.midText.setText(rowItem.getMidString());
       holder.bottomText.setText(rowItem.getBottomString());
       holder.numberText.setText(Integer.toString(position + 1));
       holder.number = position;
       Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
       holder.topText.setTypeface(tf);
       holder.midText.setTypeface(tf);
       holder.bottomText.setTypeface(tf);
       holder.numberText.setTypeface(tf);
       
       
       holder.deleteBtn.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.d("delete", "clicked");
			comm.deleteItem(holder.number);
			items.remove(this);
		}
    	   
       });
       holder.deleteLayout.setOnClickListener(new OnClickListener() {

   		@Override
   		public void onClick(View v) {
   			// TODO Auto-generated method stub
   			Log.d("delete", "clicked");
   			comm.deleteItem(holder.number);
   			items.remove(this);
   		}
       	   
        });
       
       holder.numberLayout.setOnClickListener(new OnClickListener() {

      		@Override
      		public void onClick(View v) {
      			// TODO Auto-generated method stub
      			if(isDelete) {
	      			Log.d("delete", "clicked");
	      			comm.deleteItem(holder.number);
	      			items.remove(this);
      			}
      		}
          	   
           });
       
       DisplayMetrics metrics = context.getResources().getDisplayMetrics();
       int dpi = (int)metrics.density;
       
       //holder.deleteLayout.setY(-80 * dpi);
   	
       items.add(convertView);
       return convertView;
   }
	
	
	
	public void showDelete() {
		
		
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		final int dpi = (int)metrics.density;
		for(int i=0; i<items.size(); i++) {
			View v = items.get(i);
			final ViewHolder holder = (ViewHolder)v.getTag();
			//holder.deleteLayout.setY(-80 * dpi);
			//holder.deleteLayout.setVisibility(View.VISIBLE);
			//holder.numberLayout.setVisibility(View.GONE);
			//holder.deleteLayout.setY(0);
			holder.numberLayout.setClickable(true);
			holder.deleteLayout.bringToFront();
			TranslateAnimation anim=new TranslateAnimation(0,0,0, 80 * dpi);
			//anim.setFillBefore(true);
			anim.setFillAfter(true);
			anim.setDuration(550);
			anim.setAnimationListener(new AnimationListener() {
				
				
				
				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
									
					
							        
				}

				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
					
				}
			});
			holder.deleteLayout.startAnimation(anim);
						
			
		    
		}
		isDelete = true;
		comm.disableListClick();
				
	}
	
	public void showNumber() {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		final int dpi = (int)metrics.density;
		for(int i=0; i<items.size(); i++) {
			View v = items.get(i);
			final ViewHolder holder = (ViewHolder)v.getTag();
			
			holder.numberLayout.setClickable(false);
			//holder.deleteLayout.setY(0);
			
			TranslateAnimation anim=new TranslateAnimation(0, 0, 80 * dpi, 0);
			anim.setFillAfter(true);
			anim.setDuration(550);
			
			anim.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					
				}
			});
			holder.deleteLayout.startAnimation(anim);
		}
		isDelete = false;
		comm.enableListClick();
	}
	
	public void setCommunicator(Communicator comm) {
		this.comm = comm;
	}
	
	public void deleteClicked(View v) {
		
		ViewHolder holder = (ViewHolder)v.getTag();
		comm.deleteItem(holder.number);
				
	}
	
	public class ViewHolder {
		
		LinearLayout numberLayout, deleteLayout, textLayout;
		TextView topText, midText, bottomText, numberText;
		Button deleteBtn;
		int number;
		
	}
	
	public interface Communicator {
		
		public void deleteItem(int position);
		public void disableListClick();
		public void enableListClick();
		
	}
	
	

}
