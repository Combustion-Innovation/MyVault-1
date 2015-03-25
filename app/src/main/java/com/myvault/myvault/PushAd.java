package com.myvault.myvault;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PushAd extends Activity {
	
	Button exitBtn, downloadBtn;
	TextView upperTxt, lowerTxt;
	
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.push_ad_layout);
		
		
		
		exitBtn = (Button)findViewById(R.id.pa_btn_x);
		downloadBtn = (Button)findViewById(R.id.pa_btn_download);
		
		upperTxt = (TextView)findViewById(R.id.pa_upper_text);
		lowerTxt = (TextView)findViewById(R.id.pa_lower_text);
		
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueUltraLight.ttf");
		
		upperTxt.setTypeface(tf);
		lowerTxt.setTypeface(tf);
		downloadBtn.setTypeface(tf);
		
		upperTxt.setText(Html.fromHtml("<b>" + upperTxt.getText() + "</b>"));
		downloadBtn.setText(Html.fromHtml("<b>" + downloadBtn.getText() + "</b>"));
		
		exitBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				finish();
				
				
				
			}
		});
		
		downloadBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.push.push");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
				finish();
			}
		});
	
	
	
	}

}
