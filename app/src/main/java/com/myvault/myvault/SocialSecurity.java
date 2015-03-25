package com.myvault.myvault;

import java.io.Serializable;

public class SocialSecurity implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	int id;
	
	String name;
	String ssnumber;
	String notes;
	public SocialSecurity() {
		super();
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSsnumber() {
		return ssnumber;
	}
	public void setSsnumber(String ssnumber) {
		this.ssnumber = ssnumber;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	

}
