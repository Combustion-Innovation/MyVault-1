package com.myvault.myvault;

import java.io.Serializable;

public class Account implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	int id;
	
	String business;
	String accountnum;
	String businessnum;
	String notes;
	
	public Account() {
		super();
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getAccountnum() {
		return accountnum;
	}

	public void setAccountnum(String accountnum) {
		this.accountnum = accountnum;
	}

	public String getBusinessnum() {
		return businessnum;
	}

	public void setBusinessnum(String businessnum) {
		this.businessnum = businessnum;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
	

}
