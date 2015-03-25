package com.myvault.myvault;

import android.content.Context;
import android.content.SharedPreferences;





public class UserData {
	
	SharedPreferences sharedPrefs;
	Context context;
	
	//SharedPrefs keys/strings
	int numTries = 0;
	long lockoutStart;
	int currentTime;
	String pin;
	boolean isLocked;
	boolean isLockSet;
	boolean notFirstTime;
	boolean adShown = false;
	
	public UserData(Context context) {
		
		this.context = context;
		this.sharedPrefs = context.getSharedPreferences("myVaultPrefs", Context.MODE_PRIVATE);
		
	}

	public long getLockoutStart() {
		lockoutStart = sharedPrefs.getLong("lockoutStart", -1);
		return lockoutStart;
	}

	public void setLockoutStart(long lockoutStart) {
		this.lockoutStart = lockoutStart;
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.putLong("lockoutStart", lockoutStart);
		editor.commit();
	}

	public int getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(int currentTime) {
		this.currentTime = currentTime;
	}

	public boolean isLocked() {
		isLocked = sharedPrefs.getBoolean("isLocked", false);
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.putBoolean("isLocked", isLocked);
		editor.commit();
		
	}

	public boolean isLockSet() {
		isLockSet = sharedPrefs.getBoolean("isLockSet", false);
		return isLockSet;
	}

	public void setLockSet(boolean isLockSet) {
		this.isLockSet = isLockSet;
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.putBoolean("isLockSet", isLockSet);
		editor.commit();
		
	}

	public String getPin() {
		pin = sharedPrefs.getString("pin", "-1");
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.putString("pin", pin);
		editor.commit();
	}
	
	
	public boolean isNotFirstTime() {
		notFirstTime = sharedPrefs.getBoolean("notFirstTime", false);
		return notFirstTime;
	}

	public void setNotFirstTime() {
		this.notFirstTime = true;
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.putBoolean("notFirstTime", true);
		editor.commit();
	}
	
	public boolean pushAdShown() {
		adShown = sharedPrefs.getBoolean("adShown", false);
		return adShown;
	}
	
	public void setPushAdShown() {
		this.adShown = true;
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.putBoolean("adShown", true);
		editor.commit();
		
	}

	public void initData() {
		this.isLockSet = false;
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.putBoolean("isLockSet", isLockSet);
		editor.commit();
	}
	
	
	
	

}
