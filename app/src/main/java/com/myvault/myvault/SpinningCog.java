package com.myvault.myvault;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SpinningCog extends ImageView{
	
	public SpinningCog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public SpinningCog(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public SpinningCog(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	
	
	public void startSpinning(Context c)
	{
		Animation rotation = AnimationUtils.loadAnimation(c, R.anim.clockwise_animation);
		rotation.setRepeatCount(Animation.INFINITE);
		this.startAnimation(rotation);
	}
	
	public void accelerateSpinning(Context c)
	{
		Animation rotation = AnimationUtils.loadAnimation(c, R.anim.clockwise_animation);
		rotation.setDuration(800);
		rotation.setRepeatCount(Animation.INFINITE);
		this.startAnimation(rotation);
	}

	
	public void stopSpinning()
	{
		this.clearAnimation();
	}
}
