package com.myvault.myvault;

import java.io.Serializable;

public class Bookmark implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	int id;
	
	String website;
	String url;
	String nickname;
	String notes;
	
	public Bookmark() {
		super();
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	};
	
	
	
	

}
