package com.myvault.myvault;

import java.io.Serializable;

public class Media implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	int id;
	int type;
	
	String location;

	public Media() {
		super();
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	
	
	
	

}
