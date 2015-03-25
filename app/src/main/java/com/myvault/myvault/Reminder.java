package com.myvault.myvault;

import java.io.Serializable;

public class Reminder implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	int id;
	
	String nickname;
	String name;
	String alertdate;
	String alerttime;
	String location;
	String notes;
	long alertMillis;
	
	public Reminder() {
		super();
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlertdate() {
		return alertdate;
	}

	public void setAlertdate(String alertdate) {
		this.alertdate = alertdate;
	}

	public String getAlerttime() {
		return alerttime;
	}

	public void setAlerttime(String alerttime) {
		this.alerttime = alerttime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public long getAlertMillis() {
		return alertMillis;
	}

	public void setAlertMillis(long alertMillis) {
		this.alertMillis = alertMillis;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
	

}
