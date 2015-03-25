package com.myvault.myvault;

import java.io.Serializable;

public class CreditCard implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	String nickname;
	String cardholder;
	String brand;
	String number;
	String expiration;
	String csv;
	String notes;
	long expMillis;
	
	public CreditCard() {
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
	public String getCardholder() {
		return cardholder;
	}
	public void setCardholder(String cardholder) {
		this.cardholder = cardholder;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getExpiration() {
		return expiration;
	}
	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}
	public String getCsv() {
		return csv;
	}
	public void setCsv(String csv) {
		this.csv = csv;
	}
	public long getExpMillis() {
		return expMillis;
	}

	public void setExpMillis(long expMillis) {
		this.expMillis = expMillis;
	}

	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	

}
