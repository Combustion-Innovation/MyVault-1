package com.myvault.myvault;

import java.io.Serializable;

public class Note implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	int id;
	
	String title;
	String note;
	
	public Note() {
		super();
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
	
	

}
