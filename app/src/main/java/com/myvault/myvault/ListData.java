package com.myvault.myvault;

import java.io.Serializable;

public class ListData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	String topString, midString, bottomString;
	int id;
	
	public ListData(int id, String topString, String midString, String bottomString) {
		super();
		this.id = id;
		this.topString = topString;
		this.midString = midString;
		this.bottomString = bottomString;
	}

	public String getTopString() {
		return topString;
	}

	public void setTopString(String topString) {
		this.topString = topString;
	}

	public String getMidString() {
		return midString;
	}

	public void setMidString(String midString) {
		this.midString = midString;
	}

	public String getBottomString() {
		return bottomString;
	}

	public void setBottomString(String bottomString) {
		this.bottomString = bottomString;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

}
