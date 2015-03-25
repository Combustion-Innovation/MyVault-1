package com.myvault.myvault;



import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnTouchListener;

@SuppressLint("ClickableViewAccessibility") 
public class CategoryTile extends LinearLayout implements OnTouchListener {

	int myColor;
	int myPicture;
	ImageView imageView;
	TextView catName;
	Context context;
	Button buttonOverlay;
	Communicator comm;
	
	public CategoryTile(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		inflateScreen(context);
	}
	
	public CategoryTile(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		inflateScreen(context);
	}
	
	public CategoryTile(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		inflateScreen(context);
	}

	public void inflateScreen(Context context)
	{
	    LayoutInflater  mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.category_tile, this, true);
        this.catName = (TextView)findViewById(R.id.categoryName);
        this.imageView = (ImageView)findViewById(R.id.categoryIcon);
        this.buttonOverlay = (Button)findViewById(R.id.categoryBtn);
        
	}
	
	
	
	public void setDefaults(int image, int backgroundColor,String category_name)
	{
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int dpi = (int)metrics.density;
		
		this.setOnTouchListener(this);
		this.myColor = backgroundColor;
		this.myPicture = image;
	  	this.getLayoutParams().height= 100;
	  	this.getLayoutParams().width = 100;
		this.setBackgroundColor(this.myColor);
		this.imageView.setImageResource(image);
		this.imageView.getLayoutParams().height = 50 * dpi;
		this.catName.setText(category_name);
		
		
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		catName.setTypeface(tf);
		
		
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public interface Communicator {
    	
    	public void tileClicked(View v);
    }
    
    public void setCommunicator(Communicator comm) {
    	this.comm = comm;
    }
	
		
}
