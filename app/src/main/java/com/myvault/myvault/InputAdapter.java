package com.myvault.myvault;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InputAdapter extends BaseAdapter {
    private Context mContext;
    int height;
    int width;
    Context c;
    Communicator comm;
    
    ViewHolder holder;
    LayoutInflater mInflater;
    
    public InputAdapter(Context c) {
         mContext = c;
         DisplayMetrics metrics = c.getResources().getDisplayMetrics();
         width = metrics.widthPixels/3;
         
         mInflater = (LayoutInflater)c.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
         
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
    
    public void setHeight(int height) {
    	this.height = height;
    	
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

    	holder = null; 
    	
        if (convertView == null) {  
        	// if it's not recycled, initialize some attributes
          
        	convertView = mInflater.inflate(R.layout.number_tile, null);
        	holder = new ViewHolder();
        	holder.numberIcon = (TextView)convertView.findViewById(R.id.numberIcon);
        	holder.numberName = (TextView)convertView.findViewById(R.id.numberName);
        	holder.numberImage = (ImageView)convertView.findViewById(R.id.numberImage);
        	holder.tileLayout = (RelativeLayout)convertView.findViewById(R.id.number_tile_layout);
        	convertView.setTag(holder);

        } else {
        
        	   holder = (ViewHolder)convertView.getTag();
        }
    	
        
        	holder.tileLayout.setBackgroundColor(backgroundColors[position]);
        	holder.numberName.setText(text[position]);
        	if(position != 11) {
        		holder.numberIcon.setText(names[position]);
        	}
        	else {
        		holder.numberImage.setImageResource(mThumbIds[position]);
        	}
        	
        	    		
        	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, this.height/4);
        	convertView.setLayoutParams(new GridView.LayoutParams(params));
        	
        	Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
        	Typeface tflight = Typeface.createFromAsset(mContext.getAssets(), "fonts/HelveticaNeue-UltraLight-2.ttf");
        	holder.numberIcon.setTypeface(tflight);
        	holder.numberName.setTypeface(tf);
        	
        	
        	return convertView;
    }
    
    private class ViewHolder {
    	
    	RelativeLayout tileLayout;
    	TextView numberName, numberIcon;
    	ImageView numberImage;
    	
    }

    // references to our images
    private Integer[] mThumbIds = {
            0,0,0,0,0,0,0,0,0,0,0,R.drawable.vaultclear
    };

    
    private Integer[] backgroundColors = {
    Color.parseColor("#fa954c")/*light*/, Color.parseColor("#d25e0a")/*dark*/,
    Color.parseColor("#e0782d")/*medium*/,Color.parseColor("#d25e0a")/*dark*/,
    Color.parseColor("#e0782d")/*medium*/, Color.parseColor("#fa954c")/*light*/,
    Color.parseColor("#fa954c")/*light*/,Color.parseColor("#d25e0a")/*dark*/,
    Color.parseColor("#e0782d")/*medium*/,Color.parseColor("#d25e0a")/*dark*/,
    Color.parseColor("#fa954c")/*light*/,Color.parseColor("#d25e0a")/*dark*/,
    };
    
    private String[] names = {
    	"1","2","3","4","5","6",
    	"7","8","9","C","0",""
    		
    
    };
    
    private String[] text = {"", "ABC", "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ", "Clear", "", "Delete"};
    
    public void setCommunicator(Communicator comm) {
    	this.comm = comm;
    }
    
    public interface Communicator {
    	
    	public void getInput(int number);
    }

}