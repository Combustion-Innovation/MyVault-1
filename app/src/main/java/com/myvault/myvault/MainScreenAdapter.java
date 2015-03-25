package com.myvault.myvault;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class MainScreenAdapter extends BaseAdapter {
    private Context mContext;
    int height;
    int width;
    int dpi;
    Context c;
    Communicator comm;
    
    public MainScreenAdapter(Context c) {
         mContext = c;
         DisplayMetrics metrics = c.getResources().getDisplayMetrics();
         dpi = (int)(metrics.density + 0.5f);
         width = metrics.widthPixels/3;
         height = metrics.heightPixels;
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

    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {

    	 CategoryTile imageView = null;
        if (convertView == null) {  
        	// if it's not recycled, initialize some attributes
          
            imageView = new CategoryTile(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(width,height));
         
        } else {
        
        	imageView = (CategoryTile) convertView;
        }
    	
        	imageView.setDefaults(mThumbIds[position], backgroundColors[position],names[position]);
     
        	imageView.getLayoutParams().width = width;
        	
        	int tileHeight = (height - (35 * dpi))/4;
        	imageView.getLayoutParams().height = tileHeight;
        	imageView.buttonOverlay.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.d("btnclick", "true");
					comm.tileClicked(v, position);
				}
			});
        	return imageView;
    }
    
    public interface Communicator {
    	
    	public void tileClicked(View view, int position);
    }
    
    public void setCommunicator(Communicator comm) {
    	this.comm = comm;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.folder_fill, R.drawable.bookmarks,
            R.drawable.steering_wheel, R.drawable.at,
            R.drawable.list_nested, R.drawable.plus,
            R.drawable.pen, R.drawable.unlock_stroke,
            R.drawable.aperture, R.drawable.calendar_alt_fill,
            R.drawable.layers, R.drawable.cog
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
    	"Accounts","Bookmarks","Cars","Contacts","Credit Cards","Insurance",
    	"Notes","Passwords","Media","Reminders","Social Security","Settings"
    		
    
    };

}